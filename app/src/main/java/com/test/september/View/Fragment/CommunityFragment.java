package com.test.september.View.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.september.R;


public class CommunityFragment extends BaseFragment {
    private final static String TAG = RewardFragment.class.getSimpleName();


    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_community, null);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e(TAG,"消息页面的Fragment的数据被初始化了");
    }
}
