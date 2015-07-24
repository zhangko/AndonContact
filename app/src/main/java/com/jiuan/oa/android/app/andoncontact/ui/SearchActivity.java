package com.jiuan.oa.android.app.andoncontact.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.jiuan.oa.android.app.andoncontact.R;
import com.jiuan.oa.android.app.andoncontact.database.MyDBHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;
import greendao.DaoMaster;
import greendao.DaoSession;
import greendao.Staff;
import greendao.StaffDao;

/**
 * Created by ZhangKong on 2015/6/18.
 */
public class SearchActivity extends ActionBarActivity implements SearchView.OnQueryTextListener {

    private ListView mylistview;
    private MyDBHelper myhelper;
    // private Cursor cursor;
    private String department;
    private  List<Map<String,String>> listitem;

    private SQLiteDatabase db;

    private DaoSession daoSession;

    private StaffDao staffDao;

    private List<Staff> staff_list;

    private String code;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchlayout);
        myhelper = new MyDBHelper(this);
        Button button = (Button)findViewById(R.id.cancel);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.this.finish();
            }
        });
        mylistview = (ListView)findViewById(R.id.searchlistview);
        SearchView mysearchview = (SearchView)findViewById(R.id.seachview);
        mysearchview.setOnQueryTextListener(this);
        mysearchview.setIconifiedByDefault(false);
        //  mSearchView.setOnQueryTextListener(this);
        mysearchview.setSubmitButtonEnabled(false);
        DaoMaster.DevOpenHelper myhelper = new DaoMaster.DevOpenHelper(this,"address.db",null);
        db = myhelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        staffDao = daoSession.getStaffDao();
        mylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*cursor.moveToPosition(position);
                String code = cursor.getString(2);*/
                code = staff_list.get(position).getCode();
                if (TextUtils.isEmpty(code)){
                    Log.d("MSG","  code is empty!!!" );
                    return;
                }
                Log.d("MSG","  " + code);
                Intent intent = new Intent(SearchActivity.this,PeopleActivity.class);
                intent.putExtra("MSG",code);
                startActivity(intent);
            }
        });
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //判断数据是否为空
        if(TextUtils.isEmpty(newText)){
            mylistview.setAdapter(null);
            return true;
        }


        String query = newText.toString();

        QueryBuilder queryBuilder = staffDao.queryBuilder().whereOr(StaffDao.Properties.Fullname.like(query + "%"),StaffDao.Properties.Shortname.like(query + "%"),StaffDao.Properties.Mobile.like(query + "%"),StaffDao.Properties.Name.like( query +"%"));
         staff_list = queryBuilder.list();
       Log.d("MSG","  " + staff_list.size());

       SearchAdapter adapter = new SearchAdapter(this,staff_list);
        mylistview.setAdapter(adapter);

        return true;
    }

    private class SearchAdapter extends BaseAdapter{

        private List<Staff> staffs;

        private Context mcontext;

        public SearchAdapter(Context context,List<Staff> list){
            mcontext = context;
            staffs = list;
        }



        @Override
        public int getCount() {
            return staffs.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder ;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.listitemlayout, null);
                viewHolder.nameText = (TextView)convertView.findViewById(R.id.nametext);
                viewHolder.codeText = (TextView)convertView.findViewById(R.id.worknumber);
                viewHolder.departmentText = (TextView)convertView.findViewById(R.id.telephone);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
           viewHolder.nameText.setText("姓名：" + staffs.get(position).getName());
            viewHolder.codeText.setText("工号：" + staffs.get(position).getCode());
            viewHolder.departmentText.setText("手机：" + staffs.get(position).getMobile());
            return convertView;
        }
    }
    private class ViewHolder{
        TextView nameText;
        TextView departmentText;
        TextView codeText;
    }
}





