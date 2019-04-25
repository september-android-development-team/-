package com.test.september.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.test.september.Adapter.ListViewAdapter;
import com.test.september.R;
import com.test.september.View.Activity.Main2Activity;
import com.test.september.View.Activity.SettingsActivity.Setting;
import com.test.september.View.Activity.Training_course;
import com.test.september.View.Activity.UserActivity.Personal_data;
import com.test.september.View.Login;
import com.test.september.util.PreUtils;
import com.test.september.util.SharedPreferences.SharedPrefUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public class UserCartFragment extends BaseFragment {
    private final static String TAG = UserCartFragment.class.getSimpleName();


//    @BindView(R.id.sign_out)
//    Button signOut;
    Unbinder unbinder;


//    用户中心列表


    private TextView go_login;

    private ListView listView;
//    private Button Test;
    private ImageButton to_Setting;
//    private Button To_TrainingCourse;
//    private Button To_PersonalData;
    //    private ListView listView;
    private LinearLayout linearLayout;
    private boolean isLogin=false;
    @Override
    public View initView() {

//        boolean theme = PreUtils.getBoolean(mContext, "theme", false);
//        //进入之前设置主题
//        final Context contextThemeWrapper = new ContextThemeWrapper(
//                getActivity(), theme ? R.style.AppTheme_Dark : R.style.AppTheme_light);
//        LayoutInflater localInflater = getLayoutInflater().cloneInContext(contextThemeWrapper);

//        if (theme) {
//            mContext.setTheme(R.style.AppTheme_Dark);
//        } else {
//            mContext.setTheme(R.style.AppTheme_light);
//        }
//        PreUtils.putBoolean(mContext, "theme", theme);

        View view = View.inflate(mContext,R.layout.fragment_user_cart,null);

        linearLayout=view.findViewById(R.id.login_bar);
//        listView=view.findViewById(R.id.user_lv);
//        signOut=view.findViewById(R.id.sign_out);
        to_Setting=view.findViewById(R.id.to_setting);
        go_login=view.findViewById(R.id.go_login);
        go_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, Login.class);
                startActivity(intent);
            }
        });
//        Test=view.findViewById(R.id.test);
//        To_TrainingCourse=view.findViewById(R.id.to_training_course_manage);
//        To_PersonalData=view.findViewById(R.id.To_Personal_data);
        isLogin=(boolean) SharedPrefUtil.getParam(mContext, SharedPrefUtil.IS_LOGIN, false);
        if(isLogin){
            linearLayout.setVisibility(View.GONE);
        }else {
            linearLayout.setVisibility(View.VISIBLE);
        }
        listView = (ListView) view.findViewById(R.id.listview);
        List<Map<String, Object>> list=getData();
        listView.setAdapter(new ListViewAdapter(getActivity(), list));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                listView.getItemAtPosition(position);
                switch (position)
                {
                    case 0:
                    {
                        Intent intent=new Intent(mContext, Personal_data.class);
                        startActivity(intent);
                        break;
                    }
                    case 1:
                    {
                        Intent intent=new Intent(mContext,Main2Activity.class);
                        startActivity(intent);
                        break;
                    }
                    case 2:
                    {
                        Toast.makeText(mContext,"点击",Toast.LENGTH_LONG).show();
                        break;
                    }
                    case 3:
                    {
                        Toast.makeText(mContext,"点击",Toast.LENGTH_LONG).show();
                        break;
                    }
                    case 4:
                    {
                        Intent intent=new Intent(mContext, Training_course.class);
                        startActivity(intent);
                        break;
                    }
                    case 5:
                    {
                        Toast.makeText(mContext,"点击",Toast.LENGTH_LONG).show();
                        break;
                    }
                    case 6:
                    {
                        boolean theme = PreUtils.getBoolean(mContext, "theme", true);
                        if (!theme) {
                            mContext.setTheme(R.style.AppTheme_light);
                        } else {
                            mContext.setTheme(R.style.AppTheme_Dark);
                        }
//                        mContext.//重建Activity
                        PreUtils.putBoolean(mContext, "theme", !theme);
                        Toast.makeText(mContext, "" + theme, Toast.LENGTH_SHORT).show();
                        break;
                    }
//                    case 7:
//                    {
//                        Intent intent=new Intent(mContext, Login.class);
//                        startActivity(intent);
//                        break;
//                    }
                    default:{

                    }

                }
            }
        });
//        To_PersonalData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//        To_TrainingCourse.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//        Test.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        to_Setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, Setting.class);
                startActivity(intent);
            }
        });
//        signOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(mContext, Login.class);
//                startActivity(intent);
//            }
//        });


        Log.e(TAG, "个人页面的Fragment的UI被初始化了");
        return view;

    }
    /***
     *
     * 个人中心界面适配
     *
     * */
    public List<Map<String, Object>> getData(){
        List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
//        for (int i = 0; i < 10; i++) {
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("image", R.drawable.ic_personal_profile);
            map.put("title", "个人资料");
//            map.put("right_arrow",R.drawable.ic_right_arrow);
//            map.put("info", "这是一个详细信息");
            Map<String, Object> map2=new HashMap<String, Object>();
            map2.put("image", R.drawable.ic_debug);
            map2.put("title", "调试");
//            map2.put("right_arrow",R.drawable.ic_right_arrow);
//            map2.put("info", "这是一个详细信息");
            Map<String, Object> map3=new HashMap<String, Object>();
            map3.put("image", R.drawable.ic_security);
            map3.put("title", "安全中心");
//            map3.put("right_arrow",R.drawable.ic_right_arrow);
//            map3.put("info", "这是一个详细信息");
            Map<String, Object> map4=new HashMap<String, Object>();
            map4.put("image", R.drawable.ic_management);
            map4.put("title", "资产管理");
//            map4.put("right_arrow",R.drawable.ic_right_arrow);
//            map4.put("info", "这是一个详细信息");
            Map<String, Object> map5=new HashMap<String, Object>();
            map5.put("image", R.drawable.ic_education);
            map5.put("title", "培训课程管理");
//            map5.put("right_arrow",R.drawable.ic_right_arrow);
//            map5.put("info", "这是一个详细信息");
            Map<String, Object> map6=new HashMap<String, Object>();
            map6.put("image", R.drawable.ic_analytics);
            map6.put("title", "评价管理");
//            map6.put("right_arrow",R.drawable.ic_right_arrow);
//            map6.put("info", "这是一个详细信息");
            Map<String, Object> map7=new HashMap<String, Object>();
            map7.put("image", R.drawable.ic_launcher_foreground);
            map7.put("title", "培训课程管理");
//            map7.put("right_arrow",R.drawable.ic_right_arrow);
//            map7.put("info", "这是一个详细信息");
//            Map<String, Object> map8=new HashMap<String, Object>();
//            map8.put("image", R.drawable.ic_launcher_foreground);
//            map8.put("title", "退出登录");
            list.add(map);
            list.add(map2);
            list.add(map3);
            list.add(map4);
            list.add(map5);
            list.add(map6);
            list.add(map7);
//            list.add(map8);
//        }
        return list;
    }

    @Override
    public void initData() {
        super.initData();
//        ArrayList list = new ArrayList();
//        for(int i=0;i<100;i++)
//        {
//            list.add("i="+i);
//        }
//
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//            //list点击事件
//            @Override
//            public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
//            {
//                // TODO: Implement this method
//                switch(p3){
//                    case 0://第一个item
//                        Intent intent=new Intent(mContext, Setting.class);
//                        startActivity(intent);
//                        break;
//                    case 1://第二个item
//                        Toast.makeText(mContext,"AIDE   了解  分享",Toast.LENGTH_SHORT).show();
//                        break;
//                    case 2://第三个item
//                        Toast.makeText(mContext,"AIDE   玩转  分享",Toast.LENGTH_SHORT).show();
//                        break;
//                }
//            }
//
//
//        });
//        UserAdapter adapter = new UserAdapter(mContext,list);
//        listView.setAdapter(adapter);
        Log.e(TAG, "个人页面的Fragment的数据被初始化了");
    }

    @Override
    public void setViewListener() {

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
