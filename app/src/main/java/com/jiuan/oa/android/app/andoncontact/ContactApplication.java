package com.jiuan.oa.android.app.andoncontact;

import android.app.Activity;
import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiuan.oa.android.app.andoncontact.convertpinyin.HanziToPinyin;
import com.jiuan.oa.android.app.andoncontact.database.MyDBHelper;
import com.jiuan.oa.android.app.andoncontact.response.DepartmentResponse;
import com.jiuan.oa.android.app.andoncontact.response.EmployResponse;
import com.jiuan.oa.android.library.http.login.OALoginResponse;
import com.jiuan.oa.android.app.andoncontact.oahttpprotocollibrary.LoginProtocol;
import com.jiuan.oa.android.app.andoncontact.oahttpprotocollibrary.LoginUtils;

import java.util.List;

/**
 * Created by ZhangKong on 2015/6/16.
 */
public class ContactApplication extends Application {
    public static final String LOGIN_PREFERENCES = "login_preferences";
    private MyDBHelper myhelper;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("InfoApplication", "onCreate");


    }


    /**
     * 保存人员信息
     */
    public static void saveInfo(Context context, OALoginResponse info) {
        SharedPreferences preferences = context.getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(info);
        prefsEditor.putString("OALoginResponse", json);
        prefsEditor.apply();
    }

    /**
     * 读取人员信息
     */
    public static OALoginResponse loadInfo(Context context) {
        SharedPreferences mPreferences = context.getSharedPreferences(LOGIN_PREFERENCES, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPreferences.getString("OALoginResponse", "");
        return gson.fromJson(json, OALoginResponse.class);
    }

    /**
     * 清理人员信息
     */
    public static void cleanInfo(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = preferences.edit();
        prefsEditor.clear().apply();
    }

    /**
     * 注销并退出
     * 人员信息为空,accessKey过期,
     */
    public static void logoutAndExit(Activity activity) {
        LoginUtils.startLoginActivity(activity, LoginProtocol.REQUEST_LOGOUT, Flavors.SERVER_TYPE);
        activity.finish();
    }


}
