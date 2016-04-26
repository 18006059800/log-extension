package io.log.extension.agent.core.handler;

import io.log.extension.agent.core.entity.spi.DefaultMessage;
import io.log.extension.agent.core.sender.Sender;

public class DefaultMessageHandler implements Handler {
	private Sender sender;

	public Sender getSender() {
		return sender;
	}

	public void setSender(Sender sender) {
		this.sender = sender;
	}

	@Override
	public void doHandle(DefaultMessage message) {
		sender.send(message);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destory() {
		// TODO Auto-generated method stub
		
	}

}
