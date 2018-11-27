package com.smt.domain;

import android.os.Parcel;
import android.os.Parcelable;
/** 附件 */
public class Attachment extends BaseResult implements Parcelable {
    /** 资料编号(attach_id) */
    public String id = "";
    /** 资料名称(attach_name) */
    public String name = "";
    /** 资料描述(attach_desc) */
    public String desc = "";
    /** 资料后缀(attach_suffix) */
    public String suffix = "";
    /** 资料下载地址(attach_url) */
    public String url = "";

    public Attachment(String id, String name, String desc, String suffix, String url) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.suffix = suffix;
        this.url = url;
    }

    protected Attachment(Parcel in) {
        id = in.readString();
        name = in.readString();
        desc = in.readString();
        suffix = in.readString();
        url = in.readString();
    }

    public static final Creator<Attachment> CREATOR = new Creator<Attachment>() {
        @Override
        public Attachment createFromParcel(Parcel in) {
            return new Attachment(in);
        }

        @Override
        public Attachment[] newArray(int size) {
            return new Attachment[size];
        }
    };

    @Override
    public String toString() {
        return "Attachment{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", suffix='" + suffix + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
    public Attachment() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(desc);
        dest.writeString(suffix);
        dest.writeString(url);
    }
}
