package com.wolfcoder.earpc.net.impl;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ChannelFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

/**
 * @author: chenchen_839@126.com
 * @date: 2018/2/11
 */
public class NettyChannelFactory implements ChannelFactory{
    private Bootstrap bootstrap;
    private long connectTimeout;
    private Channel channel;
    public NettyChannelFactory(Bootstrap bootstrap,long connectTimeout ) {
        this.bootstrap = bootstrap;
        this.connectTimeout = connectTimeout;
    }

    @Override
    public Channel newChannel() {
        ChannelFuture channelFuture = bootstrap.connect();
        if(channelFuture.awaitUninterruptibly(connectTimeout)
                && channelFuture.isSuccess()) {
            channel = channelFuture.channel();
        }
        return channel;
    }
}
