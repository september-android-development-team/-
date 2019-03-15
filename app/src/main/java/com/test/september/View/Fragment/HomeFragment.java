package com.test.september.View.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.test.september.Adapter.HomeAdapter;
import com.test.september.R;
import com.test.september.View.MyView.ChooseView.GridViewAdapter;
import com.test.september.View.MyView.ChooseView.HeaderViewBean;
import com.test.september.View.MyView.ChooseView.MyViewPagerAdapter;
import com.test.september.View.MyView.PullRefreshLayout.PullRefreshLayout;
import com.test.september.View.SearchToolBar;
import com.test.september.util.DividerItemDecoration;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

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
    private int REQUEST_CODE_SCAN = 111;
    private ViewPager mViewPagerGrid;
    private List<View> mViewPagerGridList;
    private List<HeaderViewBean> mDatas;

    private final static String TAG = HomeFragment.class.getSimpleName();
    //    @BindView(R.id.recyclerview)
//    RecyclerView recyclerview;
//    //    @BindView(R.id.refresh_view)
//    MaterialRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

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
    private TextView tv_scan;
//    private SearchView searchView;

    @Override
    public View initView() {
        Log.e(TAG, "主页面的Fragment的UI被初始化了");
        View view = View.inflate(mContext, R.layout.fragment_home, null);
        mViewPagerGrid=view.findViewById(R.id.vp);
        tv_scan=view.findViewById(R.id.tv_scan);
        tv_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(mContext,
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    // 权限还没有授予，进行申请
                    ActivityCompat.requestPermissions((Activity) mContext,
                            new String[]{Manifest.permission.CAMERA}, 200); // 申请的 requestCode 为 200
                } else {
                    Intent intent = new Intent(mContext, CaptureActivity.class);
                    startActivityForResult(intent,REQUEST_CODE_SCAN);
                }
            }
        });
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


//        refreshLayout = view.findViewById(R.id.refresh_view);
//        recyclerview = view.findViewById(R.id.recyclerview);
        recyclerView=view.findViewById(R.id.recyclerView);


        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        String[] array = new String[20];
        for (int i = 0; i < array.length; i++) {
            array[i] = "string " + i;
        }
        recyclerView.setAdapter(new ArrayAdapter(mContext, array));

        final PullRefreshLayout layout = (PullRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                layout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        layout.setRefreshing(false);
                    }
                }, 1000);
            }
        });
        layout.setOnLoadListener(new PullRefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                layout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        layout.setLoading(false);
                    }
                }, 1000);
            }
        });


        //FEATURE_20160216_1:添加仿美团Headerview begin
        initDatas();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        //塞GridView至ViewPager中：每页的个数
        int pageSize = getResources().getInteger(R.integer.HomePageHeaderColumn) * 2;
        //一共的页数等于 总数/每页数量，并取整。
        int pageCount = (int) Math.ceil(mDatas.size() * 1.0 / pageSize);
        //ViewPager viewpager = new ViewPager(this);
        mViewPagerGridList = new ArrayList<View>();
        for (int index = 0; index < pageCount; index++) {
            //每个页面都是inflate出一个新实例
            GridView grid = (GridView) inflater.inflate(R.layout.item_viewpager, mViewPagerGrid, false);
            grid.setAdapter(new GridViewAdapter(mContext, mDatas, index));
            grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(mContext, "hello"+i+"d"+l, Toast.LENGTH_SHORT).show();
                }
            });
            mViewPagerGridList.add(grid);
        }
//给ViewPager设置Adapter
        //viewpager.setAdapter(new MyViewPagerAdapter(viewpagerList));
//将ViewPager作为HeaderView设置给ListView
/*        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics()));
        viewpager.setLayoutParams(params);
        addHeaderView(viewpager);*/
//FEATURE_20160216_1:添加仿美团Headerview end

        mViewPagerGrid.setAdapter(new MyViewPagerAdapter(mViewPagerGridList));



        tvSearchHome=view.findViewById(R.id.tv_search_home);
        tvMessageHome=view.findViewById(R.id.tv_message_home);
//        initDatas();
//        initRefresh();
        return view;
    }

    /**
     * 处理权限回调结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 200:
            {

            }
                break;
            case 300:
            {

            }
                break;
        }
    }


    static class ArrayAdapter extends RecyclerView.Adapter<ViewHolder>{

        private String[] mArray;

        public ArrayAdapter(Context context, String[] array) {
            mArray = array;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new ViewHolder(View.inflate(viewGroup.getContext(), android.R.layout.simple_list_item_1, null));
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            viewHolder.mTextView.setText(mArray[i]);
        }

        @Override
        public int getItemCount() {
            return mArray.length;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView;
        }
    }


//
//    /**
//     * 初始化加载
//     */
//    private void initRefresh() {
//        mAdapter = new HomeAdapter(datas);
//        recyclerview.setAdapter(mAdapter);
//        //下面可以自己设置默认动画
//        recyclerview.setItemAnimator(new DefaultItemAnimator());
//        //下面可以自己设置排版方式
//        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
//
//        /**
//         * 设置是否上拉加载更多，默认是false，要手动改为true，要不然不会出现上拉加载
//         */
//        refreshLayout.setLoadMore(isLoadMore);
//        //设置分割线
//        recyclerview.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
//        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
//            /**
//             * 刷新的方法，我这里演示的是下拉刷新，因为没有数据，我这里也就只是toast一下
//             * 如果想要实现你们自己要的结果，就会在定义一个方法，获取最新数据，或者是在次
//             * 在这里调用之前获取数据的方法，以达到刷新数据的功能
//             *
//             * @param materialRefreshLayout
//             */
//            @Override
//            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
//                //一般加载数据都是在子线程中，这里我用到了handler
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(mContext, "已经没有更多数据了", Toast.LENGTH_SHORT).show();
//                        /**
//                         * 刷新完成后调用此方法，要不然刷新效果不消失
//                         */
//                        refreshLayout.finishRefresh();
//                    }
//                }, 3000);
//            }
//
//            /**
//             * 上拉加载更多的方法，在这里我只是简单的模拟了加载四条数据
//             * 真正用的时候，就会去定义方法，获取数据，一般都是分页，在数据端获取的时候
//             * 把页数去增加一，然后在去服务端去获取数据
//             * @param materialRefreshLayout
//             */
//            @Override
//            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        isLoadMore = false;
//                        for (int i = 0; i <= 3; i++) {
//                            mList.add(i, "new City " + i);
//                        }
//                        //通知刷新
//                        mAdapter.addLists(mAdapter.getLists().size(), mList);
//                        //mRecyclerView.scrollToPosition(mAdapter.getLists().size());
//                        /**
//                         * 完成加载数据后，调用此方法，要不然刷新的效果不会消失
//                         */
//                        refreshLayout.finishRefreshLoadMore();
//                    }
//                }, 3000);
//            }
//        });
//    }
//
//    /**
//     * 初始化数据
//     */
//    private void initDatas() {
//
//        datas.add("New York");
//        datas.add("Bei Jing");
//        datas.add("Boston");
//        datas.add("London");
//        datas.add("San Francisco");
//        datas.add("Chicago");
//        datas.add("Shang Hai");
//        datas.add("Tian Jin");
//        datas.add("Zheng Zhou");
//        datas.add("Hang Zhou");
//        datas.add("Guang Zhou");
//        datas.add("Fu Gou");
//        datas.add("Zhou Kou");
//    }


    private void initDatas() {
        mDatas = new ArrayList<HeaderViewBean>();
        for (int i = 0; i < 3; i++) {
            mDatas.add(new HeaderViewBean("美食", R.drawable.ic_category_0));
            mDatas.add(new HeaderViewBean("电影", R.drawable.ic_category_1));
            mDatas.add(new HeaderViewBean("酒店", R.drawable.ic_category_2));
            mDatas.add(new HeaderViewBean("KTV", R.drawable.ic_category_3));
            mDatas.add(new HeaderViewBean("外卖", R.drawable.ic_category_4));
            mDatas.add(new HeaderViewBean("美女6", R.drawable.ic_category_5));
            mDatas.add(new HeaderViewBean("美女7", R.drawable.ic_category_6));
            mDatas.add(new HeaderViewBean("美女8", R.drawable.ic_category_7));
            mDatas.add(new HeaderViewBean("帅哥", R.drawable.ic_category_8));
            mDatas.add(new HeaderViewBean("帅哥2", R.drawable.ic_category_9));
            mDatas.add(new HeaderViewBean("帅哥3", R.drawable.ic_category_10));
            mDatas.add(new HeaderViewBean("帅哥4", R.drawable.ic_category_11));
            mDatas.add(new HeaderViewBean("帅哥5", R.drawable.ic_category_12));
            mDatas.add(new HeaderViewBean("帅哥6", R.drawable.ic_category_13));
            mDatas.add(new HeaderViewBean("帅哥7", R.drawable.ic_category_14));
            mDatas.add(new HeaderViewBean("帅哥8", R.drawable.ic_category_15));
            mDatas.add(new HeaderViewBean("帅哥9", R.drawable.ic_category_16));
        }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == getActivity().RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                Log.d("result",content);
            }
        }
    }
}