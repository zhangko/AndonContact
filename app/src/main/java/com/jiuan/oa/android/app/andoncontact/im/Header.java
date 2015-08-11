package com.jiuan.oa.android.app.andoncontact.im;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ZhangKong on 2015/8/3.
 */
public class Header {

    @SerializedName("ClientEnum")
    private int clientEnum;

    @SerializedName("BlobID")
    private String blobID;

    @SerializedName("Fragment")
    private String fragment;

    @SerializedName("InformationType")
    private int informationType;

    @SerializedName("IsLast")
    private boolean isLast;

    @SerializedName("BlobToken")
    private String blobToken;

    @SerializedName("FragmentIndex")
    private String fragmentIndex;

    public Header(){
        clientEnum = 5;
        blobID = "0";
        isLast = true;
        blobToken = "";
        informationType = 1;
        fragmentIndex = "0";
    }

    public int getClientEnum() {
        return clientEnum;
    }

    public void setClientEnum(int clientEnum) {
        this.clientEnum = clientEnum;
    }

    public String getBlobID() {
        return blobID;
    }

    public void setBlobID(String blobID) {
        this.blobID = blobID;
    }

    public String getFragment() {
        return fragment;
    }

    public void setFragment(String fragment) {
        this.fragment = fragment;
    }

    public int getInformationType() {
        return informationType;
    }

    public void setInformationType(int informationType) {
        this.informationType = informationType;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean isLast) {
        this.isLast = isLast;
    }

    public String getBlobToken() {
        return blobToken;
    }

    public void setBlobToken(String blobToken) {
        this.blobToken = blobToken;
    }

    public String getFragmentIndex() {
        return fragmentIndex;
    }

    public void setFragmentIndex(String fragmentIndex) {
        this.fragmentIndex = fragmentIndex;
    }
}
