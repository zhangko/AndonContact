package com.jiuan.oa.android.app.andoncontact;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.jiuan.oa.android.app.andoncontact.oahttplibrary.OARequest;
import com.jiuan.oa.android.app.andoncontact.oahttplibrary.OAServer;

import java.lang.reflect.Modifier;

/**
 * Created by ZhangKong on 2015/6/16.
 */
public class ContactRequest extends OARequest{
    public static String PATH_DEPARTMENT;

    public static String PATH_LOGIN;

    public static String PATH_EMPLOYEE;
    private static final String DEPARTMENT = "/HumanResourcesAPI/DepartmentWeb.ashx";

    private static final String EMPLOYEE = "/HumanResourcesAPI/EmployeeWeb.ashx";

    private static final String LOGIN = "/MobileOfficeAutomationAPI/LoginWeb.ashx";

    // 添加发送小米推送token的地址
 //   public static final String TOKEN = "/MobileOfficeAutomationAPI/RegisteredToken.ashx";

    static {
        String url = "";
        switch (Flavors.SERVER_TYPE) {
            case OAServer.JIUAN:
                url = PATH_ROOT_JIUAN;
                break;
            case OAServer.JIUAN_TEST:
                url = PATH_ROOT_JIUAN_TEST;
                break;
            case OAServer.BLOOMSKY:
                url = PATH_ROOT_BLOOMSKY;
                break;
        }
        PATH_DEPARTMENT = url + DEPARTMENT;
        PATH_EMPLOYEE =url + EMPLOYEE;
        PATH_LOGIN = url + LOGIN;
    }
    @Override
    public String getContent() {
        InfoRequestBody obj = new InfoRequestBody();
        obj.setUserID(userID);
        obj.setAccessKey(accessKey);
        Gson gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.STATIC)
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
        return gson.toJson(obj);
    }
    private String userID;

    private String accessKey;
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }
    private class InfoRequestBody {

        @SerializedName("OAfUserID")
        private String userID;

        @SerializedName("AccessKey")
        private String accessKey;

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getAccessKey() {
            return accessKey;
        }

        public void setAccessKey(String accessKey) {
            this.accessKey = accessKey;
        }


    }
}
