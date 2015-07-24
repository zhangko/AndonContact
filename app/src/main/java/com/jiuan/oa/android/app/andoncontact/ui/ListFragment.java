package com.jiuan.oa.android.app.andoncontact.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jiuan.oa.android.app.andoncontact.R;
import com.jiuan.oa.android.app.andoncontact.TreeNode;
import com.jiuan.oa.android.app.andoncontact.database.MyDBHelper;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;
import greendao.DaoMaster;
import greendao.DaoSession;
import greendao.Department;
import greendao.DepartmentDao;
import greendao.Staff;
import greendao.StaffDao;

/**
 * Created by ZhangKong on 2015/6/18.
 */
public class ListFragment extends Fragment {





    private static final String ARG_SECTION_NUMBER = "section_number";



    private ArrayList<TreeNode> nodelist = new ArrayList<TreeNode>();


    private TreeViewAdapter adapter = null;
    private MyDBHelper myhelper;

    private List<String> companyID = new ArrayList<String>();

    private SQLiteDatabase db;

    private DaoSession daoSession;

    private DaoSession staffDaoSession;

    private DepartmentDao departmentDao;

    private StaffDao staffDao;

    public void ListFragment(){

    }

    public static ListFragment newInstance(int sectionNumber){
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // If the drawer is open, show the global app actions in the action bar. See also
        // showGlobalContextActionBar, which controls the top-left area of the action bar.
       getActivity().getMenuInflater().inflate(R.menu.main, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if(item.getItemId() == R.id.action_quit){
            Toast.makeText(getActivity(), "dian ji le tui chu an niu!", Toast.LENGTH_SHORT).show();
        }

        if (item.getItemId() == R.id.action_example) {

            Intent intent = new Intent(getActivity(),SearchActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DaoMaster.DevOpenHelper myhelper = new DaoMaster.DevOpenHelper(getActivity(),"address.db",null);
        db = myhelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        staffDaoSession = daoMaster.newSession();
        staffDao = staffDaoSession.getStaffDao();
        departmentDao = daoSession.getDepartmentDao();
        QueryBuilder qb = departmentDao.queryBuilder().where(DepartmentDao.Properties.IsCompany.eq(1));
        List<Department> list = qb.list();
        Log.d("一共有总司数："," " + list.size());

        for(int i = 0; i < list.size(); i++){
            companyID.add(list.get(i).getDepartmentID());
            Log.d("MSG"," " + list.get(i).getDepartmentID());
        }
      String departmentid = companyID.get(getArguments().getInt(ARG_SECTION_NUMBER));

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        QueryBuilder department_qb = departmentDao.queryBuilder().where(DepartmentDao.Properties.ParentID.eq(departmentid));

        List<Department> first_department = new ArrayList<Department>();
        first_department = department_qb.list();
        for(int i = 0; i < first_department.size();i++){
            TreeNode tempnode = new TreeNode();
            tempnode.setName(first_department.get(i).getName());
            tempnode.setHaveChild(true);
            tempnode.setLevel(1);
            tempnode.setExpanded(false);
            tempnode.setDepartmenID(first_department.get(i).getDepartmentID());
            tempnode.setHaveParent(false);
            tempnode.setID(first_department.get(i).getDepartmentID());
            tempnode.setParent("");
            nodelist.add(tempnode);
        }

        ListView mylistview = (ListView)rootView.findViewById(R.id.treelist);
           adapter = new TreeViewAdapter(getActivity(),R.layout.outline,nodelist);
        mylistview.setAdapter(adapter);
        mylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(nodelist.get(position).isHaveChild()){
                    if(nodelist.get(position).isExpanded()){
                        nodelist.get(position).setExpanded(false);
                        TreeNode temptreenode = nodelist.get(position);
                        ArrayList<TreeNode> temp =  new ArrayList<TreeNode>();
                        for(int i = position + 1; i< nodelist.size();i++){
                            if( temptreenode.getLevel() >= nodelist.get(i).getLevel() ){
                                break;
                            }
                            temp.add(nodelist.get(i));
                        }
                        nodelist.removeAll(temp);
                        adapter.notifyDataSetChanged();
                    }
                    else {
                        nodelist.get(position).setExpanded(true);
                        int level = nodelist.get(position).getLevel();
                        int nextlevel = level + 1;
                        QueryBuilder second_department_qb = departmentDao.queryBuilder().where(DepartmentDao.Properties.ParentID.eq(nodelist.get(position).getDepartmenID()));
                        List<Department> list_second_department = second_department_qb.list();
                        if (list_second_department.size() != 0) {
                            for (int i = 0; i < list_second_department.size(); i++) {
                                TreeNode templistnode = new TreeNode();
                                templistnode.setName(list_second_department.get(i).getName());
                                templistnode.setHaveChild(true);
                                templistnode.setLevel(nextlevel);
                                templistnode.setExpanded(false);
                                templistnode.setDepartmenID(list_second_department.get(i).getDepartmentID());
                                templistnode.setHaveParent(true);
                                templistnode.setID(list_second_department.get(i).getDepartmentID());
                                templistnode.setParent(list_second_department.get(i).getDepartmentID());
                                nodelist.add(position + 1, templistnode);
                            }

                        QueryBuilder first_contact_qb = staffDao.queryBuilder().where(StaffDao.Properties.DepartmentID.eq(nodelist.get(position).getDepartmenID()));
                        List<Staff> first_contact_list = first_contact_qb.list();
                        for (int i = 0; i < first_contact_list.size(); i++) {
                            TreeNode templistnode = new TreeNode();
                            templistnode.setName(first_contact_list.get(i).getName());
                            templistnode.setHaveChild(false);
                            templistnode.setLevel(nextlevel);
                            templistnode.setExpanded(false);
                            templistnode.setDepartmenID(first_contact_list.get(i).getDepartmentID());
                            templistnode.setHaveParent(true);
                            templistnode.setID(first_contact_list.get(i).getCode());
                            templistnode.setParent(first_contact_list.get(i).getCode());
                            templistnode.setCode(first_contact_list.get(i).getCode());
                            nodelist.add(position + 1, templistnode);
                        }

                        } else{
                            QueryBuilder second_contact_qb = staffDao.queryBuilder().where(StaffDao.Properties.DepartmentID.eq(nodelist.get(position).getDepartmenID()));
                            List<Staff> second_contact_list = second_contact_qb.list();
                            for(int i = 0; i < second_contact_list.size();i++){
                                TreeNode templistnode = new TreeNode();
                                templistnode.setName(second_contact_list.get(i).getName());
                                templistnode.setHaveChild(false);
                                templistnode.setLevel(nextlevel);
                                templistnode.setExpanded(false);
                                templistnode.setDepartmenID(second_contact_list.get(i).getDepartmentID());
                                templistnode.setHaveParent(true);
                                templistnode.setID(second_contact_list.get(i).getCode());
                                templistnode.setParent(second_contact_list.get(i).getCode());
                                templistnode.setCode(second_contact_list.get(i).getCode());
                                nodelist.add(position + 1 ,templistnode);
                            }

                        }

                        adapter.notifyDataSetChanged();
                    }
                  }
                else{
                    String peoplecode = nodelist.get(position).getCode();
                    Intent intent = new Intent(getActivity(),PeopleActivity.class);
                    intent.putExtra("MSG",peoplecode);
                    startActivity(intent);

                }


            }
        });

               return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myhelper = new MyDBHelper(activity);

    }

    private class TreeViewAdapter extends ArrayAdapter {

        private LayoutInflater mInflater;
        private List<TreeNode> mfilelist;
        private Bitmap mIconCollapse;
        private Bitmap mIconExpand;
        private Bitmap mPerson;

        public TreeViewAdapter(Context context, int textViewResourceId,
                               List objects) {
            super(context, textViewResourceId, objects);
            mInflater = LayoutInflater.from(context);
            mfilelist = objects;
            mIconCollapse = BitmapFactory.decodeResource(
                    context.getResources(), R.drawable.outline_list_collapse);
            mIconExpand = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.outline_list_expand);
            mPerson = BitmapFactory.decodeResource(context.getResources(),R.drawable.person);


        }

        public int getCount() {
            return mfilelist.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
			/*if (convertView == null) {*/
            convertView = mInflater.inflate(R.layout.outline, null);
            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.text);
            holder.icon = (ImageView) convertView.findViewById(R.id.icon);

            convertView.setTag(holder);
			/*} else {
				holder = (ViewHolder) convertView.getTag();
			}*/

            int level = mfilelist.get(position).getLevel();
            holder.icon.setPadding(45 * (level ), holder.icon
                    .getPaddingTop(), 0, holder.icon.getPaddingBottom());
            holder.text.setText(mfilelist.get(position).getName());
            if (mfilelist.get(position).isHaveChild()
                    && (mfilelist.get(position).isExpanded() == false)) {
                holder.icon.setImageBitmap(mIconCollapse);
            } else if (mfilelist.get(position).isHaveChild()
                    && (mfilelist.get(position).isExpanded() == true)) {
                holder.icon.setImageBitmap(mIconExpand);
            } else if (!mfilelist.get(position).isHaveChild()){
               holder.icon.setImageBitmap(mPerson);

            }
            return convertView;
        }

        class ViewHolder {
            TextView text;
            ImageView icon;
        }
    }
}
