package io.log.extension.agent.core.handler;

import io.log.extension.agent.core.config.PropertiesConfig;
import io.log.extension.agent.core.entity.spi.DefaultMessage;

import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

public abstract class AbstractHandler implements Handler {

	@Override
	public void doBefore(JoinPoint jp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doAround(ProceedingJoinPoint pjp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doAfter(JoinPoint jp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doAfterReturning(JoinPoint jp, Object result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doThrowing(JoinPoint jp, Throwable ex) {
		// TODO Auto-generated method stub

	}

	public void getBeforeMessage(JoinPoint jp, DefaultMessage msg) {
		if (null == msg) {
			msg = new DefaultMessage();
		}
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
	}

}
