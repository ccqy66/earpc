package com.wolfcoder.earpc.codec;

import com.wolfcoder.earpc.common.serialization.ISerialization;
import com.wolfcoder.earpc.common.serialization.impl.HessianSerialization;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author: chenchen_839@126.com
 * @date: 2018/2/9
 */
public class RequestDecoder extends ByteToMessageDecoder{
    private Class<?> generateClazz;
    private ISerialization serialization;

    public RequestDecoder(Class<?> generateClazz) {
        this(generateClazz,new HessianSerialization());
    }

    public RequestDecoder(Class<?> generateClazz,ISerialization serialization) {
        this.generateClazz = generateClazz;
        this.serialization = serialization;
    }
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < 4) {
            return;
        }
        byteBuf.markReaderIndex();
        int dataLength = byteBuf.readInt();
        if (dataLength < 0) {
            channelHandlerContext.close();
        }
        if (byteBuf.readableBytes() < dataLength) {
            return;
        }
        byte[] data = new byte[dataLength];
        byteBuf.readBytes(data);
        Object obj = serialization.deserialize(data, generateClazz);
        list.add(obj);
    }
}
