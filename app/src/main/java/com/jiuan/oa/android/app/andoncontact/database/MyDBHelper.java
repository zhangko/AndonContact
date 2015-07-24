package com.jiuan.oa.android.app.andoncontact.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jiuan.oa.android.app.andoncontact.convertpinyin.HanziToPinyin;
import com.jiuan.oa.android.app.andoncontact.response.DepartmentResponse;
import com.jiuan.oa.android.app.andoncontact.response.EmployResponse;

import java.util.List;

/**
 * Created by ZhangKong on 2015/6/17.
 */
public class MyDBHelper extends SQLiteOpenHelper {

    // 声明数据库名字和数据库中的表
    private static final String DB_NAME = "address.db";
//    private static final String Companytable = "companytable";
//    private static final String Contacttable = "contacttable";
    public SQLiteDatabase mydatabase;

    public MyDBHelper(Context context)
    {
        super(context ,DB_NAME,null,1);
    }
    public void onCreate(SQLiteDatabase db){
        this.mydatabase = db;

        String table1 = "create table companytable(_id integer primary key autoincrement,Name varchar, ID varchar, ParentID varchar,Code varchar,Abbre varchar,IsCompany integer)";
        String table2 = "create table contacttable(_id integer primary key autoincrement,Name varchar,Code varchar,ID varchar,DepartmentID varchar,DepartmentCode varchar,Mobile varchar,Telephone varchar,Email varchar,FullName varchar,ShortName varchar,Sex integer)";
        mydatabase.execSQL(table1);
        mydatabase.execSQL(table2);


    }
    public void insert(String tablename,ContentValues values){
        SQLiteDatabase db = getWritableDatabase();
        db.insert(tablename,null,values);
    }

    public void insert(String tablename,List<EmployResponse> employlist){
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try{
            //员工总数
            int index = employlist.size();
            for(int i = 0; i< index; i++){
                EmployResponse employResponse = employlist.get(i);
                ContentValues values = new ContentValues();
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
                db.insert(tablename,null,values);
            }
            db.setTransactionSuccessful();

        }finally {
            db.endTransaction();
        }

    }

    public void insertCompany(String tablename,List<DepartmentResponse> departmentResponseList){
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try{
            //员工总数
            int index = departmentResponseList.size();
            for(int i = 0; i< index; i++){
               DepartmentResponse departmentResponse = departmentResponseList.get(i);
                ContentValues values = new ContentValues();
                values.put("Name", departmentResponse.getName());
                values.put("Code", departmentResponse.getCode());
                values.put("ID", departmentResponse.getId());
                values.put("ParentID", departmentResponse.getParentid());
                values.put("Abbre", departmentResponse.getAbbreviation());
                values.put("IsCompany", departmentResponse.getIscompany());
                db.insert(tablename,null,values);
            }
            db.setTransactionSuccessful();

        }finally {
            db.endTransaction();
        }

    }
    public Cursor query(String tablename){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(tablename,null,null,null,null,null,null);
        return c;
    }
    public Cursor query(String tablename,String parentid){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(tablename,null,"ParentID = ?",new String[]{parentid},null,null,null);
        return c;
    }

    public Cursor contactquery(String tablename,String departmentid){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(tablename,null,"DepartmentID = ?",new String[] {departmentid},null,null,null);
        return  c;
    }
    public Cursor companyquery(String tablename){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(tablename,null,"IsCompany = 1",null,null,null,null);
        return c;
    }
    public Cursor codequery(String tablename,String code){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(tablename,null,"Code = ?",new String[]{code},null,null,null);
        return c;
    }
    public Cursor namequery(String tablename,String namequery){
        SQLiteDatabase db =getReadableDatabase();
        Cursor c= db.query(tablename,null,"Name like ?",new String[]{namequery + "%"},null,null,null);
        return c;
    }
    public Cursor telephonequery(String tablename,String telephone){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(tablename,null,"Mobile like ?",new String[]{telephone + "%"},null,null,null);
        return c;
    }
    public Cursor departmentquery(String tablename,String id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(tablename,null,"ID = ?",new String[]{id},null,null,null);
        return c;
    }
    public Cursor bothquery(String tablename, String bothstring){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(tablename,null,"FullName like ? or ShortName like ? or Mobile like ?",new String[]{bothstring + "%",bothstring + "%",bothstring + "%"},null,null,null);
        return c;
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion ,int newVersion){

    }


}
