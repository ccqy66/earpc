package com.wolfcoder.earpc.net;

import io.netty.channel.Channel;

/**
 * @author: chenchen_839@126.com
 * @date: 2018/2/11
 * client channel pool
 * to decrement connect overhead
 */
public interface IClientChannelPool {
    int getSize();
    Channel selectChannel();
    boolean isClosed();
    void close();
}
