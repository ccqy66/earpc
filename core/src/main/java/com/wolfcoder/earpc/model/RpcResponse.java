package com.wolfcoder.earpc.model;

import java.io.Serializable;

/**
 * @author: chenchen_839@126.com
 * @date: 2018/2/9
 */
public class RpcResponse implements Serializable{
    private static final long serialVersionUID = 5203111210446678943L;
    private String requestId;
    private Throwable error;
    private Object result;
    private boolean success;
    private long startTime;
    private long responseTime;
    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(long responseTime) {
        this.responseTime = responseTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "RpcResponse{" +
                "requestId='" + requestId + '\'' +
                ", error=" + error +
                ", result=" + result +
                ", success=" + success +
                ", startTime=" + startTime +
                ", responseTime=" + responseTime +
                '}';
    }
}
