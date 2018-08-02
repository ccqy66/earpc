package com.wolfcoder.earpc.configserver;

import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author: chenchen_839@126.com
 * @date: 2018/2/8
 */
public class InetAddressTest {
    @Test
    public void testInetAddress() {
        try {
            System.out.println(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
