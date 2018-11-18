package com.yhklsdf.demo_kotlin.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class PictureBean implements Parcelable {

    private String url;

    private int weidh;

    private int height;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWeidh() {
        return weidh;
    }

    public void setWeidh(int weidh) {
        this.weidh = weidh;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeInt(this.weidh);
        dest.writeInt(this.height);
    }

    public PictureBean() {
    }

    protected PictureBean(Parcel in) {
        this.url = in.readString();
        this.weidh = in.readInt();
        this.height = in.readInt();
    }

    public static final Parcelable.Creator<PictureBean> CREATOR = new Parcelable.Creator<PictureBean>() {
        @Override
        public PictureBean createFromParcel(Parcel source) {
            return new PictureBean(source);
        }

        @Override
        public PictureBean[] newArray(int size) {
            return new PictureBean[size];
        }
    };
}
