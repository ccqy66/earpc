package com.wolfcoder.earpc.server;

import com.wolfcoder.earpc.common.utils.Environment;
import com.wolfcoder.earpc.configserver.ConfigMeta;
import com.wolfcoder.earpc.configserver.IRegistry;
import com.wolfcoder.earpc.configserver.zookeeper.ZookeeperRegistry;
import com.wolfcoder.earpc.net.IServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.zookeeper.KeeperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author: chenchen_839@126.com
 * @date: 2018/2/9
 */
public class NettyServerHandler implements IServerHandler{
    private static final Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);
    private IRegistry registry;
    private ConfigMeta configMeta;
    private Object serviceImpl;
    private Channel channel;
    private EventLoopGroup boss;
    private EventLoopGroup work;
    public NettyServerHandler(ConfigMeta configMeta,
                              Object serviceImpl) {
        this.configMeta = configMeta;
        this.serviceImpl = serviceImpl;
    }
    @Override
    public void init() {
        registry = new ZookeeperRegistry(
                Environment.getConfig("zookeeper","localhost")
        );

        Class<?> serviceInterface = configMeta.getServerInterface();

        boss = new NioEventLoopGroup(1);
        work = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss,work)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ServerChannelInitializer(serviceImpl));
        bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        bootstrap.option(ChannelOption.TCP_NODELAY,true);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        try {
            ChannelFuture channelFuture = bootstrap.bind(configMeta.getPort()).sync();
            channel = channelFuture.channel();
            logger.info(serviceInterface.getSimpleName()+"--> has been published");
            logger.info(serviceInterface.getSimpleName()+"--> is provide server");
            registry.registry(configMeta.getPort(),configMeta);
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void destroy() {
        try {
            if (boss != null) {
                boss.shutdownGracefully();
            }
            if (work != null) {
                work.shutdownGracefully();
            }
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
