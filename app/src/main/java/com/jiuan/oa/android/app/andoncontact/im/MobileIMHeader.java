package com.jiuan.oa.android.app.andoncontact.im;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ZhangKong on 2015/8/3.
 */
public class MobileIMHeader {

    @SerializedName("ID")
    private String id;

    @SerializedName("GrouperID")
    private String groupID;

    @SerializedName("MessageIndex")
    private String messageIndex;

    @SerializedName("RecieveUserID")
    private String recieveUserID;

    @SerializedName("RevieveUserDisplayName")
    private String recieveUserDisplayName;

    @SerializedName("SenderUserID")
    private String senderUserID;

    @SerializedName("SenderUserDisplayName")
    private String senderUserDisplayName;

    @SerializedName("MessageContext")
    private String messageContext;

    @SerializedName("MessageType")
    private int messageType;

    @SerializedName("FontFamily")
    private String fontFamily;

    @SerializedName("FontSize")
    private double fontSize;

    @SerializedName("SendDateTime")
    private String sendDateTime;

    @SerializedName("MessageState")
    private int messageState;

    @SerializedName("ImageBase64")
    private String imageBase64;

    @SerializedName("R")
    private int r;

    @SerializedName("G")
    private int g;

    @SerializedName("B")
    private int b;

    public MobileIMHeader(){
        r = 0;
        g = 0;
        b = 0;
        messageState = 1;
        imageBase64 = "";
        fontSize = 120;
        fontFamily = "微软雅黑";
        messageIndex = "0";
        messageType = 1;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getMessageIndex() {
        return messageIndex;
    }

    public void setMessageIndex(String messageIndex) {
        this.messageIndex = messageIndex;
    }

    public String getRecieveUserID() {
        return recieveUserID;
    }

    public void setRecieveUserID(String recieveUserID) {
        this.recieveUserID = recieveUserID;
    }

    public String getRecieveUserDisplayName() {
        return recieveUserDisplayName;
    }

    public void setRecieveUserDisplayName(String recieveUserDisplayName) {
        this.recieveUserDisplayName = recieveUserDisplayName;
    }

    public String getSenderUserID() {
        return senderUserID;
    }

    public void setSenderUserID(String senderUserID) {
        this.senderUserID = senderUserID;
    }

    public String getSenderUserDisplayName() {
        return senderUserDisplayName;
    }

    public void setSenderUserDisplayName(String senderUserDisplayName) {
        this.senderUserDisplayName = senderUserDisplayName;
    }

    public String getMessageContext() {
        return messageContext;
    }

    public void setMessageContext(String messageContext) {
        this.messageContext = messageContext;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public double getFontSize() {
        return fontSize;
    }

    public void setFontSize(double fontSize) {
        this.fontSize = fontSize;
    }

    public String getSendDateTime() {
        return sendDateTime;
    }

    public void setSendDateTime(String sendDateTime) {
        this.sendDateTime = sendDateTime;
    }

    public int getMessageState() {
        return messageState;
    }

    public void setMessageState(int messageState) {
        this.messageState = messageState;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }
}
