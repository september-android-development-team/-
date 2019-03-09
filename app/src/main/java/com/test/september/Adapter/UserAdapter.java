package com.test.september.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.test.september.R;

import java.util.ArrayList;

public class UserAdapter extends BaseAdapter {
    private Context context;
    private ArrayList list;
    public UserAdapter(Context context,ArrayList list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return 20;
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
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_user_list,null);
        }
        TextView tv1 = (TextView)convertView.findViewById(R.id.tv1);
        tv1.setText(list.get(position).toString());
//        TextView tv2 = (TextView)convertView.findViewById(R.id.tv2);
//        tv2.setText(list.get(position).toString());
        return convertView;
    }
}
