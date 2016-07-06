package io.log.extension.agent.core.interceptor;

import io.log.extension.agent.core.config.PropertiesConfig;
import io.log.extension.agent.core.entity.Constants;
import io.log.extension.agent.core.handler.Handler;

import java.util.Date;
import java.util.List;
import java.util.Stack;
import java.util.UUID;

import io.log.extension.api.DefaultMessage;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

public class LogExtensionInterceptor {
	private ThreadLocal<Stack<DefaultMessage>> tdm = new ThreadLocal<Stack<DefaultMessage>>();

	private List<Handler> handlers;

	public List<Handler> getHandlers() {
		return handlers;
	}

	public void setHandlers(List<Handler> handlers) {
		this.handlers = handlers;
	}

	public void doBefore(JoinPoint jp) {
		DefaultMessage msg = new DefaultMessage();

		String className = jp.getTarget().getClass().getName();
		String methodName = jp.getSignature().getName();

		String domain = PropertiesConfig.getDomain();
		String host = PropertiesConfig.getHost();
		Date start = new Date();

		msg.setClassName(className);
		msg.setClassMethod(methodName);
		msg.setStart(start);
		msg.setDomain(domain);
		msg.setHost(host);
		msg.setHasError(false);

		Stack<DefaultMessage> sms = tdm.get();
		String mdcRootMessageId = MDC.get(Constants.MESSAGE_ROOT_ID);
		String mdcCurentRootMessageId = MDC
				.get(Constants.MESSAGE_CURRENT_ROOT_ID);
		String mdcParentMessageId = MDC.get(Constants.MESSAGE_PARENT_ID);

		String mdcRootClassName = MDC.get(Constants.AOP_ROOT_CLASS);
		String mdcRootMethodName = MDC.get(Constants.AOP_ROOT_METHOD);

		String messageId = UUID.randomUUID().toString();
		if (StringUtils.isEmpty(mdcRootMessageId)) { // 如果mdcRootMessageId，肯定是根消息,所有都相同。
			MDC.put(Constants.MESSAGE_ROOT_ID, messageId);
			MDC.put(Constants.MESSAGE_CURRENT_ROOT_ID, messageId);
			MDC.put(Constants.MESSAGE_PARENT_ID, messageId);

			MDC.put(Constants.AOP_ROOT_CLASS, className);
			MDC.put(Constants.AOP_ROOT_METHOD, methodName);

			if (null != sms) {
				sms.clear();
			} else {
				sms = new Stack<DefaultMessage>();
				tdm.set(sms);
			}
			msg.setRootClassName(className);
			msg.setRootMethodName(methodName);

			msg.setIsRootMessage(true);
			msg.setMessageId(messageId);
			msg.setRootMessageId(messageId);
			if (StringUtils.isEmpty(mdcCurentRootMessageId)) {
				msg.setCurrentRootMessageId(messageId);
				msg.setParentMessageId(messageId);
			} else {
				msg.setCurrentRootMessageId(mdcCurentRootMessageId);
				msg.setParentMessageId(mdcCurentRootMessageId);
			}
			msg.setStart(new Date());
			sms.push(msg);
		} else {
			if (null == sms) {
				sms = new Stack<DefaultMessage>();
				tdm.set(sms);
			} else {
				DefaultMessage parentMessage = sms.peek();
				if (null != parentMessage) {
					mdcParentMessageId = parentMessage.getMessageId();
				}
			}

			if (mdcParentMessageId.equals(mdcCurentRootMessageId)
					&& !mdcCurentRootMessageId.equals(mdcRootMessageId)) {
				// 通过JSF/Dubbo时候，如果mdcParentMessageId==
				// mdcCurentRootMessageId也认为是根消息
				msg.setIsRootMessage(true);
				msg.setRootClassName(className);
				msg.setRootMethodName(methodName);

				MDC.put(Constants.AOP_ROOT_CLASS, className);
				MDC.put(Constants.AOP_ROOT_METHOD, methodName);
			} else {
				msg.setIsRootMessage(false);
				msg.setRootClassName(mdcRootClassName);
				msg.setRootMethodName(mdcRootMethodName);
			}

			msg.setMessageId(messageId);
			msg.setCurrentRootMessageId(mdcCurentRootMessageId);
			msg.setParentMessageId(mdcParentMessageId);
			msg.setRootMessageId(mdcRootMessageId);
			msg.setStart(new Date());
			sms.push(msg);
			MDC.put(Constants.MESSAGE_PARENT_ID, messageId);
		}

	}

	public void doInvoke(ProceedingJoinPoint pjp) throws Throwable {

	}

	public void doAfter(JoinPoint jp) {
		Stack<DefaultMessage> ms = tdm.get();
		if (null == ms) {
			tdm.remove();
			return;
		}

		if (ms.size() < 1) {
			tdm.remove();
			return;
		}

		DefaultMessage dm = ms.pop();

		dm.setTime(new Date().getTime() - dm.getStart().getTime());
		dm.setStatus(Constants.MESSAGE_STATUS_OK);

		for (Handler handler : handlers) {
			handler.doHandle(dm);
		}

		if (dm.getIsRootMessage() || ms.size() < 1) {
			tdm.remove();
			for (Handler handler : handlers) {
				handler.destory();
			}
		}

	}

	public void doAfterReturning(JoinPoint jp, Object result) {

	}

	public void doThrowing(JoinPoint jp, Throwable ex) {
		Stack<DefaultMessage> ms = tdm.get();

		if (null == ms) {
			tdm.remove();
			return;
		}

		if (ms.size() < 1) {
			tdm.remove();
			for (Handler handler : handlers) {
				handler.destory();
			}
			return;
		}

		DefaultMessage dm = ms.pop();

		dm.setTime(new Date().getTime() - dm.getStart().getTime());
		dm.setStatus(Constants.MESSAGE_STATUS_FAIL);
		dm.setContent(ex.toString());
		dm.setMessageType(Constants.MESSAGE_TYPE_ERROR);
		dm.setHasError(true);
		if (ms.size() > 0) {
			DefaultMessage parentMessage = ms.peek();
			parentMessage.setHasError(true);
		}

		for (Handler handler : handlers) {
			handler.doHandle(dm);
		}

		if (dm.getIsRootMessage() || ms.size() < 1) {
			tdm.remove();
			for (Handler handler : handlers) {
				handler.destory();
			}
		}

	}

}
