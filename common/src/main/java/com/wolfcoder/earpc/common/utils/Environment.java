package com.wolfcoder.earpc.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author: chenchen_839@126.com
 * @date: 2018/2/8
 */
public class Environment {
    private static final Logger logger = LoggerFactory.getLogger(Environment.class);
    public static String getLocalHost() {
        String localHost = "";
        try {
            localHost = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            logger.error("get local host error",e);
        }
        return localHost;
    }

    public static String getConfig(String key,String defaultValue) {
        return defaultValue;
    }
}
