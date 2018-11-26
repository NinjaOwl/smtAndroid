package com.smt.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class APPVersion extends BaseResult implements Parcelable {
    /** 版本编号 */
    @SerializedName("version_id")
    public String versionId = "";
    /** 版本号(根据此进行升级) */
    @SerializedName("version_code")
    public String versionCode = "";
    /** 升级内容 */
    @SerializedName("version_content")
    public String versionContent = "";
    /** 下载地址 */
    @SerializedName("version_url")
    public String versionURL = "";
    /** 文件大小 */
    @SerializedName("file_size")
    public String fileSize = "";
    /** 是否强制安装 */
    @SerializedName("is_force")
    public boolean isForce = false;

    public APPVersion(String versionId, String versionCode, String versionContent,
                      String versionURL,String fileSize,boolean isForce) {
        this.versionId = versionId;
        this.versionCode = versionCode;
        this.versionContent = versionContent;
        this.versionURL = versionURL;
        this.fileSize = fileSize;
        this.isForce = isForce;
    }

    public APPVersion() {

    }

    @Override
    public String toString() {
        return "APPVersion{" +
                "versionId='" + versionId + '\'' +
                ", versionCode='" + versionCode + '\'' +
                ", versionContent='" + versionContent + '\'' +
                ", versionURL='" + versionURL + '\'' +
                ", fileSize='" + fileSize + '\'' +
                ", isForce='" + isForce + '\'' +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }



    protected APPVersion(Parcel in) {
        versionId = in.readString();
        versionCode = in.readString();
        versionContent = in.readString();
        versionURL = in.readString();
        fileSize = in.readString();
        isForce = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(versionId);
        dest.writeString(versionCode);
        dest.writeString(versionContent);
        dest.writeString(versionURL);
        dest.writeString(fileSize);
        dest.writeByte((byte) (isForce ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<APPVersion> CREATOR = new Creator<APPVersion>() {
        @Override
        public APPVersion createFromParcel(Parcel in) {
            return new APPVersion(in);
        }

        @Override
        public APPVersion[] newArray(int size) {
            return new APPVersion[size];
        }
    };
}
