package com.susankya.swadesibidhesi.models.user;

import android.os.Parcel;
import android.os.Parcelable;

public class Checkout implements Parcelable{
    public String basket;
    public String guest_email;
    public String total;
    public ProductPrice shipping_charge;
    public String shipping_method_code;
    public ShippingAddress shipping_address;

    public Checkout() {
    }

    public Checkout(Parcel in) {
        basket = in.readString();
        guest_email = in.readString();
        total = in.readString();
        shipping_method_code = in.readString();
    }

    public static final Creator<Checkout> CREATOR = new Creator<Checkout>() {
        @Override
        public Checkout createFromParcel(Parcel in) {
            return new Checkout(in);
        }

        @Override
        public Checkout[] newArray(int size) {
            return new Checkout[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(basket);
        dest.writeString(guest_email);
        dest.writeString(total);
        dest.writeString(shipping_method_code);
    }
}
