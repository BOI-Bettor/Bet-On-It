package com.example.betonit_bettor;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;


@ParseClassName("User")
public class User extends ParseObject {

//    public static final String KEY_USERNAME = "username";
//    public static final String KEY_EMAIL = "email";
    public static final String KEY_USER_FIRST = "user_First";
    public static final String KEY_USER_LAST = "user_Last";
    public static final String KEY_USERNAME = "username";

//
//    public String getUserName() { return getString(KEY_USERNAME); }
//    public void setUserName(String userName) { put(KEY_USERNAME, userName); }
//
//    public String getEmail() { return getString(KEY_EMAIL); }
//    public void setEmail(String email) { put(KEY_EMAIL, email); }

    public String getUserFirst() {
        return getString(KEY_USER_FIRST);
    }
    public void setUserFirst(String userFirst) { put(KEY_USER_FIRST, userFirst); }

    public String getUserLast() {
        return getString(KEY_USER_LAST);
    }
    public void setUserLast(String userLast) { put(KEY_USER_LAST, userLast); }

    public ParseUser getUser() { return getParseUser(KEY_USERNAME); }
    public void setUser(ParseUser username) { put(KEY_USERNAME, username); }





}