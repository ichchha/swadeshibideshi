package com.susankya.swadesibidhesi.models.WooCommerce;

import android.os.Parcel;
import android.os.Parcelable;

public class WcBilling implements Parcelable{
    public String first_name;
    public String last_name;
    public String company;
    public String address_1;
    public String address_2;
    public String city;
    public String state;
    public String postcode;
    public String country;
    public String email;
    public String phone;

    public WcBilling() {
    }

    protected WcBilling(Parcel in) {
        first_name = in.readString();
        last_name = in.readString();
        company = in.readString();
        address_1 = in.readString();
        address_2 = in.readString();
        city = in.readString();
        state = in.readString();
        postcode = in.readString();
        country = in.readString();
        email = in.readString();
        phone = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(first_name);
        dest.writeString(last_name);
        dest.writeString(company);
        dest.writeString(address_1);
        dest.writeString(address_2);
        dest.writeString(city);
        dest.writeString(state);
        dest.writeString(postcode);
        dest.writeString(country);
        dest.writeString(email);
        dest.writeString(phone);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<WcBilling> CREATOR = new Creator<WcBilling>() {
        @Override
        public WcBilling createFromParcel(Parcel in) {
            return new WcBilling(in);
        }

        @Override
        public WcBilling[] newArray(int size) {
            return new WcBilling[size];
        }
    };
}
