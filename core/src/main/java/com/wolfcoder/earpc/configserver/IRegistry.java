package com.wolfcoder.earpc.configserver;

import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.util.List;

/**
 * @author: chenchen42
 * @date: 2018/2/8
 * registry server into configServer
 */
public interface IRegistry {
    /**
     * register a server into configServer
     * @param configMeta
     */
    void registry(int port,ConfigMeta configMeta) throws IOException, KeeperException, InterruptedException;

    void registryMulti(int port, List<ConfigMeta> configMetas);

}
