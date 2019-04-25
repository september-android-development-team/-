package com.test.september.View.Fragment.SearchFragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.test.september.Adapter.ContentAdapter2;
import com.test.september.R;
import com.test.september.View.Fragment.BaseFragment;
import com.test.september.listener.OnItemClickListener;
import com.test.september.listener.OnItemDeleteListener;
import com.test.september.listener.search_contact;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class SearchHistoryFragment extends BaseFragment implements View.OnClickListener{
    private RecyclerView recyclerViewHistory;
    private ImageView ivHistoryArrow;
    private TextView tvHistory;
    private TextView search;
    private EditText input;
    private ImageView ivHistoryDelete;
    private TextView tvHistoryDeleteFinish;

    private LinearLayout llShowRecommendBar;
    private LinearLayout llRecommend1Bar;
    private LinearLayout llRecommend2Bar;

    //    private List<String> recommend1ContentList = new ArrayList<>();
//    private List<String> recommend2ContentList = new ArrayList<>();
    private List<String> historyContentList = new ArrayList<>();

    //    private ContentAdapter1 recommend1Adapter;
//    private ContentAdapter2 recommend2Adapter;
    private ContentAdapter2 historyAdapter;
    private SharedPreferences mPref;//使用SharedPreferences记录搜索历史
    private boolean isShowRecommend = true; //是否显示推荐词栏
    private boolean isHidePartialHistory = true; //是否隐藏部分历史,默认为true
    private boolean isInHistoryDelete = false; //是否处于删除历史模式
    private List<String> mHistoryKeywords;//记录文本

    private search_contact sendMessage;
//    private search_contact sendSearchText;
    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_search_history, null);
//        recyclerViewRecommend1 = findViewById(R.id.rv_recommend_bar_1);
//        recyclerViewRecommend2 = findViewById(R.id.rv_recommend_bar_2);

//        sendSearchText=(search_contact)getActivity();
        sendMessage = (search_contact) getActivity();
        recyclerViewHistory = view.findViewById(R.id.rv_recommend_bar_history);
        tvHistory = view.findViewById(R.id.tv_history);
        ivHistoryArrow = view.findViewById(R.id.iv_arrow);
        ivHistoryDelete = view.findViewById(R.id.iv_delete);
        tvHistoryDeleteFinish = view.findViewById(R.id.tv_finish);
        llShowRecommendBar = view.findViewById(R.id.linear_show_recommend);
//        llRecommend1Bar = view.findViewById(R.id.linear_recommendbar1);
//        llRecommend2Bar = view.findViewById(R.id.linear_recommendbar2);
        mPref = mContext.getSharedPreferences("input", MODE_PRIVATE);
        input = getActivity().findViewById(R.id.et_input);
        search=getActivity().findViewById(R.id.tv_cancel);
        mHistoryKeywords = new ArrayList<String>();


        ivHistoryArrow.setOnClickListener(this);
        ivHistoryDelete.setOnClickListener(this);
        tvHistoryDeleteFinish.setOnClickListener(this);
        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                save();
//                sendMessage.sendSearchText();
                sendMessage.sendMessage();
                //本文来自原创：http://www.cnblogs.com/jiangbeixiaoqiao/
                Intent intent = new Intent("jerry");
                intent.putExtra("change", "yes");
                intent.putExtra("search",input.getText().toString());
                LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
            }
        });



        DividerItemDecoration itemDecorationHor = new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL);
        itemDecorationHor.setDrawable(new ColorDrawable(Color.parseColor("#e0e0e0")));

        DividerItemDecoration itemDecorationVer = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        itemDecorationVer.setDrawable(new ColorDrawable(Color.parseColor("#e0e0e0")));

//        recommend1Adapter = new ContentAdapter1(getApplicationContext(), recommend1ContentList);
//        recommend1Adapter.setItemClickListener(recommend1BarItemClickListener);

//        GridLayoutManager gmRecommend1 = new GridLayoutManager(getApplicationContext(), 3);
//        recyclerViewRecommend1.setLayoutManager(gmRecommend1);
//        recyclerViewRecommend1.addItemDecoration(itemDecorationHor);
//        recyclerViewRecommend1.addItemDecoration(itemDecorationVer);
//        recyclerViewRecommend1.setAdapter(recommend1Adapter);

//        recommend2Adapter = new ContentAdapter2(getApplicationContext(), recommend2ContentList);
//        recommend2Adapter.setItemClickListener(recommend2BarItemClickListener);

//        GridLayoutManager gmRecommend2 = new GridLayoutManager(getApplicationContext(), 2);
//        recyclerViewRecommend2.setLayoutManager(gmRecommend2);
//        recyclerViewRecommend2.addItemDecoration(itemDecorationHor);
//        recyclerViewRecommend2.addItemDecoration(itemDecorationVer);
//        recyclerViewRecommend2.setAdapter(recommend2Adapter);
        initsearchhistory();

        historyAdapter = new ContentAdapter2(mContext, historyContentList);
        //历史记录先不显示全部
        int displayCount = historyContentList.size() > 4 ? 4 : historyContentList.size();
        historyAdapter.setDisplayCount(displayCount);
        if(isInHistoryDelete){
            historyAdapter.setItemClickListener(historyBarItemClickListener);
        }

        historyAdapter.setItemDeleteListener(historyBarItemDeleteListener);

        GridLayoutManager gmHistory = new GridLayoutManager(mContext, 2);
        recyclerViewHistory.setLayoutManager(gmHistory);
        recyclerViewHistory.addItemDecoration(itemDecorationHor);
        recyclerViewHistory.addItemDecoration(itemDecorationVer);
        recyclerViewHistory.setAdapter(historyAdapter);
        return view;
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){
            //TODO now visible to user


        } else {
            //TODO now invisible to user

        }
    }

    /**
     * 储存搜索历史
     */
    public void save() {
        String text = input.getText().toString();
        String oldText = mPref.getString("111", "");
        if (!TextUtils.isEmpty(text) && !(oldText.contains(text))) {
            if (historyContentList.size() > 15) {
                //最多保存条数
                return;
            }
            SharedPreferences.Editor editor = mPref.edit();
            editor.putString("111", text + "," + oldText);
            editor.commit();
            historyContentList.add(0, text);
        }
//        initsearchhistory();
        historyAdapter.notifyDataSetChanged();
    }

    public void initsearchhistory(){
        String history = mPref.getString("111", "");
        if (!TextUtils.isEmpty(history)) {
            List<String> list = new ArrayList<String>();
            for (Object o : history.split(",")) {
                list.add((String) o);
            }
            historyContentList = list;
        }
//        if (mHistoryKeywords.size() > 0) {
//            mSearchHistoryLl.setVisibility(View.VISIBLE);
//        } else {
//            mSearchHistoryLl.setVisibility(View.GONE);
//        }
    }




    private OnItemClickListener historyBarItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            Toast.makeText(mContext, String.format("你点击了-%s", historyContentList.get(position)), Toast.LENGTH_SHORT).show();
        }
    };

    private OnItemDeleteListener historyBarItemDeleteListener = new OnItemDeleteListener() {
        @Override
        public void onItemDelete(View view, int position) {
            Toast.makeText(mContext, String.format("你删除了-%s", historyContentList.get(position)), Toast.LENGTH_SHORT).show();
            historyContentList.remove(position);



            String history = mPref.getString("111", "");
            if (!TextUtils.isEmpty(history)) {
                List<String> list = new ArrayList<String>();
                for (Object o : history.split(",")) {
                    list.add((String) o);
                }
                list.remove(list.get(position));

                mPref = mContext.getSharedPreferences("input", MODE_PRIVATE);
                SharedPreferences.Editor editor = mPref.edit();
                editor.remove("111").commit();

//                SharedPreferences.Editor editor = mPref.edit();
                for(int i=0;i<=list.size()-1;i++) {
                    String text = list.get(i);
                    String oldText = mPref.getString("111", "");
                    editor.putString("111", text + "," + oldText);
                    editor.commit();
                }


//            mPref = getSharedPreferences("input", MODE_PRIVATE);
//            SharedPreferences.Editor editor = mPref.edit();
//            editor.remove("111").commit();
                historyAdapter.setDisplayCount(historyContentList.size());
                historyAdapter.notifyDataSetChanged();
            }
        }
    };

    private void onHistoryHideToggle(){
        isHidePartialHistory = !isHidePartialHistory;

        if (isHidePartialHistory){
            int displayCount = historyContentList.size() > 4 ? 4 : historyContentList.size();
            historyAdapter.setDisplayCount(displayCount);

            ivHistoryArrow.setRotation(180);
        }else{
            historyAdapter.setDisplayCount(historyContentList.size());
            ivHistoryArrow.setRotation(0);
        }

        historyAdapter.notifyDataSetChanged();
    }

    private void onHistoryDeleteToggle(){
        isInHistoryDelete = !isInHistoryDelete;

        if (isInHistoryDelete){
            //显示所有历史记录
            if (isHidePartialHistory){
                onHistoryHideToggle();
            }

            historyAdapter.setShowDelete(true);
            ivHistoryDelete.setVisibility(View.GONE);
            tvHistoryDeleteFinish.setVisibility(View.VISIBLE);
        }else{
            historyAdapter.setShowDelete(false);
            ivHistoryDelete.setVisibility(View.VISIBLE);
            tvHistoryDeleteFinish.setVisibility(View.GONE);
        }

        //在删除历史不能点击历史记录
        ivHistoryArrow.setVisibility(isInHistoryDelete ? View.GONE : View.VISIBLE);
        tvHistory.setClickable(isInHistoryDelete);

        historyAdapter.notifyDataSetChanged();
    }

    private void onRecommendBarToggle(){
        isShowRecommend = !isShowRecommend;
        llRecommend1Bar.setVisibility(isShowRecommend ? View.VISIBLE : View.GONE);
        llRecommend2Bar.setVisibility(isShowRecommend ? View.VISIBLE : View.GONE);
        llShowRecommendBar.setVisibility(!isShowRecommend ? View.VISIBLE : View.GONE);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_cancel:
//                finish();
                break;

            case R.id.tv_history:
            case R.id.iv_arrow:
                onHistoryHideToggle();
                break;

            case R.id.iv_delete:
            case R.id.tv_finish:
                onHistoryDeleteToggle();
                break;

            case R.id.iv_recommend_toggle:
            case R.id.linear_show_recommend:
                onRecommendBarToggle();
                break;
        }
    }
    @Override
    public void setViewListener() {

    }
}
