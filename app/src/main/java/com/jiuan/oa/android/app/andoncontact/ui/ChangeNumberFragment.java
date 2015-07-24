package com.jiuan.oa.android.app.andoncontact.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import com.jiuan.oa.android.app.andoncontact.R;


import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;
import greendao.DaoMaster;
import greendao.DaoSession;
import greendao.Staff;
import greendao.StaffDao;

/**
 * Created by ZhangKong on 2015/7/17.
 */
public class ChangeNumberFragment extends DialogFragment {

    private SQLiteDatabase db;

    private DaoSession daoSession;

    private String string;

    private StaffDao staffDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                QueryBuilder staffqb = staffDao.queryBuilder().where(StaffDao.Properties.Code.eq(PeopleActivity.number));
                List<Staff> list = staffqb.list();
                Staff staff = list.get(0);
                staff.setMobile(string);
                staffDao.update(staff);

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
