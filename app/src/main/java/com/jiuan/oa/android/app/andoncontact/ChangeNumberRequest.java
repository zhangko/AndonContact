package com.jiuan.oa.android.app.andoncontact;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.jiuan.oa.android.app.andoncontact.oahttplibrary.OARequest;

import java.lang.reflect.Modifier;

/**
 * Created by ZhangKong on 2015/7/17.
 */
public class ChangeNumberRequest extends OARequest {
    @Override
    public String getContent() {
        InfoRequestBody obj = new InfoRequestBody();
        obj.setUserID(userID);
        obj.setAccessKey(accessKey);
        obj.setMobileNumber(mobileNumber);
        obj.setTimeStamp(timeStamp);
        Gson gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.STATIC)
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
        return gson.toJson(obj);
    }

    private String userID;

    private String accessKey;

    private String timeStamp;

    private String mobileNumber;

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

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    private class InfoRequestBody {

        @SerializedName("OAfUserID")
        private String userID;

        @SerializedName("AccessKey")
        private String accessKey;

        @SerializedName("TS")
        private String timeStamp;

        @SerializedName("MobileNumber")
        private String mobileNumber;

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

        public void setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
        }

        public void setTimeStamp(String timeStamp) {
            this.timeStamp = timeStamp;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public String getTimeStamp() {
            return timeStamp;
        }
    }
}
