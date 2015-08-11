package com.jiuan.oa.android.app.andoncontact.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.jiuan.oa.android.app.andoncontact.ContactApplication;

/**
 * Created by ZhangKong on 2015/6/16.
 */
public class LogoutReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ContactApplication.cleanInfo(context);
    }
}
