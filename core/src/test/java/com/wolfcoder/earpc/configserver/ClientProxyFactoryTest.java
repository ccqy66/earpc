package com.wolfcoder.earpc.configserver;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: chenchen_839@126.com
 * @date: 2018/2/9
 */
public class ClientProxyFactoryTest {
    @Test
    public void testInvoke() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-client.xml");
        HelloService helloService = (HelloService) context.getBean("client");
        helloService.hello("hello world");
        long start = System.currentTimeMillis();
        for (int i = 0 ;i < 100 ;i++) {
            helloService.hello("hello world");
        }
        long current = System.currentTimeMillis();
        System.out.println("cost time:"+(current-start));
    }
}
