package io.log.extension.agent.container.interceptor;

import io.log.extension.agent.domain.Constants;
import io.log.extension.agent.domain.spi.DefaultMessage;

import java.util.Date;
import java.util.List;
import java.util.Stack;
import java.util.UUID;
import java.util.logging.Handler;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

public class LogExtensionInterceptor {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private List<Handler> handlers;

	ThreadLocal<Stack<DefaultMessage>> tdm = null;

	public LogExtensionInterceptor() {
		tdm = new ThreadLocal<Stack<DefaultMessage>>();
	}

	public List<Handler> getHandlers() {
		return handlers;
	}

	public void setHandlers(List<Handler> handlers) {
		this.handlers = handlers;
	}

	public void doBefore(JoinPoint jp) {
		Stack<DefaultMessage> m = tdm.get();
		String mdcRootMessageId = MDC.get(Constants.MESSAGE_ROOT_ID);
		String messageId = UUID.randomUUID().toString();

		if (StringUtils.isEmpty(mdcRootMessageId)) {
			MDC.put(Constants.MESSAGE_ROOT_ID, messageId);
			if (null != m) {
				m.clear();
			} else {
				m = new Stack<DefaultMessage>();
				tdm.set(m);
			}
			DefaultMessage dm = new DefaultMessage();
			dm.setStart(new Date());
			dm.setMessageId(messageId);
			dm.setParentMessageId(messageId);
			dm.setRootMessageId(messageId);
			m.push(dm);
		} else {
			DefaultMessage parentMessage = m.peek();
			DefaultMessage dm = new DefaultMessage();
			dm.setStart(new Date());
			dm.setMessageId(messageId);
			dm.setParentMessageId(parentMessage.getMessageId());
			dm.setRootMessageId(mdcRootMessageId);
			m.push(dm);
		}
		// long begin = System.currentTimeMillis();
		// MDC.put(Constants.MESSAGE_BEGIN_TIME, String.valueOf(begin));
		// log.info("before拦截器");
	}

	public void doInvoke(ProceedingJoinPoint pjp) throws Throwable {
		//
		// String mdcMessageId = MDC.get(Constants.MESSAGE_ID);
		// String mdcRootMessageId = MDC.get(Constants.MESSAGE_ROOT_ID);
		// String messageId = UUID.randomUUID().toString();
		//
		// if (StringUtils.isEmpty(mdcRootMessageId)) {
		// MDC.put(Constants.MESSAGE_ID, messageId);
		// MDC.put(Constants.MESSAGE_PARENT_ID, messageId);
		// MDC.put(Constants.MESSAGE_ROOT_ID, messageId);
		// } else {
		// MDC.put(Constants.MESSAGE_ID, messageId);
		// MDC.put(Constants.MESSAGE_PARENT_ID, mdcMessageId);
		// MDC.put(Constants.MESSAGE_ROOT_ID, mdcRootMessageId);
		// }
		//
		// long begin = System.currentTimeMillis();
		// MDC.put(Constants.MESSAGE_BEGIN_TIME, String.valueOf(begin));
		// log.info("before拦截器");
		//
		// pjp.proceed();
		//
		// long end = System.currentTimeMillis();;
		// long speed = begin - end;
		// MDC.put(Constants.MESSAGE_SPEED_TIME, String.valueOf(speed));
		// String aopClass = pjp.getTarget().getClass().getName();
		// MDC.put(Constants.AOP_CLASS, aopClass);
		// String aopMethod =pjp.getSignature().getName();
		// MDC.put(Constants.AOP_METHOD, aopMethod);
		// log.info("拦截器after");
	}

	public void doAfter(JoinPoint jp) {
		// String beginString = MDC.get(Constants.MESSAGE_BEGIN_TIME);
		// long begin = System.currentTimeMillis();
		// long end = begin;
		// if (null != beginString) {
		// begin = Long.valueOf(beginString);
		// }
		// long speed = begin - end;
		// MDC.put(Constants.MESSAGE_SPEED_TIME, String.valueOf(speed));
		// String aopClass = jp.getTarget().getClass().getName();
		// MDC.put(Constants.AOP_CLASS, aopClass);
		// String aopMethod = jp.getSignature().getName();
		// MDC.put(Constants.AOP_METHOD, aopMethod);
		// log.info("拦截器after");
		//
		//
		Stack<DefaultMessage> ms = tdm.get();
		if (ms.size() > 0) {
			DefaultMessage dm = ms.pop();
			dm.setTime(new Date().getTime() - dm.getStart().getTime());
			log.info(
					"messageId: {}, parentMessageId: {}, rootMessageId: {}, timeout: {}",
					dm.getMessageId(), dm.getParentMessageId(),
					dm.getRootMessageId(), dm.getTime());
		} else {
			tdm.remove();
		}
	}

	public void doThrowing(JoinPoint jp, Throwable ex) {
		Stack<DefaultMessage> ms = tdm.get();
		ex.printStackTrace();
		if (ms.size() > 0) {
			DefaultMessage dm = ms.pop();
			dm.setTime(new Date().getTime() - dm.getStart().getTime());
			log.error(
					"messageId: {}, parentMessageId: {}, rootMessageId: {}, timeout: {}",
					dm.getMessageId(), dm.getParentMessageId(),
					dm.getRootMessageId(), dm.getTime());
		} else {
			tdm.remove();
		}
	}

}
