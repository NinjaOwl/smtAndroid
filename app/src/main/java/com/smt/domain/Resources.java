package com.smt.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class Resources extends BaseResult implements Parcelable {
    /** Id(res_id) */
    public String id = "";
    /** 标题(res_name) */
    public String title = "";
    /** 资源创建时间 */
    public String createTime = "";
    /** 视频地址(res_url) */
    public String videoUrl = "";
    /** 缩略图地址(res_thumb) */
    public String thumbImageUrl = "";
    /** 附件名 */
    public String attachmentTitle = "";
    /** 附件后缀 */
    public String attachmentSuffix = "";
    /** 附件地址 */
    public String attachmentUrl = "";
    /** 备注(res_desc) */
    public String note = "";

    public Resources(String id, String title, String createTime,String videoUrl, String thumbImageUrl, String attachmentTitle, String attachmentSuffix,String attachmentUrl, String note) {
        this.id = id;
        this.title = title;
        this.createTime = createTime;
        this.videoUrl = videoUrl;
        this.thumbImageUrl = thumbImageUrl;
        this.attachmentTitle = attachmentTitle;
        this.attachmentSuffix = attachmentSuffix;
        this.attachmentUrl = attachmentUrl;
        this.note = note;
    }
    @Override
    public String toString() {
        return "Resources{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", createTime='" + createTime + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", thumbImageUrl='" + thumbImageUrl + '\'' +
                ", attachmentTitle='" + attachmentTitle + '\'' +
                ", attachmentSuffix='" + attachmentSuffix + '\'' +
                ", attachmentUrl='" + attachmentUrl + '\'' +
                ", note='" + note + '\'' +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
    public Resources() {
    }

    protected Resources(Parcel in) {
        id = in.readString();
        title = in.readString();
        createTime = in.readString();
        videoUrl = in.readString();
        thumbImageUrl = in.readString();
        attachmentTitle = in.readString();
        attachmentSuffix = in.readString();
        attachmentUrl = in.readString();
        note = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(createTime);
        dest.writeString(videoUrl);
        dest.writeString(thumbImageUrl);
        dest.writeString(attachmentTitle);
        dest.writeString(attachmentSuffix);
        dest.writeString(attachmentUrl);
        dest.writeString(note);
    }

    @Override
    public int describeContents() {
        return 0;
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
}
