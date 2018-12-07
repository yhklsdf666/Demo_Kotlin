package com.yhklsdf.demo_kotlin.bean;

public class RecycleViewItemBean<T> {

    private T data;

    private int dataType;

    public RecycleViewItemBean(T data, int dataType) {
        this.data = data;
        this.dataType = dataType;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }
}


