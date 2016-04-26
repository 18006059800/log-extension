package io.log.extension.agent.core.interceptor;

import io.log.extension.agent.core.config.PropertiesConfig;
import io.log.extension.agent.core.entity.Constants;
import io.log.extension.agent.core.entity.spi.DefaultMessage;
import io.log.extension.agent.core.handler.Handler;

import java.util.Date;
import java.util.List;
import java.util.Stack;
import java.util.UUID;

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

		Stack<DefaultMessage> sms = tdm.get();
		String mdcRootMessageId = MDC.get(Constants.MESSAGE_ROOT_ID);
		String mdcParentMessageId = MDC.get(Constants.MESSAGE_PARENT_ID);
		String messageId = UUID.randomUUID().toString();
		if (StringUtils.isEmpty(mdcRootMessageId)) {
			MDC.put(Constants.MESSAGE_ROOT_ID, messageId);
			MDC.put(Constants.MESSAGE_PARENT_ID, messageId);
			if (null != sms) {
				sms.clear();
			} else {
				sms = new Stack<DefaultMessage>();
				tdm.set(sms);
			}

			msg.setMessageId(messageId);
			msg.setParentMessageId(messageId);
			msg.setRootMessageId(messageId);
			msg.setStart(new Date());
			sms.push(msg);
		} else {
			if (null == sms) {
				sms = new Stack<DefaultMessage>();
				tdm.set(sms);
			} else {
				DefaultMessage parentMessage = sms.peek();
				mdcParentMessageId = parentMessage.getMessageId();
			}
			msg.setMessageId(messageId);
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
		}
		DefaultMessage dm = ms.pop();

		dm.setTime(new Date().getTime() - dm.getStart().getTime());
		dm.setStatus(Constants.MESSAGE_STATUS_OK);

		for (Handler handler : handlers) {
			handler.doHandle(dm);
		}

		if (ms.size() < 1) {
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
		}
		DefaultMessage dm = ms.pop();

		dm.setTime(new Date().getTime() - dm.getStart().getTime());
		dm.setStatus(Constants.MESSAGE_STATUS_FAIL);
		dm.setContent(ex.toString());
		for (Handler handler : handlers) {
			handler.doHandle(dm);
		}

		if (ms.size() < 1) {
			tdm.remove();
			for (Handler handler : handlers) {
				handler.destory();
			}
		}
	}

}
