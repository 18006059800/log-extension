package io.log.extension.agent.core.handler;

import io.log.extension.api.DefaultMessage;

public interface Handler {
	
	public void init();
	
	public void doHandle(DefaultMessage message);
	
	public void destory();
}
