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
public class JsfProviderFilter extends AbstractFilter {

    @Override
    public ResponseMessage invoke(RequestMessage requestMessage) {

//        RpcContext context = RpcContext.getContext();
//        String rootMessageId = (String) context.getAttachment(Constants.MESSAGE_ROOT_ID);
//        String currentRootMessageId = (String) context.getAttachment(Constants.MESSAGE_PARENT_ID);

        Invocation invocation = requestMessage.getInvocationBody();

        String rootMessageId = (String) invocation.getAttachment(Constants.MESSAGE_ROOT_ID);
        String currentRootMessageId = (String) invocation.getAttachment(Constants.MESSAGE_PARENT_ID);

        if (StringUtils.isEmpty(rootMessageId)) {
            rootMessageId = UUID.randomUUID().toString();
            currentRootMessageId = rootMessageId;
        }
        MDC.put(Constants.MESSAGE_ROOT_ID, rootMessageId);
        MDC.put(Constants.MESSAGE_CURRENT_ROOT_ID, currentRootMessageId);
        MDC.put(Constants.MESSAGE_PARENT_ID, currentRootMessageId);


        ResponseMessage responseMessage = getNext().invoke(requestMessage);
        return responseMessage;
    }
}
