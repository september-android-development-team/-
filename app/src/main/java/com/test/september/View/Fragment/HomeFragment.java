package com.test.september.View.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.alibaba.android.vlayout.layout.StickyLayoutHelper;
import com.test.september.Adapter.HomeAdapter;
import com.test.september.Adapter.HomeAdapters.LinearAdapter;
import com.test.september.Adapter.HomeAdapters.SingleLayoutAdapter;
import com.test.september.Adapter.HomeAdapters.StickyLayoutAdapter;
import com.test.september.R;
import com.test.september.View.MyView.ChooseView.HeaderViewBean;
import com.test.september.View.MyView.RecycleRefresh.FunGameRefreshView;
import com.test.september.View.SearchToolBar;
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
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    Unbinder unbinder;
    private int REQUEST_CODE_SCAN = 111;
    private ViewPager mViewPagerGrid;
    private List<View> mViewPagerGridList;
    private List<HeaderViewBean> mDatas;
    private FunGameRefreshView refreshView;
    private final static String TAG = HomeFragment.class.getSimpleName();

    private ArrayList<String> Linearlists = new ArrayList<>();
    //    @BindView(R.id.recyclerview)
//    RecyclerView recyclerview;
//    //    @BindView(R.id.refresh_view)
//    MaterialRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    TextView tvSearchHome;
//    TextView tvMessageHome;

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
    private DelegateAdapter adapters;
//    private LiveRoomRecyclerViewAdapter mliveRoomRecyclerViewAdapter;
    private RecyclerView mRecyclerView ;
    @Override
    public View initView() {
        Log.e(TAG, "主页面的Fragment的UI被初始化了");
        View view = View.inflate(mContext, R.layout.fragment_home, null);
        tvSearchHome = view.findViewById(R.id.tv_search_home);
        rvList=view.findViewById(R.id.rv_list);
        refreshView =view.findViewById(R.id.refresh_fun_game);
        refreshView.setGameOverText("游戏结束");
        refreshView.setLoadingFinishedText("刷新完成");
        refreshView.setLoadingText("正在刷新");
        refreshView.setTopMaskText("一个彩蛋");
        refreshView.setBottomMaskText("玩个游戏吧!");
        refreshView.setOnRefreshListener(new FunGameRefreshView.FunGameRefreshListener() {
            @Override
            public void onPullRefreshing() {
                SystemClock.sleep(2000);
            }

            @Override
            public void onRefreshComplete() {
//                刷新要做的事情
                Log.d("Refresh_finish","刷新完成");
            }
        });



        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        rvList.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(mContext);
        rvList.setLayoutManager(layoutManager);
        adapters = new DelegateAdapter(layoutManager, true);
        rvList.setAdapter(adapters);


//        mViewPagerGrid=view.findViewById(R.id.vp);
        tv_scan = view.findViewById(R.id.tv_scan);
        tv_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(mContext,
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    // 权限还没有授予，进行申请
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 200); // 申请的 requestCode 为 200
                } else {
                    Intent intent = new Intent(mContext, CaptureActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_SCAN);
                }
            }
        });
//



//        recyclerview = view.findViewById(R.id.recyclerview);
//        recyclerView=view.findViewById(R.id.recyclerView);
//
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        String[] array = new String[20];
//        for (int i = 0; i < array.length; i++) {
//            array[i] = "string " + i;
//        }
//        recyclerView.setAdapter(new ArrayAdapter(mContext, array));

//        final PullRefreshLayout layout = (PullRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
//        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                layout.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        layout.setRefreshing(false);
//                    }
//                }, 1000);
//            }
//        });
//        layout.setOnLoadListener(new PullRefreshLayout.OnLoadListener() {
//            @Override
//            public void onLoad() {
//                layout.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        layout.setLoading(false);
//                    }
//                }, 1000);
//            }
//        });


        //FEATURE_20160216_1:添加仿美团Headerview begin
//        initDatas();
//        LayoutInflater inflater = LayoutInflater.from(mContext);
//        //塞GridView至ViewPager中：每页的个数
//        int pageSize = getResources().getInteger(R.integer.HomePageHeaderColumn) * 2;
//        //一共的页数等于 总数/每页数量，并取整。
//        int pageCount = (int) Math.ceil(mDatas.size() * 1.0 / pageSize);
//        //ViewPager viewpager = new ViewPager(this);
//        mViewPagerGridList = new ArrayList<View>();
//        for (int index = 0; index < pageCount; index++) {
//            //每个页面都是inflate出一个新实例
//            GridView grid = (GridView) inflater.inflate(R.layout.item_viewpager, mViewPagerGrid, false);
//            grid.setAdapter(new GridViewAdapter(mContext, mDatas, index));
//            grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                    Toast.makeText(mContext, "hello"+i+"d"+l, Toast.LENGTH_SHORT).show();
//                }
//            });
//            mViewPagerGridList.add(grid);
//        }
//给ViewPager设置Adapter
        //viewpager.setAdapter(new MyViewPagerAdapter(viewpagerList));
//将ViewPager作为HeaderView设置给ListView
/*        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics()));
        viewpager.setLayoutParams(params);
        addHeaderView(viewpager);*/
//FEATURE_20160216_1:添加仿美团Headerview end

//        mViewPagerGrid.setAdapter(new MyViewPagerAdapter(mViewPagerGridList));



//        tvMessageHome=view.findViewById(R.id.tv_message_home);
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
            case 200: {
                Intent intent = new Intent(mContext, CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
            }
            break;
            case 300: {

            }
            break;
        }
    }

//
//    static class ArrayAdapter extends RecyclerView.Adapter<ViewHolder>{
//
//        private String[] mArray;
//
//        public ArrayAdapter(Context context, String[] array) {
//            mArray = array;
//        }
//
//        @Override
//        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//            return new ViewHolder(View.inflate(viewGroup.getContext(), android.R.layout.simple_list_item_1, null));
//        }
//
//        @Override
//        public void onBindViewHolder(ViewHolder viewHolder, int i) {
//            viewHolder.mTextView.setText(mArray[i]);
//        }
//
//        @Override
//        public int getItemCount() {
//            return mArray.length;
//        }
//    }
//
//    static class ViewHolder extends RecyclerView.ViewHolder{
//        public TextView mTextView;
//        public ViewHolder(View itemView) {
//            super(itemView);
//            mTextView = (TextView) itemView;
//        }
//    }


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


//
//    private void initDatas() {
//        mDatas = new ArrayList<HeaderViewBean>();
//        for (int i = 0; i < 3; i++) {
//            mDatas.add(new HeaderViewBean("美食", R.drawable.ic_category_0));
//            mDatas.add(new HeaderViewBean("电影", R.drawable.ic_category_1));
//            mDatas.add(new HeaderViewBean("酒店", R.drawable.ic_category_2));
//            mDatas.add(new HeaderViewBean("KTV", R.drawable.ic_category_3));
//            mDatas.add(new HeaderViewBean("外卖", R.drawable.ic_category_4));
//            mDatas.add(new HeaderViewBean("美女6", R.drawable.ic_category_5));
//            mDatas.add(new HeaderViewBean("美女7", R.drawable.ic_category_6));
//            mDatas.add(new HeaderViewBean("美女8", R.drawable.ic_category_7));
//            mDatas.add(new HeaderViewBean("帅哥", R.drawable.ic_category_8));
//            mDatas.add(new HeaderViewBean("帅哥2", R.drawable.ic_category_9));
//            mDatas.add(new HeaderViewBean("帅哥3", R.drawable.ic_category_10));
//            mDatas.add(new HeaderViewBean("帅哥4", R.drawable.ic_category_11));
//            mDatas.add(new HeaderViewBean("帅哥5", R.drawable.ic_category_12));
//            mDatas.add(new HeaderViewBean("帅哥6", R.drawable.ic_category_13));
//            mDatas.add(new HeaderViewBean("帅哥7", R.drawable.ic_category_14));
//            mDatas.add(new HeaderViewBean("帅哥8", R.drawable.ic_category_15));
//            mDatas.add(new HeaderViewBean("帅哥9", R.drawable.ic_category_16));
//        }
//    }

    @Override
    public void initData() {
        super.initData();

        initLinearData();

        //singleHelper布局
        SingleLayoutHelper singHelper=new SingleLayoutHelper();
        singHelper.setBgColor(Color.parseColor("#00B8D4"));
        singHelper.setMargin(5,0,5,5);
        adapters.addAdapter(new SingleLayoutAdapter(mContext,singHelper));


        //吸顶的Helper
        StickyLayoutHelper stickyHelper = new StickyLayoutHelper(true);
        adapters.addAdapter(new StickyLayoutAdapter(mContext,stickyHelper));


//        mliveRoomRecyclerViewAdapter = new LiveRoomRecyclerViewAdapter(mContext,mList,);
//                mRecyclerView = (RecyclerView)v.findViewById(R.id.rv_list);
//        LinearLayoutHelper linearHelper1 = new LinearLayoutHelper(5);
//        adapters.addAdapter(new HorizontalAdapter(mContext,mList, linearHelper1));

        //LinearHelper布局
        LinearLayoutHelper linearHelper = new LinearLayoutHelper(5);
        adapters.addAdapter(new LinearAdapter(mContext,Linearlists, linearHelper));


        tvSearchHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SearchToolBar.class);
                startActivity(intent);
            }
        });
        Log.e(TAG, "主页面的Fragment的数据被初始化了");
    }

    private void initLinearData() {
        for (int i = 0; i < 18; i++) {
            Linearlists.add(" 表单 :" + i);
        }
    }


    @Override
    public void setViewListener() {

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
                Log.d("result", content);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}