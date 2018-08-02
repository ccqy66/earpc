package com.wolfcoder.earpc.utils;

import com.wolfcoder.earpc.common.model.Tuple2;
import com.wolfcoder.earpc.model.RemoteServer;
import org.apache.commons.lang.StringUtils;

/**
 * @author: chenchen_839@126.com
 * @date: 2018/2/11
 */
public class VerifyUtils {
    /**
     * verify valid remote server port
     * @param remoteServerPort
     * @return
     */
    public static Tuple2<Boolean, RemoteServer> isValidRemoteServerPort(String remoteServerPort) {
        Tuple2<Boolean, RemoteServer> result = new Tuple2<>(false, null);
        if (StringUtils.isEmpty(remoteServerPort)) {
            return result;
        }
        String[] remote = remoteServerPort.split(":");
        if (remote.length == 2) {
            if (StringUtils.isNotEmpty(remote[0]) && StringUtils.isNotEmpty(remote[1])) {
                try {
                    RemoteServer remoteServer = new RemoteServer();
                    remoteServer.setWeight(10);
                    remoteServer.setRemoteHost(remote[0]);
                    remoteServer.setPort(Integer.parseInt(remote[1]));
                    result.set_1(true);
                    result.set_2(remoteServer);
                } catch (Exception e) {
                    return result;
                }
            }
        }
        return result;
    }
}
