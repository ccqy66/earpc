package com.wolfcoder.earpc.client;

import com.wolfcoder.earpc.common.model.Tuple2;
import com.wolfcoder.earpc.common.utils.Environment;
import com.wolfcoder.earpc.configserver.ConfigMeta;
import com.wolfcoder.earpc.configserver.IDiscovery;
import com.wolfcoder.earpc.configserver.zookeeper.ZookeeperDiscovery;
import com.wolfcoder.earpc.loadblance.ILoadBalance;
import com.wolfcoder.earpc.model.PoolConfig;
import com.wolfcoder.earpc.model.RemoteServer;
import com.wolfcoder.earpc.model.RemoteServerConn;
import com.wolfcoder.earpc.net.IClientChannelPool;
import com.wolfcoder.earpc.net.IClientHandler;
import com.wolfcoder.earpc.net.impl.NettyClientChannelPool;
import com.wolfcoder.earpc.utils.VerifyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author: chenchen_839@126.com
 * @date: 2018/2/9
 */
public class NettyClientHandler implements IClientHandler {
    private static final Logger logger = LoggerFactory.getLogger(NettyClientHandler.class);
    private IDiscovery discovery;
    private ConfigMeta configMeta;
    private ILoadBalance loadBalance;
    private RemoteServerConn serverConn;
    private IClientChannelPool channelPool;
    private PoolConfig poolConfig;

    public NettyClientHandler(IDiscovery discovery, ConfigMeta configMeta,
                              PoolConfig poolConfig, ILoadBalance loadBalance) {
        this.discovery = discovery;
        this.configMeta = configMeta;
        this.loadBalance = loadBalance;
        this.poolConfig = poolConfig;
    }

    public NettyClientHandler(ConfigMeta configMeta, PoolConfig poolConfig, ILoadBalance loadBalance) {
        this(new ZookeeperDiscovery(
                        Environment.getConfig("zookeeper", "localhost")),
                configMeta, poolConfig, loadBalance
        );
    }

    @Override
    public void init() {
        List<RemoteServer> remoteList = discovery.discovery(configMeta);
        if (remoteList == null || remoteList.size() == 0) {
            throw new IllegalArgumentException("remote server list is empty");
        }
        Tuple2<Boolean, RemoteServer> remoteServerVerify =
                VerifyUtils.isValidRemoteServerPort(configMeta.getRemoteServerPort());
        RemoteServer remoteServer = null;
        /**
         * support direct connect by ip and port
         */
        if (remoteServerVerify.get_1()) {
            remoteServer = remoteServerVerify.get_2();
        } else {
            remoteServer = loadBalance.select(remoteList);
        }
        channelPool = new NettyClientChannelPool(poolConfig, remoteServer);
        serverConn = new RemoteServerConn(remoteServer,channelPool);
    }

    @Override
    public void destroy() {

    }

    @Override
    public RemoteServerConn getConnect() {
        return this.serverConn;
    }
}
