package com.wolfcoder.earpc.configserver;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author: chenchen_839@126.com
 * @date: 2018/2/8
 */
public class Constant {
    public static final String DEFAULT_SCHEMA = "erap";
    public static final String SEPARATOR = "/";
    public static String HOSTNAME;
    static {
        try {
            HOSTNAME = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
