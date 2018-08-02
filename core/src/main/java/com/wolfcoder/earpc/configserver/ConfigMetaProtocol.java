package com.wolfcoder.earpc.configserver;

import com.wolfcoder.earpc.common.exception.SerializationException;

/**
 * @author: chenchen_839@126.com
 * @date: 2018/2/8
 *
 */
public interface ConfigMetaProtocol<T> {
    /**
     * transform ConfigMeta into byte[]
     * @param configMeta
     * @return
     */
    byte[] encode(T configMeta) throws SerializationException;

    /**
     * byte[] transform into ConfigMeta
     * @param binaryMeta
     * @return
     */
    T decode(byte[] binaryMeta) throws SerializationException;

}
