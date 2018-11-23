package com.yhklsdf.demo_kotlin.gson;

public class TuLingPost {

    //输入类型:0-文本(默认)、1-图片、2-音频
    private int reqType;

    //1- 128
    private String text;

    //图片地址/音频地址
    private String url;

    //所在城市
    private String city;

    //省份
    private String province;

    //街道
    private String street;

    public TuLingPost() {
        reqType = 0;
        text = "";
        url = "";
        city = "";
        province = "";
        street = "";
    }

    public void setReqType(int reqType) {
        this.reqType = reqType;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "{\"reqType\":" + reqType + ",\"perception\":{\"inputText\":{\"text\":\"" + text + "\"}},\"userInfo\":{\"apiKey\":\"7655f0b22fee44d08c1c0013e48845f7\",\"userId\":\"305517\"}}";
    }
}
