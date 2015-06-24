package com.jiuan.oa.android.app.andoncontact.ui;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jiuan.oa.android.app.andoncontact.R;
import com.jiuan.oa.android.app.andoncontact.database.MyDBHelper;

/**
 * Created by ZhangKong on 2015/6/18.
 */
public class PeopleActivity extends ActionBarActivity {

    private MyDBHelper myhelper;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.peoplelayout);
        myhelper = new MyDBHelper(this);
        String name = "";
        String number = "";
        String telephone = ""  ;
        String phone = "" ;
        String email = "" ;
        String code = getIntent().getStringExtra("MSG").toString();
        Cursor cursor = myhelper.codequery("contacttable",code);
        if(cursor.moveToFirst()){
            name =  cursor.getString(1);
            number = cursor.getString(2);
            telephone = cursor.getString(6);
            phone =  cursor.getString(7);
            email =  cursor.getString(8);
        }

        TextView nametext = (TextView)findViewById(R.id.name);
        nametext.setText(name);
        TextView numbertext = (TextView)findViewById(R.id.number);
        numbertext.setText(number);
        final TextView teleText = (TextView)findViewById(R.id.telephone);
        teleText.setText(telephone);
        final TextView phoneText = (TextView)findViewById(R.id.phone);
        phoneText.setText(phone);
       teleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MSG"," 点击了电话号码！！  " );
               String persontele = teleText.getText().toString();

                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + persontele));
                startActivity(intent);

            }
        });
        TextView emainText = (TextView)findViewById(R.id.email);
        emainText.setText(email);
        Button backbutton = (Button)findViewById(R.id.fanhui);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PeopleActivity.this.finish();
            }
        });
    }
}
