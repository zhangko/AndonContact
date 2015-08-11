package com.jiuan.oa.android.app.andoncontact.im;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ZhangKong on 2015/8/3.
 */
public class UserRequest {


    private static final String IP = "192.168.1.159";

    private static final int PORT = 4531;

    @SerializedName("UserID")
    private String userID;

    @SerializedName("UserAddress")
    private String userAddress;

    @SerializedName("UserPort")
    private int userPort;

    @SerializedName("UserLogintime")
    private String userLogintime;

    @SerializedName("UserClientEnum")
    private int userClientEnum;

    @SerializedName("UserdisplayName")
    private String userDisplayName;

    public UserRequest(){
        userClientEnum = 5;
        userAddress = IP;
        userPort = PORT;
        userLogintime = RandString.getTimeStamp();

    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public int getUserPort() {
        return userPort;
    }

    public void setUserPort(int userPort) {
        this.userPort = userPort;
    }

    public String getUserLogintime() {
        return userLogintime;
    }

    public void setUserLogintime(String userLogintime) {
        this.userLogintime = userLogintime;
    }

    public int getUserClientEnum() {
        return userClientEnum;
    }

    public void setUserClientEnum(int userClientEnum) {
        this.userClientEnum = userClientEnum;
    }

    public String getUserDisplayName() {
        return userDisplayName;
    }

    public void setUserDisplayName(String userDisplayName) {
        this.userDisplayName = userDisplayName;
    }
}
