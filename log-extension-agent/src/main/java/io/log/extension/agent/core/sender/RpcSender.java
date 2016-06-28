package io.log.extension.agent.core.sender;

import io.log.extension.api.DefaultMessage;
import io.log.extension.api.RemoteService;

/**
 * 远程消息发送<br>
 * 注入一个远程服务, 可以是dubbo/jsf的服务
 *
 *
 * Created by percy on 6/27/16.
 */
public class RpcSender implements Sender{

    private RemoteService remoteService;

    public RemoteService getRemoteService() {
        return remoteService;
    }

    public void setRemoteService(RemoteService remoteService) {
        this.remoteService = remoteService;
    }

    @Override
    public void send(DefaultMessage message) {
        remoteService.send(message);
    }
}
