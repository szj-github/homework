package com.example.homework;

import android.app.Application;

public class Appdata extends Application {
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
