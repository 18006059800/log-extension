package io.log.extension.agent.core.sender;

import io.log.extension.agent.core.entity.spi.DefaultMessage;

public interface Sender {

	public void send(DefaultMessage message);

}
