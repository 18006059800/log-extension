package io.log.extension.server.rpc;

import io.log.extension.api.DefaultMessage;
import io.log.extension.api.RemoteService;
import io.log.extension.server.service.MessageService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by percy on 6/28/16.
 */
//@Service("remoteServiceImpl")
public class RemoteServiceImpl implements RemoteService {

//    @Autowired
    private MessageService messageService;

    public MessageService getMessageService() {
        return messageService;
    }

    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void send(DefaultMessage defaultMessage) {
        messageService.handleDefaultMessage(convert(defaultMessage));
    }

    @Override
    public void sends(List<DefaultMessage> defaultMessages) {

    }

    private io.log.extension.server.entity.DefaultMessage convert(DefaultMessage defaultMessage) {
        io.log.extension.server.entity.DefaultMessage result = new io.log.extension.server.entity.DefaultMessage();
        BeanUtils.copyProperties(defaultMessage, result);
        return result;
    }
}
