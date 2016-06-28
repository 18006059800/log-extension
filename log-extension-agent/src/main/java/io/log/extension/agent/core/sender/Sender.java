package io.log.extension.agent.core.sender;

import io.log.extension.api.DefaultMessage;

public interface Sender {

	public void send(DefaultMessage message);

}
