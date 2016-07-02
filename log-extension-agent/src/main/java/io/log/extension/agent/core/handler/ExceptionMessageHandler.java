package io.log.extension.agent.core.handler;

import io.log.extension.agent.core.entity.Constants;
import io.log.extension.agent.core.sender.Sender;
import io.log.extension.api.DefaultMessage;

/**
 * Created by percy on 7/2/16.
 */
public class ExceptionMessageHandler extends AbstractMessageHandler{

    private Sender sender;

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    @Override
    public void doHandle(DefaultMessage message) {
        Boolean hasError = message.getHasError();
        if(hasError) {
            message.setMessageType(Constants.MESSAGE_TYPE_ERROR);
            sender.send(message);
            return;
        }
        return;
    }
}
