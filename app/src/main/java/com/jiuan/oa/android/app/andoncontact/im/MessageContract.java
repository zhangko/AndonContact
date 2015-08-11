package com.jiuan.oa.android.app.andoncontact.im;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ZhangKong on 2015/8/3.
 */
public class MessageContract {

    @SerializedName("SenderUserID")
    private String senderUserID;

    @SerializedName("SenderDisplayName")
    private String senderDisplayName;

    @SerializedName("DestDisplayName")
    private String destDisplayName;

    @SerializedName("SenderTime")
    private String senderTime;

    @SerializedName("DestUserID")
    private String destUserID;

    @SerializedName("Information")
    private String information;

    @SerializedName("GroupID")
    private String groupID;

    @SerializedName("Path")
    private String path;

    public MessageContract(){
        path = "";
     }

    public String getSenderUserID() {
        return senderUserID;
    }

    public void setSenderUserID(String senderUserID) {
        this.senderUserID = senderUserID;
    }

    public String getSenderDisplayName() {
        return senderDisplayName;
    }

    public void setSenderDisplayName(String senderDisplayName) {
        this.senderDisplayName = senderDisplayName;
    }

    public String getDestDisplayName() {
        return destDisplayName;
    }

    public void setDestDisplayName(String destDisplayName) {
        this.destDisplayName = destDisplayName;
    }

    public String getSenderTime() {
        return senderTime;
    }

    public void setSenderTime(String senderTime) {
        this.senderTime = senderTime;
    }

    public String getDestUserID() {
        return destUserID;
    }

    public void setDestUserID(String destUserID) {
        this.destUserID = destUserID;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
