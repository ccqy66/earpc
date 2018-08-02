package com.wolfcoder.earpc.configserver;

/**
 * @author: chenchen_839@126.com
 * @date: 2018/2/9
 */
public class HelloServiceImpl implements HelloService{
    @Override
    public String hello(String name) {
        if (name == null) {
            name ="";
        }
        name = name.length()+":"+name;
        return name;
    }
}
