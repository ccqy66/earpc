package com.wolfcoder.earpc.common.model;

/**
 * @author: chenchen_839@126.com
 * @date: 2018/2/11
 */
public class Tuple2<T,M> {
    private T _1;
    private M _2;

    public Tuple2(T _1, M _2) {
        this._1 = _1;
        this._2 = _2;
    }

    public T get_1() {
        return _1;
    }

    public void set_1(T _1) {
        this._1 = _1;
    }

    public M get_2() {
        return _2;
    }

    public void set_2(M _2) {
        this._2 = _2;
    }
}
