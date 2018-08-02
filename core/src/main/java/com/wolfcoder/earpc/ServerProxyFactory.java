package com.wolfcoder.earpc;

import com.wolfcoder.earpc.common.inter.Recycle;
import com.wolfcoder.earpc.configserver.ConfigMeta;
import com.wolfcoder.earpc.net.IServerHandler;
import com.wolfcoder.earpc.server.NettyServerHandler;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author: chenchen_839@126.com
 * @date: 2018/2/9
 */
public class ServerProxyFactory implements InitializingBean,Recycle{
    private static final int DEFAULT_TIMEOUT = 2000;
    private static final String DEFAULT_VERSION = "1.0.0";

    private IServerHandler serverHandler;
    private Class<?> serverInterface;
    private Object serverImplement;
    private String localAppkey;
    private String version;
    private int timeout;
    private int port;
    private boolean inited;

    public IServerHandler getServerHandler() {
        return serverHandler;
    }

    public void setServerHandler(IServerHandler serverHandler) {
        this.serverHandler = serverHandler;
    }

    public Class<?> getServerInterface() {
        return serverInterface;
    }

    public void setServerInterface(Class<?> serverInterface) {
        this.serverInterface = serverInterface;
    }

    public Object getServerImplement() {
        return serverImplement;
    }

    public void setServerImplement(Object serverImplement) {
        this.serverImplement = serverImplement;
    }

    public String getLocalAppkey() {
        return localAppkey;
    }

    public void setLocalAppkey(String localAppkey) {
        this.localAppkey = localAppkey;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            if (!inited) {
                init();
            }
        }catch (Exception e) {
            destroy();
        }finally {

        }
    }

    @Override
    public void init() {
        if (serverHandler == null) {
            ConfigMeta configMeta = new ConfigMeta();
            configMeta.setVersion(version);
            configMeta.setServerInterface(serverInterface);
            configMeta.setAppkey(localAppkey);
            configMeta.setPort(port);
            serverHandler = new NettyServerHandler(configMeta,serverImplement);
        }
        if (timeout <= 0) {
            timeout = DEFAULT_TIMEOUT;
        }
        if (StringUtils.isEmpty(localAppkey)) {
            throw new NullPointerException("localAppkey can't be null");
        }
        if (port <= 0) {
            throw new IllegalArgumentException("port can't set be 0");
        }
        if (StringUtils.isEmpty(version)) {
            version = DEFAULT_VERSION;
        }
        if (serverInterface == null) {
            throw new NullPointerException("please set serverInterface");
        }
        if (serverImplement == null) {
            throw new IllegalArgumentException("please set serverImplement!");
        }
        serverHandler.init();
        inited = true;
    }

    @Override
    public void destroy() {
        if (serverHandler != null) {
            serverHandler.destroy();
        }
    }
}
