package com.wolfcoder.earpc.model;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author: chenchen_839@126.com
 * @date: 2018/2/9
 */
public class RpcRequest implements Serializable{
    private static final long serialVersionUID = 5606111210446678943L;
    private String requestId;
    private String className;
    private String methodName;
    private Class<?>[] paramterTypes;
    private Object[] paramter;
    private long startTime;
    private long endTime;
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParamterTypes() {
        return paramterTypes;
    }

    public void setParamterTypes(Class<?>[] paramterTypes) {
        this.paramterTypes = paramterTypes;
    }

    public Object[] getParamter() {
        return paramter;
    }

    public void setParamter(Object[] paramter) {
        this.paramter = paramter;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "RpcRequest{" +
                "requestId='" + requestId + '\'' +
                ", className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", paramterTypes=" + Arrays.toString(paramterTypes) +
                ", paramter=" + Arrays.toString(paramter) +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
