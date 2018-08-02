package com.wolfcoder.earpc.configserver;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: chenchen_839@126.com
 * @date: 2018/2/9
 */
public class ServerProxyFactoryTest {
    @Test
    public void testPublish() throws InterruptedException {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-server.xml");
    }
}
