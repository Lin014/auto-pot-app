package com.example.potapp.entity;

public class User {

    final private int user_id;
    private String user_account;
    private String user_password;

    public User(String user_account, String user_password) {
        this.user_id = 0;
        this.user_account = user_account;
        this.user_password = user_password;
    }

    public void setUser_account(String user_account) {
        this.user_account = user_account;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getUser_account() {
        return user_account;
    }

    public String getUser_password() {
        return user_password;
    }
}
