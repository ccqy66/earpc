package com.wolfcoder.earpc.configserver;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: chenchen_839@126.com
 * @date: 2018/2/9
 */
public class ClientFactoryTest {
    @Test
    public void test() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-client.xml");
        HelloService helloService = (HelloService) context.getBean("client");
        System.out.println(helloService.hello("name"));
    }
}
