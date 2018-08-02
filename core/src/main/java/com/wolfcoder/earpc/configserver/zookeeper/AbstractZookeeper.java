package com.wolfcoder.earpc.configserver.zookeeper;

import org.I0Itec.zkclient.ZkClient;

/**
 * @author: chenchen_839@126.com
 * @date: 2018/2/8
 */
public abstract class AbstractZookeeper {
    protected ZkClient zkClient;
    protected String connectString;
    protected int timeout;
    protected static final int DEFAULT_TIMEOUT = 3000;

    public AbstractZookeeper(String connectString) {
        this(connectString,DEFAULT_TIMEOUT);
    }
    public AbstractZookeeper(String connectString,int timeout) {
        this.connectString = connectString;
        this.timeout = timeout;
        zkClient = new ZkClient(connectString,timeout);
    }
}
