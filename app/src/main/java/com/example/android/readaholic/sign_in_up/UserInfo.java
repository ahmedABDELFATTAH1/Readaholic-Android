package com.example.android.readaholic.sign_in_up;

import android.support.design.widget.NavigationView;

/**
 * this class holds the info of a user
 * we fill the data when a user signs in
 * we remove the data when the user signs out
 */
public class UserInfo {

    public static String sUserName;
    public static String sName;
    public static String sImageUrl;
    //token required for post requests
    public static String sToken;

    /**
     * adding the user data
     * called when the user data is received successfully
     * @param userName
     * @param name
     * @param imageUrl
     * @param token
     */
    public static void addUserInfo(String userName , String name , String imageUrl , String token ){
        sUserName = userName;
        sName = name;
        sImageUrl = imageUrl;
        sToken = token;
    }

    /**
     * removes the user data
     * should be called when the user signs out
     */
    public static void removeUserInfo() {
        sUserName = null;
        sName = null;
        sImageUrl = null;
        sToken = null;
    }



}