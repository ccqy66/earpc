package com.wolfcoder.earpc.configserver.zookeeper;

import com.wolfcoder.earpc.common.utils.Environment;
import com.wolfcoder.earpc.configserver.ConfigMeta;
import com.wolfcoder.earpc.configserver.IRegistry;
import org.apache.zookeeper.KeeperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * @author: chenchen_839@126.com
 * @date: 2018/2/8
 * registry implement by zookeeper
 */
public class ZookeeperRegistry extends AbstractZookeeper implements IRegistry {
    private static final Logger logger = LoggerFactory.getLogger(ZookeeperRegistry.class);

    public ZookeeperRegistry(String connectString) {
        super(connectString);
    }

    public ZookeeperRegistry(String connectString,int timeout) {
        super(connectString, timeout);
    }

    @Override
    public void registry(int port, ConfigMeta configMeta) throws IOException, KeeperException, InterruptedException {
        if (configMeta == null) {
            logger.error("configMeta must be no-null");
            throw new NullPointerException("configMeta == null");
        }
        String configMetaStr = configMeta.generateSchema();
        String localAddress = configMetaStr.concat("/").concat(Environment.getLocalHost())
                .concat(":").concat(String.valueOf(port));
        String value = Environment.getLocalHost()+":"+port;
        if (!zkClient.exists(configMetaStr)) {
            zkClient.createPersistent(configMetaStr,true);
        }
        if (zkClient.exists(localAddress)) {
            zkClient.delete(localAddress);
        }else {
            zkClient.createEphemeral(localAddress,value.getBytes());
        }
    }

    @Override
    public void registryMulti(int port, List<ConfigMeta> configMetas){
        if (configMetas == null || configMetas.size()==0) {
            logger.error("configMetas must be no-null or no-empty");
        }
        configMetas.stream().forEach(item -> {
            try {
                registry(port, item);
            } catch (IOException e) {
                logger.error("register server error-IOException.{},{}",port,item);
            } catch (KeeperException e) {
                logger.error("register server error-KeeperException.{},{}",port,item);
            } catch (InterruptedException e) {
                logger.error("register server error-InterruptedException.{},{}",port,item);
            }
        });
    }
}
