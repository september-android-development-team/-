package com.test.september.View.Fragment;


import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;


/**
.
 */
public class RewardFragment extends BaseFragment {
    private final static String TAG = RewardFragment.class.getSimpleName();

    private TextView textView;

    @Override
    public View initView() {
        textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(25);
        Log.e(TAG,"悬赏页面的Fragment的UI被初始化了");
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        textView.setText("悬赏");
        Log.e(TAG,"悬赏页面的Fragment的数据被初始化了");
    }
}