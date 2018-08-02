package com.wolfcoder.earpc.net.impl;

import com.wolfcoder.earpc.client.ClientChannelInitializer;
import com.wolfcoder.earpc.model.PoolConfig;
import com.wolfcoder.earpc.model.RemoteServer;
import com.wolfcoder.earpc.net.IClientChannelPool;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ChannelFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: chenchen_839@126.com
 * @date: 2018/2/11
 */
public class NettyClientChannelPool implements IClientChannelPool{

    private List<Channel> channelList = new ArrayList<>();
    private volatile boolean isClosed;
    private PoolConfig poolConfig;
    private InetSocketAddress remoteAddress;
    private AtomicInteger poolSize = new AtomicInteger(0);
    private ChannelFactory channelFactory;
    private AtomicInteger selectedIndex = new AtomicInteger(0);

    public NettyClientChannelPool(PoolConfig poolConfig, RemoteServer remoteServer) {
        this.poolConfig = poolConfig;
        remoteAddress = new InetSocketAddress(remoteServer.getRemoteHost()
                ,remoteServer.getPort());
        Bootstrap bootstrap = createBoostrap();
        channelFactory = new NettyChannelFactory(bootstrap,poolConfig.getConnectTimeout());
        initPool(poolConfig);
    }
    private Bootstrap createBoostrap() {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(poolConfig.getThreadPoolSize()>0?
        new NioEventLoopGroup(poolConfig.getThreadPoolSize()):new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,poolConfig.getConnectTimeout())
                .remoteAddress(remoteAddress)
                .handler(new ClientChannelInitializer());
        return bootstrap;
    }
    private void initPool(PoolConfig poolConfig) {
        for (int i = 0; i < poolConfig.getThreadPoolSize(); i++) {
            Channel channel = channelFactory.newChannel();
            channelList.add(channel);
            poolSize.addAndGet(1);
        }
    }

    @Override
    public int getSize() {
        return poolSize.get();
    }

    @Override
    public Channel selectChannel() {
        if (isClosed()) {
            throw new IllegalStateException("current pool has been closed");
        }
        Channel channel = null;
        if (poolSize.get() < poolConfig.getThreadPoolSize()) {
            if (poolSize.incrementAndGet() > poolConfig.getThreadPoolSize()) {
                poolSize.decrementAndGet();
            }else {
                channel = channelFactory.newChannel();
            }
        }
        if (!channelList.isEmpty()) {
            int selected = selectedIndex.getAndIncrement() % poolConfig.getThreadPoolSize();
            channel = channelList.get(selected);
        }
        return channel;
    }

    @Override
    public boolean isClosed() {
        return isClosed;
    }

    @Override
    public void close() {
        if (isClosed == true) {
            throw new IllegalStateException("current channel pool has been closed");
        }
        isClosed = true;
        channelList.stream().filter(item ->
            item.isActive() && item.isWritable() && item.isOpen()
        ).forEach(item -> item.disconnect());
    }
}
