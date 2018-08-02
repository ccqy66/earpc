package com.wolfcoder.earpc.loadblance;

import com.wolfcoder.earpc.model.RemoteServer;

import java.util.List;

/**
 * @author: chenchen_839@126.com
 * @date: 2018/2/9
 * load balance
 */
public interface ILoadBalance {

    public RemoteServer select(List<RemoteServer> serverList);

}
