package io.log.extension.agent.container.interceptor;

import io.log.extension.agent.domain.Constants;

import java.util.List;
import java.util.UUID;
import java.util.logging.Handler;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

public class LogExtensionInterceptor {
	private List<Handler> handlers;

	public List<Handler> getHandlers() {
		return handlers;
	}

	public void setHandlers(List<Handler> handlers) {
		this.handlers = handlers;
	}

	public void doBefore(JoinPoint jp) {
		String mdcMessageId = MDC.get(Constants.MESSAGE_ID);
		String mdcRootMessageId = MDC.get(Constants.MESSAGE_ROOT_ID);
		String messageId = UUID.randomUUID().toString();

		if (StringUtils.isEmpty(mdcRootMessageId)) {
			MDC.put(Constants.MESSAGE_ID, messageId);
			MDC.put(Constants.MESSAGE_PARENT_ID, messageId);
			MDC.put(Constants.MESSAGE_ROOT_ID, messageId);
		} else {
			MDC.put(Constants.MESSAGE_ID, messageId);
			MDC.put(Constants.MESSAGE_PARENT_ID, mdcMessageId);
			MDC.put(Constants.MESSAGE_ROOT_ID, mdcRootMessageId);
		}

		long begin = System.currentTimeMillis();
		MDC.put(Constants.MESSAGE_BEGIN_TIME, String.valueOf(begin));
	}

	public void doInvoke(JoinPoint jp, ProceedingJoinPoint pjp)
			throws Throwable {
		// pjp.proceed();
	}

	public void doAfter(JoinPoint jp) {
		String beginString = MDC.get(Constants.MESSAGE_BEGIN_TIME);
		long begin = System.currentTimeMillis();
		long end = begin;
		if (null != beginString) {
			begin = Long.valueOf(beginString);
		}
		long speed = begin - end;
		MDC.put(Constants.MESSAGE_SPEED_TIME, String.valueOf(speed));
	}

	public void doThrowing(JoinPoint jp, Throwable ex) {

	}

}
