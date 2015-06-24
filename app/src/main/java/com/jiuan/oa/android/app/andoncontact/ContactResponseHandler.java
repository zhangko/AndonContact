package com.jiuan.oa.android.app.andoncontact;

import android.app.Activity;
import android.widget.Toast;

import com.jiuan.oa.android.app.andoncontact.oahttplibrary.OAHttpResponseHandler;

/**
 * Created by ZhangKong on 2015/6/16.
 */
public class ContactResponseHandler extends OAHttpResponseHandler {
    /**
     * AccessKey错误
     */
    private static final String FAILURE_210_1 = "210.1";

    /**
     * AccessKey过期
     */
    private static final String FAILURE_210_2 = "210.2";

    /**
     * 上传数据不完整，或格式不正确
     */
    private static final String FAILURE_230 = "230";

    private Activity mContext;

    public ContactResponseHandler(Activity context) {
        mContext = context;
    }

    @Override
    public void onOAFailure(String msg) {
        if (msg.equals(FAILURE_210_1)) {
            onInfoFailure("AccessKey错误");
        } else if (msg.equals(FAILURE_210_2)) {
            onInfoFailure("AccessKey过期");
        } else if (msg.equals(FAILURE_230)) {
            onInfoFailure("上传数据不完整，或格式不正确");
        }
    }

    public void onInfoFailure(String value) {

    }

    @Override
    public void onOAError(String value) {
        Toast.makeText(mContext, value, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOAExceptionFinish() {
        Toast.makeText(mContext, "网络连接异常", Toast.LENGTH_SHORT).show();
    }
}
