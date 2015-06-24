package com.jiuan.oa.android.app.andoncontact.ui;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiuan.oa.android.app.andoncontact.ContactApplication;
import com.jiuan.oa.android.app.andoncontact.ContactClient;
import com.jiuan.oa.android.app.andoncontact.ContactResponseHandler;
import com.jiuan.oa.android.app.andoncontact.R;
import com.jiuan.oa.android.app.andoncontact.convertpinyin.HanziToPinyin;
import com.jiuan.oa.android.app.andoncontact.database.MyDBHelper;
import com.jiuan.oa.android.app.andoncontact.response.DepartmentResponse;
import com.jiuan.oa.android.app.andoncontact.response.EmployResponse;
import com.jiuan.oa.android.library.http.login.OALoginResponse;

import java.util.List;

/**
 * Created by ZhangKong on 2015/6/18.
 */
public class LoginActivity extends ActionBarActivity {

    private MyDBHelper myhelper;

    private ProgressDialog LoadingDialog;

    private SharedPreferences sp;

    private boolean isFirst;

    private SharedPreferences.Editor editor;

    private String company;

    private String contact;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginlayout);

        myhelper = new MyDBHelper(this);
        Log.d("MSG", "开始执行oncreate");
        LoadingDialog = new ProgressDialog(this);
        LoadingDialog.setMessage("Please wait while loading...");
        LoadingDialog.setIndeterminate(true);
        LoadingDialog.setCancelable(false);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        editor = sp.edit();
        isFirst = sp.getBoolean("login", false);
        Log.d("MSG", "显示对话框");
        LoadingDialog.show();
        if (!isFirst) {
            getDepartMent();
            Log.d("MSG", "读取完成部门列表");
            getContact();
            Log.d("MSG", "读取完成人员列表");
            Log.v("MSG", "取消对话框");
        } else {
            LoadingDialog.cancel();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        Log.d("onDestroy", "onDestroy");
        super.onDestroy();
    }

    private void getDepartMent() {
        OALoginResponse oaLoginResponse = ContactApplication.loadInfo(this);
        if (oaLoginResponse == null) {
            Log.d("oaLoginResponse", "为空");
            ContactApplication.logoutAndExit(this);
            Log.d("finish", "0");
            finish();
            Log.d("finish", "1");
            return;
        }

        Log.v("OALOGINRESPONSE", " " + oaLoginResponse.toString());
        Log.v("MSG", " " + oaLoginResponse.getAccount().toString());
        Log.v("MSG", " " + oaLoginResponse.getAccessKey().toString());
        Log.v("MSG", " " + oaLoginResponse.getUserID().toString());
        ContactClient.requestDepartment(this, oaLoginResponse.getAccount(), oaLoginResponse.getUserID(), oaLoginResponse.getAccessKey(), new ContactResponseHandler(this) {
            @Override
            public void onOASuccess(String value) {
                if (null == value) {
                    return;
                }
                Log.d("onOASuccess", value);
                company = value;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();
                        List<DepartmentResponse> DepartMentList = gson.fromJson(company, new TypeToken<List<DepartmentResponse>>() {
                        }.getType());
                        Log.v("MSG", "部门数：" + DepartMentList.size());
                        ContentValues values = new ContentValues();
                        int index = 0;
                        for (int i = 0; i < DepartMentList.size(); i++) {
                            DepartmentResponse departmentResponse = DepartMentList.get(i);
                            values.put("Name", departmentResponse.getName());
                            values.put("Code", departmentResponse.getCode());
                            values.put("ID", departmentResponse.getId());
                            values.put("ParentID", departmentResponse.getParentid());
                            values.put("Abbre", departmentResponse.getAbbreviation());
                            values.put("IsCompany", departmentResponse.getIscompany());
                            myhelper.insert("companytable", values);
                            index++;

                        }
                        Log.v("MSG", "部门数：" + index);
                        Log.d("MSG", "部门数据库生成成功！");
                    }
                }).start();


            }
        });
    }

    private void customStop() {
        Log.d("customStop", "执行");
    }

    private void getContact() {

        OALoginResponse oaLoginResponse = ContactApplication.loadInfo(this);
        if (oaLoginResponse == null) {
            ContactApplication.logoutAndExit(this);
            return;
        }

        Log.v("OALOGINRESPONSE", " " + oaLoginResponse.toString());
        Log.v("INFO", " " + oaLoginResponse.getAccount().toString());
        Log.v("INFO", " " + oaLoginResponse.getAccessKey().toString());
        Log.v("INFO", " " + oaLoginResponse.getUserID().toString());
        ContactClient.requestEmployee(this, oaLoginResponse.getAccount(), oaLoginResponse.getUserID(), oaLoginResponse.getAccessKey(), new ContactResponseHandler(this) {
            @Override
            public void onOASuccess(String value) {
                if (null == value) {
                    return;
                }
                Log.d("onOASuccess", value);
                contact = value;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();
                        List<EmployResponse> EmployList = gson.fromJson(contact, new TypeToken<List<EmployResponse>>() {
                        }.getType());
                        Log.v("MSG", "公司人数：" + EmployList.size());
                        Log.v("MSG", "个人信息：" + EmployList.get(12).toString());
                        Log.v("MSG", "  " + EmployList.get(12).getName().toString() + EmployList.get(12).getCode().toString());
                        ContentValues values = new ContentValues();
                        int index = 0;
                        for (int i = 0; i < EmployList.size(); i++) {
                            EmployResponse employResponse = EmployList.get(i);
                            values.put("Name", employResponse.getName());
                            values.put("Code", employResponse.getCode());
                            values.put("ID", employResponse.getId());
                            values.put("DepartmentID", employResponse.getDepartmentid());
                            values.put("DepartmentCode", employResponse.getDepartmentcode());
                            values.put("Mobile", employResponse.getMobile());
                            values.put("Telephone", employResponse.getTelephone());
                            values.put("Email", employResponse.getEmail());
                            values.put("FullName", HanziToPinyin.getFullPinYin(employResponse.getName()));
                            values.put("ShortName", HanziToPinyin.getPinyin(employResponse.getName()));
                            values.put("Sex", employResponse.getSex());
                            myhelper.insert("contacttable", values);

                            index++;
                        }
                        Log.v("MSG", "公司人数：" + index);
                        Log.d("MSG", "公司员工数据库生成！！");
                        LoadingDialog.cancel();
                        editor.putBoolean("login", true);
                        editor.commit();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).start();
            }
        });
    }
}
