package com.wolfcoder.earpc.common.serialization.impl;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.wolfcoder.earpc.common.serialization.ISerialization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author: chenchen_839@126.com
 * @date: 2018/2/9
 */
public class HessianSerialization implements ISerialization{
    @Override
    public byte[] serialize(Object obj) {
        if(obj==null) {throw new NullPointerException();}
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        HessianOutput ho = new HessianOutput(os);
        try {
            ho.writeObject(obj);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return os.toByteArray();
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clz) {
        if(data==null) throw new NullPointerException();

        ByteArrayInputStream is = new ByteArrayInputStream(data);
        HessianInput hi = new HessianInput(is);
        try {
            return (T)hi.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
