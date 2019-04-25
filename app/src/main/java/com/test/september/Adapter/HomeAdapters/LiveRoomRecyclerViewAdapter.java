package com.test.september.Adapter.HomeAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.test.september.R;
import com.test.september.widget.HorizontalViewHolder;

import java.util.List;

/**
 * Created by KUNLAN
 * on 2019-04-08
 */
//public abstract class HorizontalAdapter extends DelegateAdapter.Adapter<HorizontalViewHolder> {

//    private Context mContext;
//
//    private LayoutHelper mLayoutHelper;
//
//    public HorizontalAdapter(Context context, LayoutHelper layoutHelper) {
//        this.mContext = context;
//        this.mLayoutHelper = layoutHelper;
//    }
//
//    @Override
//    public LayoutHelper onCreateLayoutHelper() {
//        return mLayoutHelper;
//    }
//
//    @Override
//    public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (viewType == 4) {
//            return new HorizontalViewHolder(
////                    LayoutInflater.from(mContext).inflate(R.layout.layout_recyclerview, parent, false));
//        }
//        return null;
//    }
//
//    @Override
//    public void onBindViewHolder(NormalViewHolder holder, int position) {
//        if (holder.itemView instanceof RecyclerView) {
//            RecyclerView recyclerView = (RecyclerView) holder.itemView;
//            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
//            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//            recyclerView.setLayoutManager(linearLayoutManager);
//            recyclerView.setAdapter(new HotItemAdapter(mContext, list,listener));
//        }
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return 4;
//    }
//
//    @Override
//    public int getItemCount() {
//        return 1;
//    }
//}