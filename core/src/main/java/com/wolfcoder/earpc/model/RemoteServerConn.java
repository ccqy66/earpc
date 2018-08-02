package com.wolfcoder.earpc.model;

import com.wolfcoder.earpc.net.IClientChannelPool;

import java.io.Serializable;

/**
 * @author: chenchen_839@126.com
 * @date: 2018/2/11
 *
 */
public class RemoteServerConn implements Serializable{
    private RemoteServer remoteServerList;
    private IClientChannelPool channelPool;

    public RemoteServerConn(RemoteServer remoteServerList, IClientChannelPool channelPool) {
        this.remoteServerList = remoteServerList;
        this.channelPool = channelPool;
    }

    public RemoteServer getRemoteServerList() {
        return remoteServerList;
    }

    public void setRemoteServerList(RemoteServer remoteServerList) {
        this.remoteServerList = remoteServerList;
    }

    public IClientChannelPool getChannelPool() {
        return channelPool;
    }

    public void setChannelPool(IClientChannelPool channelPool) {
        this.channelPool = channelPool;
    }

    @Override
    public String toString() {
        return "RemoteServerConn{" +
                "remoteServerList=" + remoteServerList +
                ", channelPool=" + channelPool +
                '}';
    }
}
