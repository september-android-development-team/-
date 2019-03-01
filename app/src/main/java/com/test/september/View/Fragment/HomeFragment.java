package com.test.september.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.test.september.Adapter.HomeAdapter;
import com.test.september.R;
import com.test.september.View.SearchToolBar;
import com.test.september.util.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

//import android.support.v7.widget.DividerItemDecoration;


/**
 * 首页Fragment
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {


    private final static String TAG = HomeFragment.class.getSimpleName();
    //    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    //    @BindView(R.id.refresh_view)
    MaterialRefreshLayout refreshLayout;
    TextView tvSearchHome;
    TextView tvMessageHome;


    /**
     * 刚初始化的数据
     */
    private List<String> datas = new ArrayList<>();

    /**
     * 在上拉刷新的时候，判断，是否处于上拉刷新，如果是的话，就禁止在一次刷新，保障在数据加载完成之前
     * 避免重复和多次加载
     */
    private boolean isLoadMore = true;
    /**
     * 一个承接数据的数组
     */
    private List<String> mList = new ArrayList<>();
    private HomeAdapter mAdapter;


//    private SearchView searchView;

    @Override
    public View initView() {
        Log.e(TAG, "主页面的Fragment的UI被初始化了");
        View view = View.inflate(mContext, R.layout.fragment_home, null);

//        searchView = (SearchView) view.findViewById(R.id.search_view);
//        searchView.setOnClickSearch(new ICallBack() {
//            @Override
//            public void SearchAciton(String string) {
//                System.out.println("我收到了" + string);
//            }
//        });
//
//        // 5. 设置点击返回按键后的操作（通过回调接口）
//        searchView.setOnClickBack(new bCallBack() {
//            @Override
//            public void BackAciton() {
//
//            }
//
//        });


//        input = (EditText) view.findViewById(R.id.et_input);
//        btn_search = (Button) view.findViewById(R.id.btn_search);
        refreshLayout = view.findViewById(R.id.refresh_view);
        recyclerview = view.findViewById(R.id.recyclerview);
        tvSearchHome=view.findViewById(R.id.tv_search_home);
        tvMessageHome=view.findViewById(R.id.tv_message_home);
        initRefresh();
        return view;
    }


    /**
     * 初始化加载
     */
    private void initRefresh() {
        mAdapter = new HomeAdapter(datas);
        recyclerview.setAdapter(mAdapter);
        //下面可以自己设置默认动画
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        //下面可以自己设置排版方式
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));

        /**
         * 设置是否上拉加载更多，默认是false，要手动改为true，要不然不会出现上拉加载
         */
        refreshLayout.setLoadMore(isLoadMore);
        //设置分割线
        recyclerview.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            /**
             * 刷新的方法，我这里演示的是下拉刷新，因为没有数据，我这里也就只是toast一下
             * 如果想要实现你们自己要的结果，就会在定义一个方法，获取最新数据，或者是在次
             * 在这里调用之前获取数据的方法，以达到刷新数据的功能
             *
             * @param materialRefreshLayout
             */
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                //一般加载数据都是在子线程中，这里我用到了handler
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mContext, "已经没有更多数据了", Toast.LENGTH_SHORT).show();
                        /**
                         * 刷新完成后调用此方法，要不然刷新效果不消失
                         */
                        refreshLayout.finishRefresh();
                    }
                }, 3000);
            }

            /**
             * 上拉加载更多的方法，在这里我只是简单的模拟了加载四条数据
             * 真正用的时候，就会去定义方法，获取数据，一般都是分页，在数据端获取的时候
             * 把页数去增加一，然后在去服务端去获取数据
             * @param materialRefreshLayout
             */
            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isLoadMore = false;
                        for (int i = 0; i <= 3; i++) {
                            mList.add(i, "new City " + i);
                        }
                        //通知刷新
                        mAdapter.addLists(mAdapter.getLists().size(), mList);
                        //mRecyclerView.scrollToPosition(mAdapter.getLists().size());
                        /**
                         * 完成加载数据后，调用此方法，要不然刷新的效果不会消失
                         */
                        refreshLayout.finishRefreshLoadMore();
                    }
                }, 3000);
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initDatas() {

        datas.add("New York");
        datas.add("Bei Jing");
        datas.add("Boston");
        datas.add("London");
        datas.add("San Francisco");
        datas.add("Chicago");
        datas.add("Shang Hai");
        datas.add("Tian Jin");
        datas.add("Zheng Zhou");
        datas.add("Hang Zhou");
        datas.add("Guang Zhou");
        datas.add("Fu Gou");
        datas.add("Zhou Kou");
    }

    @Override
    public void initData() {
        super.initData();
        tvSearchHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, SearchToolBar.class);
                startActivity(intent);
            }
        });
        tvMessageHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, CommunityFragment.class);
                startActivity(intent);
            }
        });
//        for (int i = 0; i < mVals.length; i++) {
//            TextView tv = (TextView) mInflater.inflate(
//                    R.layout.search_label_tv, mFlowLayout, false);
//            tv.setText(mVals[i]);
//            final String str = tv.getText().toString();
//            //点击事件
//            tv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //加入搜索历史纪录记录
//                    Toast.makeText(mContext, str, Toast.LENGTH_LONG).show();
//                }
//            });
//            mFlowLayout.addView(tv);
//        }
        Log.e(TAG, "主页面的Fragment的数据被初始化了");
    }

    @Override
    public void onClick(View v) {

    }


}