package com.susankya.swadesibidhesi.models.WooCommerce;

import com.google.gson.annotations.SerializedName;

public class AccountError {
    @SerializedName("message")
    public String message;
    @SerializedName("error")
    public Error error;
    public String code;
    @Override
    public String toString() {
        return "ApiError{" +
                ", message='" + message + '\'' +
                '}';
    }
}
