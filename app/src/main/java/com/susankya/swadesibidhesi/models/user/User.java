package com.susankya.swadesibidhesi.models.user;

public class User {
    public int id;
    public String username;
    public String password;
    public String email;
    public String first_name;
    public String mobile_no;
    public String last_name;
    public String full_name;
    public String token;

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getToken() {
        return token;
    }
}
