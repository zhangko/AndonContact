package com.jiuan.oa.android.app.andoncontact.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.jiuan.oa.android.app.andoncontact.ChangeNumberRequest;
import com.jiuan.oa.android.app.andoncontact.R;
import com.jiuan.oa.android.app.andoncontact.database.MyDBHelper;

import org.w3c.dom.Text;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;
import greendao.DaoMaster;
import greendao.DaoSession;
import greendao.Staff;
import greendao.StaffDao;

/**
 * Created by ZhangKong on 2015/6/18.
 */
public class PeopleActivity extends Activity implements View.OnClickListener,View.OnTouchListener{



    private SQLiteDatabase db;

    private DaoSession daoSession;

    String name = "";
    public static String number = "";
    String telephone = ""  ;
    String phone = "" ;
    String email = "" ;

    private String code;



    private StaffDao staffDao;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_peoplelayout);


       code = getIntent().getStringExtra("MSG").toString();

        DaoMaster.DevOpenHelper myhelper = new DaoMaster.DevOpenHelper(this,"address.db",null);
        db = myhelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();

        staffDao = daoSession.getStaffDao();
        QueryBuilder staffqb = staffDao.queryBuilder().where(StaffDao.Properties.Code.eq(code));
        List<Staff> list = staffqb.list();


            name =  list.get(0).getName();
            number = list.get(0).getCode();

            //这个是手机号码
            telephone = list.get(0).getMobile();
            phone =  list.get(0).getTelephone();
            email =  list.get(0).getEmail();
           initView();



    }

    private void initView(){

        TextView employ = (TextView)findViewById(R.id.employeename);
        employ.setText(name);
        TextView mobile = (TextView)findViewById(R.id.mobile);
        if(TextUtils.isEmpty(telephone)){

            mobile.setText("");
        }
        else{
            mobile.setText(convertMyString(telephone));
        }

        TextView codeText = (TextView)findViewById(R.id.code);
        codeText.setText(code);
        TextView emailText = (TextView)findViewById(R.id.email);
        emailText.setText(email);
        ImageButton message = (ImageButton)findViewById(R.id.message);
        message.setOnTouchListener(this);
        message.setOnClickListener(this);
        ImageButton call = (ImageButton)findViewById(R.id.callphone);
        call.setOnTouchListener(this);
        call.setOnClickListener(this);
        ImageButton returnbutton = (ImageButton)findViewById(R.id.returnbutton);
        returnbutton.setOnTouchListener(this);
        returnbutton.setOnClickListener(this);
        ImageButton edit = (ImageButton)findViewById(R.id.edit);
        edit.setOnTouchListener(this);
        edit.setOnClickListener(this);
        TextView infoTextview = (TextView)findViewById(R.id.peopleinfo);
        infoTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.callphone:
                Uri uri = Uri.parse("tel:" + telephone);
                Intent it = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(it);
                break;
            case R.id.message:
                Uri smsToUri = Uri.parse("smsto:" + telephone);
                Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
                startActivity(intent);
                break;
            case R.id.returnbutton:

                finish();
                break;
            case R.id.edit:
                ChangeNumberFragment changeNumberFragment = new ChangeNumberFragment();
                changeNumberFragment.show(getFragmentManager(),"changnumber");
                break;

        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            v.setBackgroundColor(getResources().getColor(android.R.color.background_dark));
        }else if(event.getAction() == MotionEvent.ACTION_UP){
            v.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        }
        return false;
    }

    private String convertMyString(String string){
        String tmp = string.substring(0,3) + "-" + string.substring(3,7) + "-" + string.substring(7);
        return tmp;

    }
}
