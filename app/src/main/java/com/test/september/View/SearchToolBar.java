package com.test.september.View;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.test.september.Adapter.ContentAdapter2;
import com.test.september.R;
import com.test.september.View.Fragment.BaseFragment;
import com.test.september.View.Fragment.SearchFragment.SearchHistoryFragment;
import com.test.september.View.Fragment.SearchFragment.SearchResultFragment;
import com.test.september.View.MyView.FlowLayout;
import com.test.september.listener.OnItemClickListener;
import com.test.september.listener.OnItemDeleteListener;
import com.test.september.listener.search_contact;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SearchToolBar extends AppCompatActivity implements search_contact {
    //    private RecyclerView recyclerViewRecommend1;
//    //    private RecyclerView recyclerViewRecommend2;
//    private RecyclerView recyclerViewHistory;
//    private ImageView ivHistoryArrow;
//    private TextView tvHistory;
    private TextView search;
    private EditText input;

    //    private ImageView ivHistoryDelete;
//    private TextView tvHistoryDeleteFinish;
//
//    private LinearLayout llShowRecommendBar;
//    private LinearLayout llRecommend1Bar;
//    private LinearLayout llRecommend2Bar;
//
//    //    private List<String> recommend1ContentList = new ArrayList<>();
////    private List<String> recommend2ContentList = new ArrayList<>();
//    private List<String> historyContentList = new ArrayList<>();
//
//    //    private ContentAdapter1 recommend1Adapter;
////    private ContentAdapter2 recommend2Adapter;
//    private ContentAdapter2 historyAdapter;
//    private SharedPreferences mPref;//使用SharedPreferences记录搜索历史
//    private boolean isShowRecommend = true; //是否显示推荐词栏
//    private boolean isHidePartialHistory = true; //是否隐藏部分历史,默认为true
//    private boolean isInHistoryDelete = false; //是否处于删除历史模式
//    private List<String> mHistoryKeywords;//记录文本

    //装fragment的实例集合
    private ArrayList<BaseFragment> fragments=  new ArrayList<BaseFragment>();
    //缓存Fragment或上次显示的Fragment
    private Fragment tempFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tool_bar);
        init();
        initFragment();
//        initData();
        initview();
    }


    /**
     * 添加的时候按照顺序
     */
    private void initFragment() {
        fragments.add(new SearchHistoryFragment());
        fragments.add(new SearchResultFragment());

        BaseFragment baseFragment = getFragment(0);
        switchFragment(tempFragment, baseFragment);
    }

    private void initview(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
    @SuppressLint("ClickableViewAccessibility")
    private void init(){

        input=findViewById(R.id.et_input);
        search=findViewById(R.id.tv_cancel);
        input.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // getCompoundDrawables获取是一个数组，数组0,1,2,3,对应着左，上，右，下 这4个位置的图片，如果没有就为null
                Drawable drawable = input.getCompoundDrawables()[2];
                //如果右边没有图片，不再处理
                if (drawable == null){
                    return false;
                }
                //如果不是按下事件，不再处理
                if (event.getAction() != MotionEvent.ACTION_DOWN) {
                    return false;
                }
                if (event.getX() > input.getWidth()
                        - input.getPaddingRight()
                        - drawable.getIntrinsicWidth()){
                    //具体操作
                    input.setText("");
                }
                return false;
            }
        });


        input.addTextChangedListener(new TextWatcher() { // 对文本内容改变进行监听
            @Override
            public void afterTextChanged(Editable paramEditable) {

            }
            @Override
            public void beforeTextChanged(CharSequence paramCharSequence,int paramInt1, int paramInt2, int paramInt3) {

            }
            @Override
            public void onTextChanged(CharSequence paramCharSequence,int paramInt1, int paramInt2, int paramInt3) {
                String keywords=input.getText().toString();
                if (TextUtils.isEmpty(keywords)) {
                    Drawable leftDrawable = getResources().getDrawable(R.drawable.ic_search);
                    leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());
                    input.setCompoundDrawables(leftDrawable,null,null,null);
                    BaseFragment baseFragment = getFragment(0);
                    switchFragment(tempFragment, baseFragment);
                }else {
                    Drawable leftDrawable = getResources().getDrawable(R.drawable.ic_search);
                    Drawable rightDrawable = getResources().getDrawable(R.drawable.ic_remove);
                    leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());
                    rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
                    input.setCompoundDrawables(leftDrawable, null, rightDrawable, null);
                }
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keywords = input.getText().toString();
                if (!TextUtils.isEmpty(keywords)) {
                    //根据位置得到相应的Fragment
//                    save();
                } else {
//                    Drawable leftDrawable = getResources().getDrawable(R.drawable.ic_search);
//                    input.setCompoundDrawables(leftDrawable,null,null,null);
//                    Toast.makeText(SearchToolBar.this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    /**
     * 根据位置得到对应的 Fragment
     *
     * @param position
     * @return
     */
    private BaseFragment getFragment(int position) {
        if (fragments != null && fragments.size() > 0) {
            BaseFragment baseFragment = fragments.get(position);
            return baseFragment;
        }
        return null;
    }
    /**
     * 切换Fragment
     *
     * @param fragment
     * @param nextFragment
     */
    private void switchFragment(Fragment fragment, BaseFragment nextFragment) {

        if (tempFragment != nextFragment) {
            tempFragment = nextFragment;
            if (nextFragment != null) {

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                //判断nextFragment是否添加成功
                if (!nextFragment.isAdded()) {
                    //隐藏当前的Fragment
                    if (fragment != null) {
                        transaction.hide(fragment);
                    }
                    //添加Fragment
                    transaction.add(R.id.frameLayout, nextFragment).commit();
                } else {

                    //隐藏当前Fragment
                    if (fragment != null) {
//                        if(!isLogin){
//                            transaction.replace(R.id.frameLayout,fragment);
//                            transaction.hide(nextFragment);
//                        }else {
                        transaction.hide(fragment);
                    }
//                    }
                    transaction.show(nextFragment).commit();
                }
            }
        }
    }

    @Override
    public void sendMessage() {
        BaseFragment baseFragment = getFragment(1);
        switchFragment(tempFragment, baseFragment);
    }

    @Override
    public void sendSearchText() {

//        input.getText();
    }

}
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//    }
//
//    @SuppressLint("ClickableViewAccessibility")
//    private void initViews(){
//        //沉浸式处理
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
//
////        recyclerViewRecommend1 = findViewById(R.id.rv_recommend_bar_1);
////        recyclerViewRecommend2 = findViewById(R.id.rv_recommend_bar_2);
//        recyclerViewHistory = findViewById(R.id.rv_recommend_bar_history);
//        tvHistory = findViewById(R.id.tv_history);
//        ivHistoryArrow = findViewById(R.id.iv_arrow);
//        ivHistoryDelete = findViewById(R.id.iv_delete);
//        tvHistoryDeleteFinish = findViewById(R.id.tv_finish);
//        llShowRecommendBar = findViewById(R.id.linear_show_recommend);
////        llRecommend1Bar = findViewById(R.id.linear_recommendbar1);
////        llRecommend2Bar = findViewById(R.id.linear_recommendbar2);
//        mPref = getSharedPreferences("input", Activity.MODE_PRIVATE);
//        input = (EditText) findViewById(R.id.et_input);
//        search=findViewById(R.id.tv_cancel);
//        mHistoryKeywords = new ArrayList<String>();
//
//        input.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                // getCompoundDrawables获取是一个数组，数组0,1,2,3,对应着左，上，右，下 这4个位置的图片，如果没有就为null
//                Drawable drawable = input.getCompoundDrawables()[2];
//                //如果右边没有图片，不再处理
//                if (drawable == null){
//                    return false;
//                }
//                //如果不是按下事件，不再处理
//                if (event.getAction() != MotionEvent.ACTION_DOWN) {
//                    return false;
//                }
//                if (event.getX() > input.getWidth()
//                        - input.getPaddingRight()
//                        - drawable.getIntrinsicWidth()){
//                    //具体操作
//                    input.setText("");
//                }
//                return false;
//            }
//        });
//
//        input.addTextChangedListener(new TextWatcher() { // 对文本内容改变进行监听
//            @Override
//            public void afterTextChanged(Editable paramEditable) {
//
//            }
//            @Override
//            public void beforeTextChanged(CharSequence paramCharSequence,int paramInt1, int paramInt2, int paramInt3) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence paramCharSequence,int paramInt1, int paramInt2, int paramInt3) {
//                String keywords = input.getText().toString();
//                if (TextUtils.isEmpty(keywords)) {
//                    Drawable leftDrawable = getResources().getDrawable(R.drawable.ic_search);
//                    leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());
//                    input.setCompoundDrawables(leftDrawable,null,null,null);
//                }else {
//                    Drawable leftDrawable = getResources().getDrawable(R.drawable.ic_search);
//                    Drawable rightDrawable = getResources().getDrawable(R.drawable.ic_remove);
//                    leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());
//                    rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
//                    input.setCompoundDrawables(leftDrawable, null, rightDrawable, null);
//                }
//            }
//        });
//
////        initEditText();
//        search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String keywords = input.getText().toString();
//                if (!TextUtils.isEmpty(keywords)) {
//                    save();
//            } else {
////                    Drawable leftDrawable = getResources().getDrawable(R.drawable.ic_search);
////                    input.setCompoundDrawables(leftDrawable,null,null,null);
//                    Toast.makeText(SearchToolBar.this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//
//
//
//        DividerItemDecoration itemDecorationHor = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.HORIZONTAL);
//        itemDecorationHor.setDrawable(new ColorDrawable(Color.parseColor("#e0e0e0")));
//
//        DividerItemDecoration itemDecorationVer = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
//        itemDecorationVer.setDrawable(new ColorDrawable(Color.parseColor("#e0e0e0")));
//
////        recommend1Adapter = new ContentAdapter1(getApplicationContext(), recommend1ContentList);
////        recommend1Adapter.setItemClickListener(recommend1BarItemClickListener);
//
////        GridLayoutManager gmRecommend1 = new GridLayoutManager(getApplicationContext(), 3);
////        recyclerViewRecommend1.setLayoutManager(gmRecommend1);
////        recyclerViewRecommend1.addItemDecoration(itemDecorationHor);
////        recyclerViewRecommend1.addItemDecoration(itemDecorationVer);
////        recyclerViewRecommend1.setAdapter(recommend1Adapter);
//
////        recommend2Adapter = new ContentAdapter2(getApplicationContext(), recommend2ContentList);
////        recommend2Adapter.setItemClickListener(recommend2BarItemClickListener);
//
////        GridLayoutManager gmRecommend2 = new GridLayoutManager(getApplicationContext(), 2);
////        recyclerViewRecommend2.setLayoutManager(gmRecommend2);
////        recyclerViewRecommend2.addItemDecoration(itemDecorationHor);
////        recyclerViewRecommend2.addItemDecoration(itemDecorationVer);
////        recyclerViewRecommend2.setAdapter(recommend2Adapter);
//        initsearchhistory();
//        historyAdapter = new ContentAdapter2(getApplicationContext(), historyContentList);
//        //历史记录先不显示全部
//        int displayCount = historyContentList.size() > 4 ? 4 : historyContentList.size();
//        historyAdapter.setDisplayCount(displayCount);
//        historyAdapter.setItemClickListener(historyBarItemClickListener);
//        historyAdapter.setItemDeleteListener(historyBarItemDeleteListener);
//
//        GridLayoutManager gmHistory = new GridLayoutManager(getApplicationContext(), 2);
//        recyclerViewHistory.setLayoutManager(gmHistory);
//        recyclerViewHistory.addItemDecoration(itemDecorationHor);
//        recyclerViewHistory.addItemDecoration(itemDecorationVer);
//        recyclerViewHistory.setAdapter(historyAdapter);
//    }
//
//    //初始化edittext 控件
//    private void initfragent() {
//
//    }
//
//
////    // 控制图片的显示
////    public void setEditTextDrawable() {
////        if (getText().toString().length() == 0) {
////            setCompoundDrawables(null, null, null, null);
////        } else {
////            setCompoundDrawables(null, null, this.dRight, null);
////        }
////    }
//
//
//
//
//    /**
//     * 储存搜索历史
//     */
//    public void save() {
//        String text = input.getText().toString();
//        String oldText = mPref.getString("111", "");
//        if (!TextUtils.isEmpty(text) && !(oldText.contains(text))) {
//            if (historyContentList.size() > 15) {
//                //最多保存条数
//                return;
//            }
//            SharedPreferences.Editor editor = mPref.edit();
//            editor.putString("111", text + "," + oldText);
//            editor.commit();
//            historyContentList.add(0, text);
//        }
////        initsearchhistory();
//        historyAdapter.notifyDataSetChanged();
//    }
//
//    public void initsearchhistory(){
//        String history = mPref.getString("111", "");
//        if (!TextUtils.isEmpty(history)) {
//            List<String> list = new ArrayList<String>();
//            for (Object o : history.split(",")) {
//                list.add((String) o);
//            }
//            historyContentList = list;
//        }
////        if (mHistoryKeywords.size() > 0) {
////            mSearchHistoryLl.setVisibility(View.VISIBLE);
////        } else {
////            mSearchHistoryLl.setVisibility(View.GONE);
////        }
//    }
//
//
//    /**
//     * 初始化数据，到时可自定义绑定
//     * */
//    private void initData(){
////        recommend1ContentList.add("陈翔六点半");
////        recommend1ContentList.add("毕加索画");
////        recommend1ContentList.add("姐弟恋结婚");
////
////        recommend2ContentList.add("柯基摔倒");
////        recommend2ContentList.add("银行行长");
////        recommend2ContentList.add("退休生活");
////        recommend2ContentList.add("中国核武");
////        recommend2ContentList.add("坐88路车回家");
////        recommend2ContentList.add("解放军军演");
////        recommend2ContentList.add("银行放贷");
////        recommend2ContentList.add("阿拉斯加训练");
////        recommend2ContentList.add("特朗普推特");
////        recommend2ContentList.add("王者荣耀老夫子");
////        recommend2ContentList.add("吴卓羲督察");
////        recommend2ContentList.add("泰国榴莲");
////        recommend2ContentList.add("王者荣耀");
//
////        historyContentList.add("零食");
////        historyContentList.add("美团外卖");
////        historyContentList.add("极限挑战");
////        historyContentList.add("安卓");
////        historyContentList.add("php开发");
////        historyContentList.add("鹿晗");
////        historyContentList.add("奔跑吧兄弟");
////        historyContentList.add("深圳房价");
////        historyContentList.add("深圳大学");
////        historyContentList.add("科技园");
////        historyContentList.add("老梁");
////        historyContentList.add("五十一度");
////        historyContentList.add("回来吧");
////        historyContentList.add("黄渤");
////        historyContentList.add("最近上映电影");
////        historyContentList.add("小说");
////        historyContentList.add("酷狗");
//    }
//
////    private OnItemClickListener recommend1BarItemClickListener = new OnItemClickListener() {
////        @Override
////        public void onItemClick(View view, int position) {
////            Toast.makeText(getApplicationContext(), String.format("你点击了-%s", recommend1ContentList.get(position)), Toast.LENGTH_SHORT).show();
////        }
////    };
////
////    private OnItemClickListener recommend2BarItemClickListener = new OnItemClickListener() {
////        @Override
////        public void onItemClick(View view, int position) {
////            Toast.makeText(getApplicationContext(), String.format("你点击了-%s", recommend2ContentList.get(position)), Toast.LENGTH_SHORT).show();
////        }
////    };
//
//    private OnItemClickListener historyBarItemClickListener = new OnItemClickListener() {
//        @Override
//        public void onItemClick(View view, int position) {
//            Toast.makeText(getApplicationContext(), String.format("你点击了-%s", historyContentList.get(position)), Toast.LENGTH_SHORT).show();
//        }
//    };
//
//    private OnItemDeleteListener historyBarItemDeleteListener = new OnItemDeleteListener() {
//        @Override
//        public void onItemDelete(View view, int position) {
//            Toast.makeText(getApplicationContext(), String.format("你删除了-%s", historyContentList.get(position)), Toast.LENGTH_SHORT).show();
//            historyContentList.remove(position);
//
//
//            String history = mPref.getString("111", "");
//            if (!TextUtils.isEmpty(history)) {
//                List<String> list = new ArrayList<String>();
//                for (Object o : history.split(",")) {
//                    list.add((String) o);
//                }
//                list.remove(list.get(position));
//                mPref = getSharedPreferences("input", MODE_PRIVATE);
//                SharedPreferences.Editor editor = mPref.edit();
//                editor.remove("111").commit();
//
////                SharedPreferences.Editor editor = mPref.edit();
//                for(int i=0;i<=list.size()-1;i++) {
//                    String text = list.get(i);
//                    String oldText = mPref.getString("111", "");
//                    editor.putString("111", text + "," + oldText);
//                    editor.commit();
//                }
//
//
////            mPref = getSharedPreferences("input", MODE_PRIVATE);
////            SharedPreferences.Editor editor = mPref.edit();
////            editor.remove("111").commit();
//                historyAdapter.setDisplayCount(historyContentList.size());
//                historyAdapter.notifyDataSetChanged();
//            }
//        }
//    };
//
//    private void onHistoryHideToggle(){
//        isHidePartialHistory = !isHidePartialHistory;
//        if (isHidePartialHistory){
//            int displayCount = historyContentList.size() > 4 ? 4 : historyContentList.size();
//            historyAdapter.setDisplayCount(displayCount);
//            ivHistoryArrow.setRotation(180);
//        }else{
//            historyAdapter.setDisplayCount(historyContentList.size());
//            ivHistoryArrow.setRotation(0);
//        }
//
//        historyAdapter.notifyDataSetChanged();
//    }
//
//    private void onHistoryDeleteToggle(){
//        isInHistoryDelete = !isInHistoryDelete;
//
//        if (isInHistoryDelete){
//            //显示所有历史记录
//            if (isHidePartialHistory){
//                onHistoryHideToggle();
//            }
//
//            historyAdapter.setShowDelete(true);
//            ivHistoryDelete.setVisibility(View.GONE);
//            tvHistoryDeleteFinish.setVisibility(View.VISIBLE);
//        }else{
//            historyAdapter.setShowDelete(false);
//            ivHistoryDelete.setVisibility(View.VISIBLE);
//            tvHistoryDeleteFinish.setVisibility(View.GONE);
//        }
//
//        //在删除历史不能点击历史记录
//        ivHistoryArrow.setVisibility(isInHistoryDelete ? View.GONE : View.VISIBLE);
//        tvHistory.setClickable(!isInHistoryDelete);
//
//        historyAdapter.notifyDataSetChanged();
//    }
//
//    private void onRecommendBarToggle(){
//        isShowRecommend = !isShowRecommend;
//
//        llRecommend1Bar.setVisibility(isShowRecommend ? View.VISIBLE : View.GONE);
//        llRecommend2Bar.setVisibility(isShowRecommend ? View.VISIBLE : View.GONE);
//        llShowRecommendBar.setVisibility(!isShowRecommend ? View.VISIBLE : View.GONE);
//    }
//
//    public void onClick(View view){
//        switch (view.getId()){
//            case R.id.tv_cancel:
//                finish();
//                break;
//
//            case R.id.tv_history:
//            case R.id.iv_arrow:
//                onHistoryHideToggle();
//                break;
//
//            case R.id.iv_delete:
//            case R.id.tv_finish:
//                onHistoryDeleteToggle();
//                break;
//
//            case R.id.iv_recommend_toggle:
//            case R.id.linear_show_recommend:
//                onRecommendBarToggle();
//                break;
//        }
//    }
//}


//    private FlowLayout mFlowLayout;
//    private LayoutInflater mInflater;
//    private String[] mVals = new String[]{"Java", "Android", "iOS", "Python",
//            "Mac OS", "PHP", "JavaScript", "Objective-C",
//            "Groovy", "Pascal", "Ruby", "Go", "Swift"};//数据模拟，实际应从网络获取此数据
//
//    /************
//     * 以上为流式标签相关
//     ************/
//    public static final String EXTRA_KEY_TYPE = "extra_key_type";
//    public static final String EXTRA_KEY_KEYWORD = "extra_key_keyword";
//    public static final String KEY_SEARCH_HISTORY_KEYWORD = "key_search_history_keyword";
//    private SharedPreferences mPref;//使用SharedPreferences记录搜索历史
//    private String mType;
//    private EditText input;
//    private Button btn_search;
//    private List<String> mHistoryKeywords;//记录文本
//    private ArrayAdapter<String> mArrAdapter;//搜索历史适配器
//    private LinearLayout mSearchHistoryLl;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_search_tool_bar);
//        initFlowView();
//        initHistoryView();
//    }
//
//    private void initFlowView() {
//        mInflater = LayoutInflater.from(this);
//        mFlowLayout = (FlowLayout) findViewById(R.id.flowlayout);
//        initData();
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask(){
//            public void run()
//                {
//                    InputMethodManager inputManager = (InputMethodManager)input.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                    inputManager.showSoftInput(input, 0);
//                }
//            },300);
//    }
//    /**
//     * 将数据放入流式布局
//     */
//    private void initData() {
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
//                    Toast.makeText(SearchToolBar.this, str, Toast.LENGTH_LONG).show();
//                }
//            });
//            mFlowLayout.addView(tv);
//        }
//    }
//
//    /************
//     * 以上为流式标签相关
//     ************/
//
//    private void initHistoryView() {
//        input = (EditText) findViewById(R.id.et_input);
//
//        /**
//         * 对焦重置
//         * **/
//        input.setFocusable(true);
//        input.setFocusableInTouchMode(true);
//        input.requestFocus();
//
//        btn_search = (Button) findViewById(R.id.btn_search);
//        btn_search.setOnClickListener(this);
//
//        mPref = getSharedPreferences("input", Activity.MODE_PRIVATE);
//        mType = getIntent().getStringExtra(EXTRA_KEY_TYPE);
//        String keyword = getIntent().getStringExtra(EXTRA_KEY_KEYWORD);
//        if (!TextUtils.isEmpty(keyword)) {
//            input.setText(keyword);
//        }
//        mHistoryKeywords = new ArrayList<String>();
////        input = (EditText) findViewById(R.id.et_input);
//
//        input.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (s.length() == 0) {
//                    if (mHistoryKeywords.size() > 0) {
//                        mSearchHistoryLl.setVisibility(View.VISIBLE);
//                    } else {
//                        mSearchHistoryLl.setVisibility(View.GONE);
//                    }
//                } else {
//                    mSearchHistoryLl.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//        initSearchHistory();
//    }
//
//    /**
//     * 初始化搜索历史记录
//     */
//    public void initSearchHistory() {
//        mSearchHistoryLl = (LinearLayout) findViewById(R.id.search_history_ll);
//        ListView listView = (ListView) findViewById(R.id.search_history_lv);
//        findViewById(R.id.clear_history_btn).setOnClickListener(this);
//        String history = mPref.getString(KEY_SEARCH_HISTORY_KEYWORD, "");
//        if (!TextUtils.isEmpty(history)) {
//            List<String> list = new ArrayList<String>();
//            for (Object o : history.split(",")) {
//                list.add((String) o);
//            }
//            mHistoryKeywords = list;
//        }
//        if (mHistoryKeywords.size() > 0) {
//            mSearchHistoryLl.setVisibility(View.VISIBLE);
//        } else {
//            mSearchHistoryLl.setVisibility(View.GONE);
//        }
//        mArrAdapter = new ArrayAdapter<String>(this, R.layout.item_search_history, mHistoryKeywords);
//        listView.setAdapter(mArrAdapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                input.setText(mHistoryKeywords.get(i));
//                mSearchHistoryLl.setVisibility(View.GONE);
//            }
//        });
//        mArrAdapter.notifyDataSetChanged();
//    }
//
//    /**
//     * 储存搜索历史
//     */
//    public void save() {
//        String text = input.getText().toString();
//        String oldText = mPref.getString(KEY_SEARCH_HISTORY_KEYWORD, "");
//        Log.e("tag", "" + oldText);
//        Log.e("Tag", "" + text);
//        Log.e("Tag", "" + oldText.contains(text));
//        if (!TextUtils.isEmpty(text) && !(oldText.contains(text))) {
//            if (mHistoryKeywords.size() > 5) {
//                //最多保存条数
//                return;
//            }
//            SharedPreferences.Editor editor = mPref.edit();
//            editor.putString(KEY_SEARCH_HISTORY_KEYWORD, text + "," + oldText);
//            editor.commit();
//            mHistoryKeywords.add(0, text);
//        }
//        mArrAdapter.notifyDataSetChanged();
//    }
//
//    /**
//     * 清除历史纪录
//     */
//    public void cleanHistory() {
//        mPref = getSharedPreferences("input", MODE_PRIVATE);
//        SharedPreferences.Editor editor = mPref.edit();
//        editor.remove(KEY_SEARCH_HISTORY_KEYWORD).commit();
//        mHistoryKeywords.clear();
//        mArrAdapter.notifyDataSetChanged();
//        mSearchHistoryLl.setVisibility(View.GONE);
//        Toast.makeText(SearchToolBar.this, "清楚搜索历史成功", Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btn_search:
//                String keywords = input.getText().toString();
//                if (!TextUtils.isEmpty(keywords)) {
//                    Toast.makeText(SearchToolBar.this, keywords + "save成功", Toast.LENGTH_LONG).show();
//                    save();
//                } else {
//                    Toast.makeText(SearchToolBar.this, "请输入搜索内容", Toast.LENGTH_LONG).show();
//                }
//                break;
//            case R.id.clear_history_btn:
//                cleanHistory();
//                break;
//            default:
//                break;
//        }
//
//    }
//}
