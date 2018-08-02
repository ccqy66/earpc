package com.wolfcoder.earpc.model;

import java.io.Serializable;

/**
 * @author: chenchen_839@126.com
 * @date: 2018/2/9
 */
public class RemoteServer implements Serializable{
    private static final long serialVersionUID = 1852506157407273889L;

    private String remoteHost;
    private int port;
    private int weight;

    public String getRemoteHost() {
        return remoteHost;
    }

    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "RemoteServer{" +
                "remoteHost='" + remoteHost + '\'' +
                ", port=" + port +
                ", weight=" + weight +
                '}';
    }
}
