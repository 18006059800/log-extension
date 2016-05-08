package io.log.extension.agent.core.handler;

import io.log.extension.agent.core.entity.spi.DefaultMessage;
import io.log.extension.agent.core.sender.Sender;

import java.util.Map;

public class DefaultMessageHandler extends AbstractMessageHandler {
	private Sender sender;

	public Sender getSender() {
		return sender;
	}

	public void setSender(Sender sender) {
		this.sender = sender;
	}

	@Override
	public void doHandle(DefaultMessage message) {
		
		// 有错误发送；根消息的状态来记录
		StorageConcurrentMap storage = StorageConcurrentMap.getInstance();
		Map<String, Boolean> root = storage.getRoot();
		String classNameAndMethodName = message.getRootClassName() + "-"
				+ message.getRootMethodName();
		if (root.containsKey(classNameAndMethodName)) {
			Boolean status = root.get(classNameAndMethodName);
			Boolean hasError = message.getHasError();
			if (!status) {
				sender.send(message);
				if (!hasError) {
					root.put(classNameAndMethodName, hasError);
				}
			}
			return;
		}
		
		sender.send(message);
//		root.put(classNameAndMethodName, value);
		
		message.getIsRootMessage();

	}


}
