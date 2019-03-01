package com.test.september.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.september.R;

import java.util.List;

/**
 * Created by 若兰 on 2016/1/26.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:1069584784
 * csdn:http://blog.csdn.net/wuyinlei
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {


    /**
     * 加载布局的
     */
    private LayoutInflater mInflater;

    /**
     * 用来存放数据的集合
     */
    private List<String> mLists;

    /**
     * 自定义的item点击（在这里没有用到）
     */
    private OnItemClickListener mListener;


    /**
     * mListener get方法
     * @return
     */
    public OnItemClickListener getListener() {
        return mListener;
    }

    /**
     * mListener set方法
     * @param listener
     */
    public void setListener(OnItemClickListener listener) {
        mListener = listener;
    }

    /**
     * mLists集合的get方法  通过他可以取得数据的size();
     * @return
     */
    public List<String> getLists() {
        return mLists;
    }

    /**
     * 对以下方法的复用
     * @param lists
     */
    public void addLists(List<String> lists) {
        addLists(0, lists);
    }


    /**
     * 添加数据
     * @param position  添加的位置
     * @param lists   添加的数据
     */
    public void addLists(int position, List<String> lists) {
        //mLists = lists;
        if (lists != null && lists.size() > 0) {
            mLists.addAll(lists);
            /**
             * Notify any registered observers that the <code>itemCount</code> items starting at
             * position <code>positionStart</code> have changed.
             *
             * 通知item是从哪个地方到哪个地方已经改变了
             */
            notifyItemRangeChanged(position, mLists.size());
        }
    }


    /**
     * 构造方法，通过new对象的时候传入数据
     * @param items
     */
    public HomeAdapter(List<String> items) {
        mLists = items;
    }

    // 创建ViewHolder ,相当于ListVie Adapter 的getView 方法
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.list_item, null);
        return new MyViewHolder(view);
    }

    /**
     * 绑定数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_text.setText(mLists.get(position));
    }

    @Override
    public int getItemCount() {
        return mLists.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_text;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_text = (TextView) itemView.findViewById(R.id.tv_item);

            /**
             * 如果我们想要实现item的点击事件，就要在这处理，因为recycleview
             * 并没有给我们提供直接的点击触摸事件，这个也是他的好处（也可以理解为不好的地方）
             */
            tv_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onClick(v, getLayoutPosition(), mLists.get(getLayoutPosition()));
                    }
                }
            });

        }
    }

    //RecycleView的事件监听，这个要自己去写，里面的参数也是根据自己的需要去定义
    //不像listview中，已经有了item的点击监听事件
    interface OnItemClickListener {
        //自己定义
        void onClick(View v, int position, String item);
    }
}

