package com.wolfcoder.earpc;

import com.google.common.util.concurrent.SettableFuture;
import com.wolfcoder.earpc.common.inter.Recycle;
import com.wolfcoder.earpc.configserver.ConfigMeta;
import com.wolfcoder.earpc.loadblance.ILoadBalance;
import com.wolfcoder.earpc.loadblance.RandomLoadBalance;
import com.wolfcoder.earpc.model.PoolConfig;
import com.wolfcoder.earpc.model.RemoteServerConn;
import com.wolfcoder.earpc.net.IClientHandler;
import com.wolfcoder.earpc.model.RpcRequest;
import com.wolfcoder.earpc.model.RpcResponse;
import com.wolfcoder.earpc.client.NettyClientHandler;
import com.wolfcoder.earpc.net.CallbackPool;
import io.netty.channel.Channel;
import org.aopalliance.intercept.MethodInterceptor;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author: chenchen_839@126.com
 * @date: 2018/2/8
 */
public class ClientProxyFactory implements FactoryBean,InitializingBean,Recycle {
    private static final Logger logger = LoggerFactory.getLogger(ClientProxyFactory.class);
    private Object proxyObject;
    private Class<?> serviceInterface;
    private String appkey;
    private String prefix;
    private String version;
    private String remoteServerPort;
    private long timeout;
    private IClientHandler clientHandler;
    private ILoadBalance loadBalance;
    private PoolConfig poolConfig;
    @Override
    public Object getObject() throws Exception {
        return proxyObject;
    }

    @Override
    public Class<?> getObjectType() {
        if (serviceInterface == null) {
            throw new NullPointerException("serviceInterface == null");
        }
        return serviceInterface;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public Class<?> getServiceInterface() {
        return serviceInterface;
    }

    public void setServiceInterface(Class<?> serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public ILoadBalance getLoadBalance() {
        return loadBalance;
    }

    public void setLoadBalance(ILoadBalance loadBalance) {
        this.loadBalance = loadBalance;
    }

    public String getRemoteServerPort() {
        return remoteServerPort;
    }

    public void setRemoteServerPort(String remoteServerPort) {
        this.remoteServerPort = remoteServerPort;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
       init();
    }

    @Override
    public void init() {
        if (StringUtils.isEmpty(appkey)) {
            logger.error("appkey can't be empty,please set appkey");
            throw new IllegalArgumentException("appkey can't be empty,please set appkey");
        }
        if (StringUtils.isEmpty(version)) {
            version = "1.0.0";
        }
        if (serviceInterface == null) {
            logger.error("serviceInterface can't be null,please set serviceInterface");
            throw new NullPointerException("serviceInterface can't be null,please set serviceInterface");
        }
        if (loadBalance == null) {
            loadBalance = new RandomLoadBalance();
        }
        if (poolConfig == null) {
            poolConfig = new PoolConfig();
            poolConfig.setConnectTimeout(200);
            poolConfig.setMaxWait(200);
            poolConfig.setThreadPoolSize(16);
            poolConfig.setTimeout(200);
        }
        ConfigMeta configMeta = new ConfigMeta();
        configMeta.setAppkey(appkey);
        configMeta.setVersion(version);
        configMeta.setServerInterface(serviceInterface);
        configMeta.setRemoteServerPort(remoteServerPort);

        clientHandler = new NettyClientHandler(configMeta,poolConfig,loadBalance);
        clientHandler.init();
        ProxyFactory pf = new ProxyFactory(serviceInterface, (MethodInterceptor) methodInvocation -> {
            Method method = methodInvocation.getMethod();
            String methodName = method.getName();
            Object[] args = methodInvocation.getArguments();
            Class<?>[] parameterTypes = method.getParameterTypes();
            RpcRequest request = new RpcRequest();
            request.setStartTime(System.currentTimeMillis());
            request.setClassName(method.getDeclaringClass().getSimpleName());
            request.setMethodName(methodName);
            request.setParamter(args);
            request.setParamterTypes(parameterTypes);
            request.setRequestId(UUID.randomUUID().toString());
            SettableFuture<RpcResponse> resultFuture = sendRequest(request);
            RpcResponse response = resultFuture.get(timeout, TimeUnit.MILLISECONDS);
            CallbackPool.removeFutureCallback(request.getRequestId());
            return response.getResult();
        });
        proxyObject = pf.getProxy();
    }
    private SettableFuture<RpcResponse> sendRequest(RpcRequest request) {
        /**
         * 发送请求
         */
        RemoteServerConn serverConn = clientHandler.getConnect();
        Channel channel = serverConn.getChannelPool().selectChannel();
        channel.writeAndFlush(request);
        CallbackPool.addFutureCallback(request.getRequestId());
        /**
         * 返回结果的future
         */
        return CallbackPool.getFuture(request.getRequestId());
    }
    @Override
    public void destroy() {
        clientHandler.destroy();
    }
}
