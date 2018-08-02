package com.wolfcoder.earpc.net;

import com.google.common.util.concurrent.SettableFuture;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author: chenchen_839@126.com
 * @date: 2018/2/9
 */
public class CallbackPool {
    private static final ConcurrentMap<String,SettableFuture> futurePool
            = new ConcurrentHashMap<>();

    public static void addFutureCallback(String requestId) {
       futurePool.putIfAbsent(requestId,SettableFuture.create());
    }
    public static SettableFuture getFuture(String requestId) {
        return futurePool.get(requestId);
    }
    public static void removeFutureCallback(String requestId) {
        futurePool.remove(requestId);
    }
}
