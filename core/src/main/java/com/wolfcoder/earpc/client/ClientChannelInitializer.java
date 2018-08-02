package com.wolfcoder.earpc.client;

import com.wolfcoder.earpc.codec.RequestDecoder;
import com.wolfcoder.earpc.codec.ResponseEncoder;
import com.wolfcoder.earpc.common.serialization.ISerialization;
import com.wolfcoder.earpc.common.serialization.impl.HessianSerialization;
import com.wolfcoder.earpc.model.RpcRequest;
import com.wolfcoder.earpc.model.RpcResponse;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @author: chenchen_839@126.com
 * @date: 2018/2/9
 */
public class ClientChannelInitializer extends ChannelInitializer<SocketChannel>{
    private ISerialization serialization;
    public ClientChannelInitializer() {
        this(new HessianSerialization());
    }
    public ClientChannelInitializer(ISerialization serialization) {
        this.serialization = serialization;
    }
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline()
                .addLast(new RequestDecoder(RpcResponse.class,serialization))
                .addLast(new ResponseEncoder(RpcRequest.class,serialization))
                .addLast(new ClientBizHandler());
    }
}
