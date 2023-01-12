package com.gabia.evoting.web.dto;

import java.io.Serializable;

public class ResponseMessageDto<T> implements Serializable {

    private String status;

    private T data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}