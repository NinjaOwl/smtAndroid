package com.smt.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class UserInfo extends BaseResult implements Parcelable {
    /** 用户编号 */
    public String id = "";
    /** 登录账户(手机号) */
    @SerializedName("username")
    public String username = "";
    /** 用户姓名 */
    public String name = "";
    /** 性别 1女 2 男 0 未知 */
    public String sex = "";
    /** 厂编名称*/
    @SerializedName("factory_name")
    public String factoryName = "";
    /** 厂编号 */
    @SerializedName("factory_id")
    public String factoryId = "";

    public UserInfo(String id, String phone, String name, String sex, String factoryName, String factoryId) {
        this.id = id;
        this.username = phone;
        this.name = name;
        this.sex = sex;
        this.factoryName = factoryName;
        this.factoryId = factoryId;
    }

    public UserInfo() {
    }

    protected UserInfo(Parcel in) {
        id = in.readString();
        username = in.readString();
        name = in.readString();
        sex = in.readString();
        factoryName = in.readString();
        factoryId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(username);
        dest.writeString(name);
        dest.writeString(sex);
        dest.writeString(factoryName);
        dest.writeString(factoryId);
    }
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };

    @Override
    public String toString() {
        return "UserInfo{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", factoryName='" + factoryName + '\'' +
                ", factoryId='" + factoryId + '\'' +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
