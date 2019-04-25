package com.test.september.View.Fragment;


import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.test.september.R;


/**
.
 */
public class RewardFragment extends BaseFragment {
    private final static String TAG = RewardFragment.class.getSimpleName();


    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_reward, null);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e(TAG,"悬赏页面的Fragment的数据被初始化了");
    }

    @Override
    public void setViewListener() {

    }
}