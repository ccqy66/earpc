package com.wolfcoder.earpc.model;

import java.io.Serializable;

/**
 * @author: chenchen_839@126.com
 * @date: 2018/2/11
 */
public class PoolConfig implements Serializable{
    /**
     * max eventLoop size
     */
    private int threadPoolSize;
    /**
     * connect timeout
     */
    private int connectTimeout;

    private long timeout;
    /**
     * connect wait time
     */
    private int maxWait;

    public int getThreadPoolSize() {
        return threadPoolSize;
    }

    public void setThreadPoolSize(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public int getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }
}
