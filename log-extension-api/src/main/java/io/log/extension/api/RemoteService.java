package io.log.extension.api;

import java.util.List;

/**
 * Created by percy on 6/28/16.
 */
public interface RemoteService {
    /**
     * 发送消息
     *
     * @param defaultMessage
     */
    void send(DefaultMessage defaultMessage);

    /**
     * 发送多条消息
     *
     * @param defaultMessages
     */
    void sends(List<DefaultMessage> defaultMessages);

}
