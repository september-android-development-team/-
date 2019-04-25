package com.test.september.Adapter.HomeAdapters;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.test.september.R;
import com.test.september.View.Login;
import com.test.september.widget.ChangeViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Linsa on 2018/1/2:11:21.
 * des: 创建相关LayoutHelper的使用
 */

public class StickyLayoutAdapter extends DelegateAdapter.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private LayoutHelper mHelper;

    private ChangeViewPager changeViewPager;


    private ArrayList<String> mItemList = new ArrayList<>();
//    @Override
//    public void onClick(View v) {
//
//        switch (v.getId()){
//            case 0:{
////                Toast.makeText(mContext,"呵呵",Toast.LENGTH_SHORT).show();
//                Log.d("wqwqwqwq","呵呵");
//            }break;
//        }
//    }
    public StickyLayoutAdapter(Context context, LayoutHelper helper) {
        this.mContext=context;
        this.mHelper=helper;
    }



    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sticky_layout, parent, false);


        return new RecyclerViewItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecyclerViewItemHolder recyclerViewHolder = (RecyclerViewItemHolder) holder;


        recyclerViewHolder.stick_view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"吸顶1",Toast.LENGTH_SHORT).show();

//                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
//                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);//横向滚动的RecycleView
//                mRecyclerView.setLayoutManager(linearLayoutManager);
//                mliveRoomRecyclerViewAdapter.initData(mItemList);
//                mRecyclerView.setAdapter(mliveRoomRecyclerViewAdapter)
            }
        });

        recyclerViewHolder.stick_view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"吸顶2",Toast.LENGTH_SHORT).show();
            }
        });

        recyclerViewHolder.stick_view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"吸顶3",Toast.LENGTH_SHORT).show();
            }
        });

        recyclerViewHolder.stick_view4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"吸顶4",Toast.LENGTH_SHORT).show();
            }
        });

//        recyclerViewHolder.stick_view1.setOnClickListener(this);
//        recyclerViewHolder.stick_view2.setOnClickListener(this);
//        recyclerViewHolder.stick_view3.setOnClickListener(this);
//        recyclerViewHolder.stick_view4.setOnClickListener(this);




//        ViewGroup.LayoutParams layoutParams =recyclerViewHolder.tv_name.getLayoutParams();
//        layoutParams.height = 260 + position % 7 * 20;
//        recyclerViewHolder.tv_name.setLayoutParams(layoutParams);
//        recyclerViewHolder.iv_icon.setBackgroundResource();

    }

    @Override
    public int getItemCount() {
        return 1;
    }



    /**
     * 正常条目的item的ViewHolder
     */
    private class RecyclerViewItemHolder extends RecyclerView.ViewHolder{

        public TextView stick_view1,stick_view2,stick_view3,stick_view4;


        public RecyclerViewItemHolder(View itemView) {
            super(itemView);
            stick_view1 = itemView.findViewById(R.id.stick_view1);
            stick_view2 = itemView.findViewById(R.id.stick_view2);
            stick_view3 = itemView.findViewById(R.id.stick_view3);
            stick_view4 = itemView.findViewById(R.id.stick_view4);
//            stick_view1.setOnClickListener((View.OnClickListener) mContext);




        }


    }
}
