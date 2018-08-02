package com.wolfcoder.earpc.common.exception;

/**
 * @author: chenchen_839@126.com
 * @date: 2018/2/8
 */
public class SerializationException extends Exception{
    public SerializationException(){}
    public SerializationException(String msg) {
        super(msg);
    }
    public SerializationException(String msg,Throwable throwable) {
        super(msg,throwable);
    }
    public SerializationException(Throwable throwable) {
        super(throwable);
    }
}
