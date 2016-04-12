package io.log.extension.agent.core.handler;

import io.log.extension.agent.core.entity.spi.DefaultMessage;

public interface Handler {
	public void doHandle(DefaultMessage message);
}
