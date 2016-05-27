package io.log.extension.agent.core.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.log.extension.agent.core.entity.spi.DefaultMessage;
import io.log.extension.agent.core.sender.Sender;
import io.log.extension.agent.core.util.JsonUtil;

public class Log4jSender implements Sender{
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void send(DefaultMessage message) {
		String result = JsonUtil.marshal(message);
		log.info(result);
	}

}
