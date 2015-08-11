package com.jiuan.oa.android.app.andoncontact.im;

import java.util.Random;

/**
 * Created by ZhangKong on 2015/8/3.
 *
 */
public class RandString {

    private static final String STR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String getString(int length){
        StringBuilder stringBuilder = new StringBuilder();
        Random rand = new Random();
        for(int i = 0; i < length; i++){
            int number = rand.nextInt(62);
            stringBuilder.append(STR.charAt(number));
        }

        return stringBuilder.toString();
    }

    public static String getTimeStamp(){
        long time =  System.currentTimeMillis();
        String timestamp = Long.toString(time);
        return timestamp;
    }
}
