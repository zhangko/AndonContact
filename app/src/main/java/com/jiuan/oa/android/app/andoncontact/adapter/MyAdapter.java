package com.jiuan.oa.android.app.andoncontact.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jiuan.oa.android.app.andoncontact.R;
import com.jiuan.oa.android.app.andoncontact.im.ImMessage;

import java.util.List;

/**
 * Created by ZhangKong on 2015/7/24.
 */
public class MyAdapter extends ArrayAdapter {

    private List<ImMessage> mList;

    private LayoutInflater mInflater;

    public MyAdapter(Context context, int textViewResourceId,
                     List objects){
        super(context,textViewResourceId,objects);
        mInflater = LayoutInflater.from(context);
        mList = objects;

    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.adapter_layout,null);
        ViewHolder  holder = new ViewHolder();
       holder.mText = (TextView)convertView.findViewById(R.id.chat_content_send);
        holder.yText = (TextView)convertView.findViewById(R.id.chat_content_receive);

        convertView.setTag(holder);

        switch (mList.get(position).getType()){
            case 1:
                holder.mText.setText(mList.get(position).getMessage());
                holder.yText.setBackgroundResource(R.color.chat_background);
                break;
            case 2:
                holder.mText.setBackgroundResource(R.color.chat_background);
                holder.yText.setText(mList.get(position).getMessage());
             //   holder.yText.setBackgroundResource(android.R.color.holo_green_light);
                break;
        }

        return  convertView;
    }

    public synchronized void addItem(ImMessage item) {
        // checkListNull();
        mList.add(item);
        Log.d("TAG","添加了新的项目");
        notifyDataSetChanged();
    }

    class ViewHolder{
        TextView mText;
        TextView yText;
    }
}
