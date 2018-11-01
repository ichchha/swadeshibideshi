package com.susankya.swadesibidhesi.models.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class CatLevelTwo implements Parcelable{
    @SerializedName("id")
    int sub_id;
    String name;

    public CatLevelTwo(String name) {
        this.name = name;
    }

    public int getId() {
        return sub_id;
    }

    protected CatLevelTwo(Parcel in) {
        name = in.readString();
    }

    public static final Creator<CatLevelTwo> CREATOR = new Creator<CatLevelTwo>() {
        @Override
        public CatLevelTwo createFromParcel(Parcel in) {
            return new CatLevelTwo(in);
        }

        @Override
        public CatLevelTwo[] newArray(int size) {
            return new CatLevelTwo[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String item) {
        this.name = item;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }
}
