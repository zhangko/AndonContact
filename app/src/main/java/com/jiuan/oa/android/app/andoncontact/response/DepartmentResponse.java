package com.jiuan.oa.android.app.andoncontact.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ZhangKong on 2015/6/16.
 */
public class DepartmentResponse {
    @SerializedName("Name")
    private String name;
    @SerializedName("ID")
    private String id;
    @SerializedName("ParentID")
    private String parentid;
    @SerializedName("Code")
    private String code;
    @SerializedName("Abbreviation")
    private String abbreviation;
    @SerializedName("IsCompany")
    private int iscompany;

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIscompany(int iscompany) {
        this.iscompany = iscompany;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getCode() {
        return code;
    }

    public int getIscompany() {
        return iscompany;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getParentid() {
        return parentid;
    }

}
