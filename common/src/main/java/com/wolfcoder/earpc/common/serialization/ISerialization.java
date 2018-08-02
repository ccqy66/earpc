package com.wolfcoder.earpc.common.serialization;

/**
 * @author: chenchen_839@126.com
 * @date: 2018/2/9
 */
public interface ISerialization {
    /**
     * serialize o into byte[]
     * @param o
     * @return
     */
    byte[] serialize(Object o);

    /**
     * deserialize byte[] into java object
     * @param data
     * @param clz
     * @param <T>
     * @return
     */
    <T> T deserialize(byte[] data,Class<T> clz);
}
