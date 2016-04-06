package io.log.extension.agent.core.handler;

import io.log.extension.agent.core.entity.Constants;
import io.log.extension.agent.core.entity.spi.DefaultMessage;
import io.log.extension.agent.core.sender.Sender;

import java.util.Date;
import java.util.Stack;
import java.util.UUID;

import org.aspectj.lang.JoinPoint;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

public class DefaultMessageHandler extends AbstractHandler {
	private ThreadLocal<Stack<DefaultMessage>> tdm = new ThreadLocal<Stack<DefaultMessage>>();
	private Sender sender;

	public Sender getSender() {
		return sender;
	}

	public void setSender(Sender sender) {
		this.sender = sender;
	}

	@Override
	public void doBefore(JoinPoint jp) {
		DefaultMessage msg = new DefaultMessage();
		super.getBeforeMessage(jp, msg);

		Stack<DefaultMessage> sms = tdm.get();
		String mdcRootMessageId = MDC.get(Constants.MESSAGE_ROOT_ID);
		String messageId = UUID.randomUUID().toString();
		if (StringUtils.isEmpty(mdcRootMessageId)) {
			MDC.put(Constants.MESSAGE_ROOT_ID, messageId);
			MDC.put(Constants.MESSAGE_PARENT_ID, messageId);
			if (null != sms) {
				sms.clear();
			} else {
				sms = new Stack<DefaultMessage>();
				tdm.set(sms);
			}

			msg.setMessageId(messageId);
			msg.setParentMessageId(messageId);
			msg.setRootMessageId(messageId);
			sms.push(msg);
		} else {
			DefaultMessage parentMessage = sms.peek();
			msg.setMessageId(messageId);
			msg.setParentMessageId(parentMessage.getMessageId());
			msg.setRootMessageId(mdcRootMessageId);
			sms.push(msg);
			MDC.put(Constants.MESSAGE_PARENT_ID, messageId);
		}
	}

	@Override
	public void doAfter(JoinPoint jp) {
		Stack<DefaultMessage> ms = tdm.get();
		if (null == ms) {
			tdm.remove();
		}
		DefaultMessage dm = ms.pop();
		
		dm.setTime(new Date().getTime() - dm.getStart().getTime());
		dm.setStatus(Constants.MESSAGE_STATUS_OK);
		
		sender.send(dm);
		if (ms.size() < 1) {
			tdm.remove();
		}
	}

	@Override
	public void doThrowing(JoinPoint jp, Throwable ex) {
		Stack<DefaultMessage> ms = tdm.get();
		
		
		if (null == ms) {
			tdm.remove();
		}
		DefaultMessage dm = ms.pop();
		
		dm.setTime(new Date().getTime() - dm.getStart().getTime());
		dm.setStatus(Constants.MESSAGE_STATUS_FAIL);
		dm.setContent(ex.toString());
		sender.send(dm);
		if (ms.size() < 1) {
			tdm.remove();
		}
		
	}

	// public static void main(String[] args) {
	// DefaultMessageHandler dmh = new DefaultMessageHandler();
	//
	// System.out.println(dmh.getClass().getName());
	//
	// }
}
