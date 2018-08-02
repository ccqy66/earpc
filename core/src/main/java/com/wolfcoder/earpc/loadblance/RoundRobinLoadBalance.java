package com.wolfcoder.earpc.loadblance;

import com.wolfcoder.earpc.model.RemoteServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: chenchen_839@126.com
 * @date: 2018/2/9
 */
public class RoundRobinLoadBalance implements ILoadBalance{
    private static final Logger logger = LoggerFactory.getLogger(RoundRobinLoadBalance.class);
    private static AtomicInteger currentAtPosition = new AtomicInteger(-1);
    @Override
    public RemoteServer select(List<RemoteServer> serverList) {
        int exceptPosition = currentAtPosition.get();
        int currentPosition = exceptPosition;
        RemoteServer result = null;
        int serverLength = serverList.size();
        if (currentPosition >= serverLength) {
            currentPosition = 0;
        }
        currentPosition+=1;
        try {
            result = serverList.get(currentPosition);
        }catch (NullPointerException e) {
            logger.error("serverList == null or serverList is empty",e);
            if (serverList == null || serverList.size() == 0) {
                throw new NullPointerException("serverList == null or serverList is empty");
            }
        }catch (ArrayIndexOutOfBoundsException e) {
            currentPosition = 0;
            result = serverList.get(currentPosition);
            currentAtPosition.compareAndSet(exceptPosition,currentPosition);
        }
        return result;
    }
}
