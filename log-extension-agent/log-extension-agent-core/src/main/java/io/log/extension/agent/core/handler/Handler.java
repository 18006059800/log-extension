package io.log.extension.agent.core.handler;

import org.aspectj.lang.JoinPoint;

public interface Handler {

	public void doBefore(JoinPoint jp);
	
	public void doAround(JoinPoint jp);
	
	public void doAfter(JoinPoint jp);
	
	public void doAfterReturning(JoinPoint jp, Object result);
	
	public void doThrowing(JoinPoint jp, Throwable ex);
}
