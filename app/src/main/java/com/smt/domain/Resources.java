package com.smt.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Resources extends BaseResult implements Parcelable {
    /** Id(res_id) */
    public String id = "";
    /** 标题(res_name) */
    public String title = "";
    /** 资源创建时间(res_date) */
    public String createTime = "";
    /** 视频地址(res_url) */
    public String videoUrl = "";
    /** 缩略图地址(res_thumb) */
    public String thumbImageUrl = "";
    /** 备注(res_desc) */
    public String note = "";
    /** 附件列表*/
    public ArrayList<Attachment> attachments = new ArrayList<Attachment>();

    public Resources(String id, String title, String createTime,String videoUrl, String thumbImageUrl, String note,ArrayList<Attachment> attachments) {
        this.id = id;
        this.title = title;
        this.createTime = createTime;
        this.videoUrl = videoUrl;
        this.thumbImageUrl = thumbImageUrl;
        this.note = note;
        this.attachments = attachments;
    }

    protected Resources(Parcel in) {
        id = in.readString();
        title = in.readString();
        createTime = in.readString();
        videoUrl = in.readString();
        thumbImageUrl = in.readString();
        note = in.readString();
        attachments = in.createTypedArrayList(Attachment.CREATOR);
    }

    public static final Creator<Resources> CREATOR = new Creator<Resources>() {
        @Override
        public Resources createFromParcel(Parcel in) {
            return new Resources(in);
        }

        @Override
        public Resources[] newArray(int size) {
            return new Resources[size];
        }
    };

    @Override
    public String toString() {
        return "Resources{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", createTime='" + createTime + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", thumbImageUrl='" + thumbImageUrl + '\'' +
                ", attachments='" + attachments.toString() + '\'' +
                ", note='" + note + '\'' +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
    public Resources() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(createTime);
        dest.writeString(videoUrl);
        dest.writeString(thumbImageUrl);
        dest.writeString(note);
        dest.writeTypedList(attachments);
    }
}
