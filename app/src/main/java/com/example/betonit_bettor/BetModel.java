package com.example.betonit_bettor;

import java.io.Serializable;

public class BetModel implements Serializable {

    private String userName;
    private String desc;
    private String objectID;


    public BetModel(String userName, String desc) {
        this.userName = userName;
        this.desc = desc;
    }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getDesc() { return desc; }
    public void setDesc(String desc) { this.desc = desc; }

}
