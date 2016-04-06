package io.log.extension.agent.core.filter;

import io.log.extension.agent.core.entity.Constants;

import java.util.UUID;

import org.slf4j.MDC;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;

public class DubboConsumerFilter implements Filter {

	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation)
			throws RpcException {
		String rootMessageId = MDC.get(Constants.MESSAGE_ROOT_ID);
		String parentMessageId = MDC.get(Constants.MESSAGE_PARENT_ID);
		MDC.put("aa", "bb");
		
		if (StringUtils.isEmpty(rootMessageId)) {
			rootMessageId = UUID.randomUUID().toString();
			parentMessageId = rootMessageId;
		}

		RpcContext context = RpcContext.getContext();
		context.setAttachment(Constants.MESSAGE_ROOT_ID, rootMessageId);
		context.setAttachment(Constants.MESSAGE_PARENT_ID, parentMessageId);
		
		return invoker.invoke(invocation);
	}

}
