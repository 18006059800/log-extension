package io.log.extension.agent.core.handler;

import org.aspectj.lang.JoinPoint;

public abstract class AbstractHandler implements Handler {

	@Override
	public void doBefore(JoinPoint jp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doAround(JoinPoint jp) {
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

}
