package com.wolfcoder.earpc.configserver;

import org.apache.commons.lang.StringUtils;

/**
 * @author: chenchen_839@126.com
 * @date: 2018/2/8
 * node schema:
 * key=earpc/appkey/server/version
 * value=ip:port
 * for example:
 * key=earpc/somebu/com.wolfcoder.earpc.HelloWorld/1.0.0
 */
public class ConfigMeta {
    private String prefix;
    private String appkey;
    private String remoteServerPort;
    private Class<?> serverInterface;
    private String version;
    private String localAddress;
    private int port;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public Class<?> getServerInterface() {
        return serverInterface;
    }

    public void setServerInterface(Class<?> serverInterface) {
        this.serverInterface = serverInterface;
    }

    public String getVersion() {
        return version;
    }

    public String getLocalAddress() {
        return localAddress;
    }

    public void setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getRemoteServerPort() {
        return remoteServerPort;
    }

    public void setRemoteServerPort(String remoteServerPort) {
        this.remoteServerPort = remoteServerPort;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    public String generateSchema() {
        StringBuilder schemaBuilder = new StringBuilder("/");
        if (StringUtils.isEmpty(prefix)) {
            schemaBuilder.append(Constant.DEFAULT_SCHEMA);
        }else {
            schemaBuilder.append(prefix);
        }
        schemaBuilder.append(Constant.SEPARATOR)
                .append(appkey)
                .append(Constant.SEPARATOR)
                .append(serverInterface.getName())
                .append(Constant.SEPARATOR)
                .append(version);
        return schemaBuilder.toString();
    }

    @Override
    public String toString() {
        return "ConfigMeta{" +
                "prefix='" + prefix + '\'' +
                ", appkey='" + appkey + '\'' +
                ", serverInterface='" + serverInterface + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
