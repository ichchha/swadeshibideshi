package com.susankya.swadesibidhesi.models.user;

public class Availability {
    private Boolean is_available_to_buy;
    private int num_available;
    private String message;

    public Boolean getIs_available_to_buy() {
        return is_available_to_buy;
    }

    public int getNum_available() {
        return num_available;
    }

    public String getMessage() {
        return message;
    }
}
