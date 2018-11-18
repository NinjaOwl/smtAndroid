package com.smt.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Factory extends BaseResult implements Parcelable {
    /** 工厂编号 */
    @SerializedName("factory_id")
    public String id = "";
    /** 工厂名称 */
    @SerializedName("factory_name")
    public String name = "";
    /** 工厂地址 */
    @SerializedName("factory_address")
    public String address = "";

    protected Factory(Parcel in) {
        id = in.readString();
        name = in.readString();
        address = in.readString();
    }

    @Override
    public String toString() {
        return "Factory{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public Factory(String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Factory() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(address);
    }

    public static final Creator<Factory> CREATOR = new Creator<Factory>() {
        @Override
        public Factory createFromParcel(Parcel in) {
            return new Factory(in);
        }

        @Override
        public Factory[] newArray(int size) {
            return new Factory[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
