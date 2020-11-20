package com.example.betonit_bettor;

import java.io.Serializable;

public class UsersModel implements Serializable {

    private String userName;
    private String fullName;
    private int img;

    public UsersModel(String userName, String fullName, int img) {
        this.userName = userName;
        this.fullName = fullName;
        this.img = img;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
