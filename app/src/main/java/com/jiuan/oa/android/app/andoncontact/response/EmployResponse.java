package com.jiuan.oa.android.app.andoncontact.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ZhangKong on 2015/6/16.
 */
public class EmployResponse {
    @SerializedName("Name")
    private String name;
    @SerializedName("Code")
    private String code;
    @SerializedName("ID")
    private String id;
    @SerializedName("DepartmentID")
    private String departmentid;
    @SerializedName("DepartmentCode")
    private String departmentcode;
    @SerializedName("Mobile")
    private String mobile;
    @SerializedName("Telephone")
    private String telephone;
    @SerializedName("Email")
    private String email;
    @SerializedName("Sex")
    private int sex;
    @SerializedName("IsMainDepartment")
    private int ismaindepartment;

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setDepartmentcode(String departmentcode) {
        this.departmentcode = departmentcode;
    }

    public void setDepartmentid(String departmentid) {
        this.departmentid = departmentid;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIsmaindepartment(int ismaindepartment) {
        this.ismaindepartment = ismaindepartment;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getIsmaindepartment() {
        return ismaindepartment;
    }

    public int getSex() {
        return sex;
    }

    public String getDepartmentcode() {
        return departmentcode;
    }

    public String getDepartmentid() {
        return departmentid;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getTelephone() {
        return telephone;
    }



}
