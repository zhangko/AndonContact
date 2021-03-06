package com.jiuan.oa.android.app.andoncontact.oahttplibrary;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ZhangKong on 2015/6/15.
 */
public class OAResponse {
    @SerializedName("Result") private int result;

    @SerializedName("ResultMessage") private String resultMessage;

    @SerializedName("ReturnValue") private String returnValue;

    @SerializedName("TS") private String timestamp;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public String getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
