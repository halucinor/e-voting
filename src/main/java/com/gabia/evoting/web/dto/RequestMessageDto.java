package com.gabia.evoting.web.dto;

import java.io.Serializable;

public class RequestMessageDto<T> implements Serializable {

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
