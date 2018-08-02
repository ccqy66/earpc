package com.wolfcoder.earpc.configserver.zookeeper;

import com.wolfcoder.earpc.configserver.ConfigMeta;
import com.wolfcoder.earpc.configserver.IDiscovery;
import com.wolfcoder.earpc.model.RemoteServer;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: chenchen_839@126.com
 * @date: 2018/2/8
 */
public class ZookeeperDiscovery extends AbstractZookeeper implements IDiscovery{
    private static final int DEFAULT_WEIGHT = 10;
    public ZookeeperDiscovery(String connectString) {
        super(connectString);
    }
    public ZookeeperDiscovery(String connectString,int timeout) {
        super(connectString, timeout);
    }
    @Override
    public List<RemoteServer> discovery(ConfigMeta configMeta) {
        if (configMeta == null) {
            throw new NullPointerException("configMeta==null");
        }
        String addressPath = configMeta.generateSchema();
        List<String> providers = zkClient.getChildren(addressPath);
        return providers.stream()
                .filter(item -> StringUtils.isNotBlank(item))
                .map(item -> {
                    RemoteServer remoteServer = new RemoteServer();
                    String[] remote = item.split(":");
                    remoteServer.setRemoteHost(remote[0]);
                    remoteServer.setPort(Integer.parseInt(remote[1]));
                    if (remote.length > 2) {
                        remoteServer.setWeight(Integer.parseInt(remote[2]));
                    }else {
                        remoteServer.setWeight(DEFAULT_WEIGHT);
                    }
                    return remoteServer;
                })
                .collect(Collectors.toList());
    }
}
