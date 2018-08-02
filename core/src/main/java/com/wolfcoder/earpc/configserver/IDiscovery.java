package com.wolfcoder.earpc.configserver;

import com.wolfcoder.earpc.model.RemoteServer;

import java.util.List;

/**
 * @author: chenchen42
 * @date: 2018/2/8
 * discover server from configServer
 */
public interface IDiscovery {
    /**
     * discover server from configServer
     * @param configMeta
     * @return
     */
    List<RemoteServer> discovery(ConfigMeta configMeta);
}
