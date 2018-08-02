package com.wolfcoder.earpc.server;

import com.wolfcoder.earpc.common.serialization.ISerialization;
import com.wolfcoder.earpc.common.serialization.impl.HessianSerialization;
import com.wolfcoder.earpc.model.RpcRequest;
import com.wolfcoder.earpc.model.RpcResponse;
import com.wolfcoder.earpc.codec.RequestDecoder;
import com.wolfcoder.earpc.codec.ResponseEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;


/**
 * @author: chenchen_839@126.com
 * @date: 2018/2/9
 */
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    private ISerialization serialization;
    private Object serverImpl;

    public ServerChannelInitializer(Object serverImpl) {
       this(new HessianSerialization(),serverImpl);
    }

    public ServerChannelInitializer(ISerialization serialization,Object serverImpl) {
        this.serialization = serialization;
        this.serverImpl = serverImpl;
    }
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        channel.pipeline()
                .addLast(new RequestDecoder(RpcRequest.class,serialization))
                .addLast(new ResponseEncoder(RpcResponse.class,serialization))
                .addLast(new ServerBizHandler(serverImpl));
    }
}
