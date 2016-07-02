package io.log.extension.agent.core.filter;

import com.jd.jsf.gd.filter.AbstractFilter;
import com.jd.jsf.gd.msg.Invocation;
import com.jd.jsf.gd.msg.RequestMessage;
import com.jd.jsf.gd.msg.ResponseMessage;
import com.jd.jsf.gd.util.RpcContext;
import io.log.extension.agent.core.entity.Constants;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * Created by percy on 6/30/16.
 */
public class JsfConsumerFilter extends AbstractFilter {
    @Override
    public ResponseMessage invoke(RequestMessage requestMessage) {
        Invocation invocation = requestMessage.getInvocationBody();

        String rootMessageId = MDC.get(Constants.MESSAGE_ROOT_ID);
        String parentMessageId = MDC.get(Constants.MESSAGE_PARENT_ID);

        if (StringUtils.isEmpty(rootMessageId)) {
            rootMessageId = UUID.randomUUID().toString();
            parentMessageId = rootMessageId;
        }

//        RpcContext context = RpcContext.getContext();

        invocation.addAttachment(Constants.MESSAGE_ROOT_ID, rootMessageId);
        invocation.addAttachment(Constants.MESSAGE_PARENT_ID, parentMessageId);

//        context.setAttachment(Constants.MESSAGE_ROOT_ID, rootMessageId);
//        context.setAttachment(Constants.MESSAGE_PARENT_ID, parentMessageId);
        ResponseMessage responseMessage = getNext().invoke(requestMessage);
        return responseMessage;
    }
}
