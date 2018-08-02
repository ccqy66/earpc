package com.wolfcoder.earpc.net;

import com.wolfcoder.earpc.common.inter.Recycle;
import com.wolfcoder.earpc.model.RemoteServerConn;

/**
 * @author: chenchen_839@126.com
 * @date: 2018/2/9
 */
public interface IClientHandler extends Recycle{
    RemoteServerConn getConnect();
}
