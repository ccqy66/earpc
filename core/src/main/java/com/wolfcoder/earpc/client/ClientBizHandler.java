package com.wolfcoder.earpc.client;

import com.google.common.util.concurrent.SettableFuture;
import com.wolfcoder.earpc.model.RpcResponse;
import com.wolfcoder.earpc.net.CallbackPool;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author: chenchen_839@126.com
 * @date: 2018/2/9
 */
public class ClientBizHandler extends SimpleChannelInboundHandler<RpcResponse>{
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcResponse rpcResponse) throws Exception {
        SettableFuture<Object> result = CallbackPool.getFuture(rpcResponse.getRequestId());
        if (result != null) {
            result.set(rpcResponse);
        }
    }
}
