package com.wolfcoder.earpc.codec;

import com.wolfcoder.earpc.common.serialization.ISerialization;
import com.wolfcoder.earpc.common.serialization.impl.HessianSerialization;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author: chenchen_839@126.com
 * @date: 2018/2/9
 * |4 byte| body |
 *  data-length content
 */
public class ResponseEncoder extends MessageToByteEncoder{
    private ISerialization serialization;

    private Class<?> generateClass;
    public ResponseEncoder(Class<?> generateClass) {
        this(generateClass,new HessianSerialization());
    }

    public ResponseEncoder(Class<?> generateClass,ISerialization serialization) {
        this.generateClass = generateClass;
        this.serialization = serialization;
    }
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        if (generateClass.isInstance(o)) {
            byte[] data = serialization.serialize(o);
            byteBuf.writeInt(data.length);
            byteBuf.writeBytes(data);
        }
    }
}
