package io.log.extension.agent.core.interceptor;

import io.log.extension.agent.core.handler.Handler;

import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

public class LogExtensionInterceptor {
	private List<Handler> handlers;

	public List<Handler> getHandlers() {
		return handlers;
	}

	public void setHandlers(List<Handler> handlers) {
		this.handlers = handlers;
	}

	public void doBefore(JoinPoint jp) {
		for (Handler handler : handlers) {
			handler.doBefore(jp);
		}
	}

	public void doInvoke(ProceedingJoinPoint pjp) throws Throwable {
		for (Handler handler : handlers) {
			handler.doAround(pjp);
		}
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
		for (Handler handler : handlers) {
			handler.doAfter(jp);
		}
	}

	public void doAfterReturning(JoinPoint jp, Object result) {
		for (Handler handler : handlers) {
			handler.doAfterReturning(jp, result);
		}
	}

	public void doThrowing(JoinPoint jp, Throwable ex) {
		for (Handler handler : handlers) {
			handler.doThrowing(jp, ex);
		}
	}

}
