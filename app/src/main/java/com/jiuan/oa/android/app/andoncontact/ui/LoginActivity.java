package com.jiuan.oa.android.app.andoncontact.ui;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiuan.oa.android.app.andoncontact.ContactApplication;
import com.jiuan.oa.android.app.andoncontact.ContactClient;
import com.jiuan.oa.android.app.andoncontact.ContactResponseHandler;
import com.jiuan.oa.android.app.andoncontact.R;
import com.jiuan.oa.android.app.andoncontact.TestActivity;
import com.jiuan.oa.android.app.andoncontact.convertpinyin.HanziToPinyin;
import com.jiuan.oa.android.app.andoncontact.database.MyDBHelper;
import com.jiuan.oa.android.app.andoncontact.response.DepartmentResponse;
import com.jiuan.oa.android.app.andoncontact.response.EmployResponse;
import com.jiuan.oa.android.library.http.login.OALoginResponse;

import java.util.ArrayList;
import java.util.List;

import greendao.DaoMaster;
import greendao.DaoSession;
import greendao.Department;
import greendao.DepartmentDao;
import greendao.Staff;
import greendao.StaffDao;

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

    private SQLiteDatabase db;

    private DaoSession daoSession;

    private DaoSession departmentDaoSession;

    private StaffDao staffDao;

    private DepartmentDao departmentDao;

    private int index = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginlayout);

     //   myhelper = new MyDBHelper(this);
        Log.d("MSG", "开始执行oncreate");
        DaoMaster.DevOpenHelper myhelper = new DaoMaster.DevOpenHelper(this,"address.db",null);
        db = myhelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        departmentDaoSession = daoMaster.newSession();
        staffDao = daoSession.getStaffDao();
        departmentDao = departmentDaoSession.getDepartmentDao();
        LoadingDialog = new ProgressDialog(this);
        LoadingDialog.setMessage("正在下载人员信息列表...");
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
                getContact();
                Log.d("MSG", "读取完成人员列表");
                Log.v("MSG", "取消对话框");

                // TODO
               /*getDepartMent();
                index++;
                Log.d("MSG","这时第" + index + "次");*/
                company = value;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();
                        List<DepartmentResponse> DepartMentList = gson.fromJson(company, new TypeToken<List<DepartmentResponse>>() {
                        }.getType());
                        Log.v("MSG", "部门数：" + DepartMentList.size());
                        long number = DepartMentList.size();
                        List<Department> listDepartment = new ArrayList<Department>();
                        for(int i = 0; i < number; i++){
                            DepartmentResponse departmentResponse = DepartMentList.get(i);
                            Long l = new Long((long)i);
                            Department department = new Department(l);
                            department.setAbbreviation(departmentResponse.getAbbreviation());
                            department.setCode(departmentResponse.getCode());
                            department.setDepartmentID(departmentResponse.getId());
                            department.setCode(departmentResponse.getCode());
                            department.setIsCompany(departmentResponse.getIscompany());
                            department.setName(departmentResponse.getName());
                            department.setParentID(departmentResponse.getParentid());
                            listDepartment.add(department);
                        }

                        long begintime = System.currentTimeMillis();
                        departmentDao.insertInTx(listDepartment);
                        long endtime = System.currentTimeMillis();
                        Log.d("存储时间为"," " + (begintime - endtime) + "毫秒");

                     //   myhelper.insertCompany("companytable",DepartMentList);
                        Log.v("MSG", "部门数：" +DepartMentList.size());
                        Log.d("MSG", "部门数据库生成成功！");
                    }
                }).start();
            }
            @Override
            public void onOAExceptionFinish(){
                Toast.makeText(LoginActivity.this,"当前网络异常，请重新下载",Toast.LENGTH_SHORT).show();
                Log.d("Department","department网络异常");
                LoadingDialog.cancel();
                deleteDatabase("address.db");
                finish();


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

                // TODO
              /*  getContact();
                index++;
                Log.d("MSG","这时第" + index + "次");*/

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

                        long number = EmployList.size();
                        List<Staff> listStaff = new ArrayList<Staff>();

                        for( int i = 0; i < number; i++){
                            EmployResponse  employResponse = EmployList.get(i);
                            Long l = new Long((long)i);
                            Staff staff = new Staff(l);
                            staff.setName(employResponse.getName());
                            staff.setCode(employResponse.getCode());
                            staff.setStaffID(employResponse.getId());
                            staff.setDepartmentCode(employResponse.getDepartmentcode());
                            staff.setDepartmentID(employResponse.getDepartmentid());
                            staff.setEmail(employResponse.getEmail());
                            staff.setMobile(employResponse.getMobile());
                            staff.setTelephone(employResponse.getTelephone());
                            staff.setIsMainDepartment(employResponse.getIsmaindepartment());
                            staff.setGender(employResponse.getSex());
                            staff.setFullname(HanziToPinyin.getFullPinYin(employResponse.getName()));
                            staff.setShortname(HanziToPinyin.getPinyin(employResponse.getName()));
                            listStaff.add(staff);

                        }

                        long begintime = System.currentTimeMillis();
                        staffDao.insertInTx(listStaff);
                        long endtime = System.currentTimeMillis();
                        Log.d("存储时间为"," " + (begintime - endtime) + "毫秒");
                        Log.v("MSG", "公司人数：" + EmployList.size());
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

            @Override
            public void onOAExceptionFinish(){
                Toast.makeText(LoginActivity.this,"当前网络异常，请重新下载",Toast.LENGTH_SHORT).show();
                Log.d("Contact","Contact网络异常！");
                LoadingDialog.cancel();
                deleteDatabase("adress.db");
                finish();


            }
        });
    }
}
