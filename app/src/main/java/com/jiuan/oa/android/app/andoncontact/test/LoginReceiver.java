package com.jiuan.oa.android.app.andoncontact.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.jiuan.oa.android.app.andoncontact.ContactApplication;
import com.jiuan.oa.android.library.http.login.OALoginResponse;

/**
 * Created by ZhangKong on 2015/6/16.
 */
public class LoginReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v("MSG","收到消息了！！");
        Bundle bundle = intent.getExtras();
        OALoginResponse info = bundle.getParcelable("OALoginResponse");
        Log.v("MSG","  " + info.toString());
        ContactApplication.saveInfo(context, info);
    }
}
