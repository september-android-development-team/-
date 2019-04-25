package com.test.september.View.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.test.september.Adapter.ListViewAdapter;
import com.test.september.R;
import com.test.september.View.Activity.Main2Activity;
import com.test.september.View.Activity.SettingsActivity.Setting;
import com.test.september.View.Activity.Training_course;
import com.test.september.View.Activity.UserActivity.Personal_data;
import com.test.september.View.Home;
import com.test.september.View.Login;
import com.test.september.View.MyView.Dialogview.NiftyDialogBuilder;
import com.test.september.util.PreUtils;
import com.test.september.util.effects.control.Effectstype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StatusFragment extends BaseFragment {

    private Effectstype effect;
    private final static String TAG = StatusFragment.class.getSimpleName();
    private TextView textView;

    private ListView listView;
    //    private Button Test;
    private ImageButton to_Setting;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_status, null);
        Log.e(TAG, "状态页面的Fragment的UI被初始化了");
//        Intent intent=new Intent(mContext,Login.class);
//        startActivity(intent);
        return view;

    }


    @Override
    public void initData() {
        super.initData();
//        Dialog();
        Log.e(TAG, "状态页面的Fragment的数据被初始化了");
    }
    public void Dialog() {
        //实现处理
        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(mContext);
        effect = Effectstype.Flipv;
        dialogBuilder
                .withTitle("注册登录")                                  //.withTitle(null)  no title
                .withTitleColor("#FF0000")                                  //def
                .withDividerColor("#11000000")                              //def
                .withMessage("登录以享受更多服务!")                     //.withMessage(null)  no Msg
                .withMessageColor("#000000")                              //def  | withMessageColor(int resid)
                .withDialogColor("#FFFFFF")                               //def  | withDialogColor(int resid)                               //def
                .withIcon(getResources().getDrawable(R.drawable.ic_sign_out_button))
                .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
                .withDuration(300)                                          //def
                .withEffect(effect)                                         //def Effectstype.Slidetop
                .withButton1Text("确认")                                      //def gone

//                .setCustomView(R.layout.custom_view,v.getContext())         //.setCustomView(View or ResId,context)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                        Intent intent=new Intent(mContext, Login.class);
                        startActivity(intent);
                    }
                })
                .show();

    }
    @Override
    public void setViewListener() {

    }
}