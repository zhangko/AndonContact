package com.jiuan.oa.android.app.andoncontact.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jiuan.oa.android.app.andoncontact.ContactApplication;
import com.jiuan.oa.android.app.andoncontact.ContactClient;
import com.jiuan.oa.android.app.andoncontact.R;
import com.jiuan.oa.android.app.andoncontact.oahttplibrary.OAHttpResponseHandler;
import com.jiuan.oa.android.library.http.login.OALoginResponse;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;
import greendao.DaoMaster;
import greendao.DaoSession;
import greendao.Staff;
import greendao.StaffDao;

/**
 * Created by ZhangKong on 2015/7/17.
 */
public class FireMisslesDialogFragment extends DialogFragment {

    private String userID;

    private String account;

    private String accessKey;

    private String timeStamp;

    private SQLiteDatabase db;

    private DaoSession daoSession;

   private String string;

    private StaffDao staffDao;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        OALoginResponse oaLoginResponse = ContactApplication.loadInfo(getActivity());
        Log.d("MSG"," "  + oaLoginResponse.getUserID());
        Log.d("MSG"," " + oaLoginResponse.getAccessKey());
        Log.d("MSG","  " + oaLoginResponse.getAccount());
        userID = oaLoginResponse.getUserID();
        accessKey = oaLoginResponse.getAccessKey();
        account = oaLoginResponse.getAccount();

        long time = System.currentTimeMillis();
        timeStamp = Long.toString(time/1000)  ;
        Log.d("TimeStamp", " "  + timeStamp);

        DaoMaster.DevOpenHelper myhelper = new DaoMaster.DevOpenHelper(getActivity(),"address.db",null);
        db = myhelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        staffDao = daoSession.getStaffDao();



    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the Builder class for convenient dialog construction
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);
        final TextView textView = (TextView)view.findViewById(R.id.newnumber);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("修改号码：");
        builder.setView(view);
        // builder.setView(R.layout.layout_dialog);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
             string = textView.getText().toString();
                ContactClient.requestChangeNumber(getActivity(),account,userID,accessKey,timeStamp,string,new OAHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                        Log.d("onSuccess","  " + response.toString());
                        QueryBuilder staffqb = staffDao.queryBuilder().where(StaffDao.Properties.Code.eq(account));
                        List<Staff> list = staffqb.list();
                        Staff staff = list.get(0);
                        staff.setMobile(string);
                        staffDao.update(staff);

                        Log.d("MSG","修改成功！！！");
                    }
                });
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }
}
