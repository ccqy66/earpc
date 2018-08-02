package com.wolfcoder.earpc.server;

import com.wolfcoder.earpc.model.RpcRequest;
import com.wolfcoder.earpc.model.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.Method;

/**
 * @author: chenchen_839@126.com
 * @date: 2018/2/9
 */
public class ServerBizHandler extends SimpleChannelInboundHandler<RpcRequest>{
    private Object serverImpl;

    public ServerBizHandler(Object serverImpl) {
        this.serverImpl = serverImpl;
    }
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcRequest request) throws Exception {
        RpcResponse response = new RpcResponse();
        String methodName = request.getMethodName();
        Class<?> serverImplClz = serverImpl.getClass();
        Method method = serverImplClz.getMethod(methodName,request.getParamterTypes());
        response.setRequestId(request.getRequestId());
        try {
            Object result = method.invoke(serverImpl,request.getParamter());
            response.setSuccess(true);
            response.setResult(result);
            response.setStartTime(request.getStartTime());
            response.setResponseTime(request.getEndTime());
            channelHandlerContext.writeAndFlush(response);
        }catch (Exception e) {
            response.setSuccess(false);
            response.setError(e);
        }
    }
}
