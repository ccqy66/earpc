### How to Use
#### Server:
  - interface:
    ```java
    /**
     * @author: chenchen_839@126.com
     * @date: 2018/2/9
     */
    public interface HelloService {
        String hello(String name);
    }
    ```
    - implement:
    
    ```java
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
    ```
    
    - spring config:
    ```xml
        <beans xmlns="http://www.springframework.org/schema/beans"
               xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
               xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
            <bean id="publish" class="com.wolfcoder.earpc.ServerProxyFactory" init-method="init">
                <property name="localAppkey" value="com.wolfcoder.earpc"/>
                <property name="version" value="1.0.0"/>
                <property name="timeout" value="200"/>
                <property name="port" value="10888"/>
                <property name="serverInterface" value="com.wolfcoder.earpc.configserver.HelloService"/>
                <property name="serverImplement" ref="helloWorld"/>
            </bean>
            <bean id="helloWorld" class="com.wolfcoder.earpc.configserver.HelloServiceImpl"/>
        </beans>
    ```
#### client

  - spring config:
    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
        <bean id="client" class="com.wolfcoder.earpc.ClientProxyFactory">
            <property name="appkey" value="com.wolfcoder.earpc"/>
            <property name="serviceInterface" value="com.wolfcoder.earpc.configserver.HelloService"/>
            <property name="timeout" value="200"/>
            <property name="version" value="1.0.0"/>
            <property name="remoteServerPort" value="localhost:10888"/>
            <property name="loadBalance" ref="roundRobinLoadBalance"/>
        </bean>
        <bean id="roundRobinLoadBalance" class="com.wolfcoder.earpc.loadblance.RoundRobinLoadBalance"/>
    </beans>
    ```