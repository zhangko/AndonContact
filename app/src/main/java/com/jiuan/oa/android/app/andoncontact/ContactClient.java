package com.jiuan.oa.android.app.andoncontact;

import android.content.Context;

import com.jiuan.oa.android.app.andoncontact.oahttplibrary.HeadInfo;
import com.jiuan.oa.android.app.andoncontact.oahttplibrary.OAClient;
import com.jiuan.oa.android.app.andoncontact.oahttplibrary.OAHttpResponseHandler;
import com.loopj.android.http.RequestHandle;

/**
 * Created by ZhangKong on 2015/6/16.
 */
public class ContactClient {
    private ContactClient() {
    }

    public static RequestHandle requestChangeNumber(Context context,String account,String userID,String accessKey,String timestamp,String mobilenumber,OAHttpResponseHandler oaHttpResponseHandler){
        return request(context,account,userID,accessKey,timestamp,mobilenumber,oaHttpResponseHandler,ContactRequest.PATH_CHANGE_TELEPHONE);
    }
    public static RequestHandle requestDepartment(Context context, String account, String userID, String accessKey, ContactResponseHandler responseHandler) {
        return request(context, account, userID, accessKey, responseHandler, ContactRequest.PATH_DEPARTMENT);
    }

    public static RequestHandle requestEmployee(Context context, String account, String userID, String accessKey, ContactResponseHandler responseHandler) {
        return request(context, account, userID, accessKey, responseHandler,ContactRequest.PATH_EMPLOYEE);
    }
    public static RequestHandle request(Context context, String account, String userID, String accessKey, ContactResponseHandler responseHandler, String path) {
        ContactRequest helper = new ContactRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context).account(account);
        helper.setHeadInfo(builder.build());
        helper.setUserID(userID);
        helper.setAccessKey(accessKey);

        OAClient client = OAClient.getInstance();
        client.setSSLSocketFactory();
        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }

    public static RequestHandle request(Context context,String account,String userID,String accessKey,String timestamp,String mobilenumber,OAHttpResponseHandler responseHandler,String path){
        ChangeNumberRequest helper = new ChangeNumberRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context).account(account);
        helper.setHeadInfo(builder.build());
        helper.setUserID(userID);
        helper.setAccessKey(accessKey);
        helper.setTimeStamp(timestamp);
        helper.setMobileNumber(mobilenumber);

        OAClient client = OAClient.getInstance();
        client.setSSLSocketFactory();
        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }
    public static void cancelRequests(Context context, boolean mayInterruptIfRunning) {
        OAClient.getInstance().cancelRequests(context, mayInterruptIfRunning);

    }
}
