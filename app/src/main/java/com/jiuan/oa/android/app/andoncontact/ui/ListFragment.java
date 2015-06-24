package com.jiuan.oa.android.app.andoncontact.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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

/**
 * Created by ZhangKong on 2015/6/18.
 */
public class ListFragment extends Fragment {





    private static final String ARG_SECTION_NUMBER = "section_number";



    private ArrayList<TreeNode> nodelist = new ArrayList<TreeNode>();


    private TreeViewAdapter adapter = null;
    private MyDBHelper myhelper;

    private List<String> companyID;

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
        companyID = new ArrayList<String>();
        Cursor companycursor = myhelper.companyquery("companytable");
        if(companycursor.moveToFirst()){
            boolean firstflag = true;
            while (firstflag){
                Log.v("MSG", companycursor.getString(1));
                companyID.add(companycursor.getString(2));
                Log.v("MSG",companycursor.getString(1) + "    " +  companycursor.getString(2));
                firstflag = companycursor.moveToNext();
            }
        }

        if(companycursor != null){
            companycursor.close();
        }
      String departmentid = companyID.get(getArguments().getInt(ARG_SECTION_NUMBER));

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        // 一级部门的游标 firstcursor
        Cursor firstcursor = myhelper.query("companytable",departmentid);
        if(firstcursor.moveToFirst()){
            boolean firstflag = true;
            while(firstflag){
                TreeNode tempnode = new TreeNode();
                tempnode.setName(firstcursor.getString(1));
                tempnode.setHaveChild(true);
                tempnode.setLevel(1);
                tempnode.setExpanded(false);
                tempnode.setDepartmenID(firstcursor.getString(2));
                tempnode.setHaveParent(false);
                tempnode.setID(firstcursor.getString(2));
                tempnode.setParent("");
                nodelist.add(tempnode);
                firstflag = firstcursor.moveToNext();
            }
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
                    else{
                        nodelist.get(position).setExpanded(true);
                        int level = nodelist.get(position).getLevel();
                        int nextlevel = level + 1;
                        Cursor cursor = myhelper.query("companytable",nodelist.get(position).getDepartmenID());
                        if(cursor.getCount() != 0){
                            if(cursor.moveToFirst()){
                                boolean secondflag = true;
                                int i = 1;
                                while (secondflag){

                                    TreeNode templistnode = new TreeNode();
                                    templistnode.setName(cursor.getString(1));
                                    templistnode.setHaveChild(true);
                                    templistnode.setLevel(nextlevel);
                                    templistnode.setExpanded(false);
                                    templistnode.setDepartmenID(cursor.getString(2));
                                    templistnode.setHaveParent(true);
                                    templistnode.setID(cursor.getString(2));
                                    templistnode.setParent(cursor.getString(2));
                                    nodelist.add(position + 1 ,templistnode);
                                    ++i;
                                    secondflag = cursor.moveToNext();
                                }
                            }
                            Cursor contactcursor = myhelper.contactquery("contacttable",nodelist.get(position).getDepartmenID());
                            if(contactcursor.moveToFirst()){
                                boolean contactflag = true;
                                int i = 1;
                                while(contactflag){
                                    TreeNode templistnode = new TreeNode();
                                    templistnode.setName(contactcursor.getString(1));
                                    templistnode.setHaveChild(false);
                                    templistnode.setLevel(nextlevel);
                                    templistnode.setExpanded(false);
                                    templistnode.setDepartmenID(contactcursor.getString(4));
                                    templistnode.setHaveParent(true);
                                    templistnode.setID(contactcursor.getString(2));
                                    templistnode.setParent(contactcursor.getString(2));
                                    templistnode.setCode(contactcursor.getString(2));
                                    nodelist.add(position + 1 ,templistnode);
                                    ++i;
                                    contactflag = contactcursor.moveToNext();

                                }
                            }

                        }
                        else{
                            Cursor peoplecursor = myhelper.contactquery("contacttable",nodelist.get(position).getDepartmenID());
                            if(peoplecursor.moveToFirst()){
                                boolean peopleflag = true;
                                int i = 1;
                                while(peopleflag){

                                    TreeNode templistnode = new TreeNode();
                                    templistnode.setName(peoplecursor.getString(1));
                                    templistnode.setHaveChild(false);
                                    templistnode.setLevel(nextlevel);
                                    templistnode.setExpanded(false);
                                    templistnode.setDepartmenID(peoplecursor.getString(4));
                                    templistnode.setHaveParent(true);
                                    templistnode.setID(peoplecursor.getString(2));
                                    templistnode.setParent(peoplecursor.getString(2));
                                    templistnode.setCode(peoplecursor.getString(2));
                                    nodelist.add(position + 1 ,templistnode);
                                    ++i;
                                    peopleflag = peoplecursor.moveToNext();

                                }
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
