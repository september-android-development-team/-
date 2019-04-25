package com.test.september.View.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.test.september.Contact.Contact;
import com.test.september.R;
import com.test.september.View.Activity.CommunityActivity.ContactBook;
import com.test.september.View.Activity.CommunityActivity.Schedule;

import java.util.ArrayList;
import java.util.List;


public class CommunityFragment extends BaseFragment {
    private final static String TAG = CommunityFragment.class.getSimpleName();
    private LinearLayout To_Schedule;
    private LinearLayout To_Contacts;


    private static String[] permissions = {//动态申请权限列表
            "android.permission.READ_CONTACTS",
            "android.permission.WRITE_CONTACTS",
            "android.permission.GET_ACCOUNTS"};//读通讯录权限的权限名

    private static final int PERMISSION_REQUEST = 11011;
    List<String> mPermissionList = new ArrayList<>();
    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_community, null);
        To_Schedule=view.findViewById(R.id.To_Schedule);
        To_Contacts=view.findViewById(R.id.To_Contacts);
        To_Contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
                Log.d("tap","被点击了!");
            }
        });
        To_Schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, Schedule.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e(TAG,"消息页面的Fragment的数据被初始化了");
    }

    @Override
    public void setViewListener() {

    }

    /**
     * 检查权限
     */
    private void checkPermission() {
        mPermissionList.clear();
        /**
         * 判断哪些权限未授予
         */
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(mContext, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);
            }
        }
        /**
         * 判断是否为空
         */
        if (mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了
            Intent intent = new Intent(mContext, ContactBook.class);
            startActivity(intent);
        } else {//请求权限方法
            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
            requestPermissions(permissions, PERMISSION_REQUEST);
        }
    }

    /**
     * 响应授权
     * 这里不管用户是否拒绝，都进入首页，不再重复申请权限
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                switch (requestCode) {
            case PERMISSION_REQUEST:
                Intent intent = new Intent(mContext, ContactBook.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
    //    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case PERMISSION_REQUEST:
//                Intent intent = new Intent(mContext, ContactBook.class);
//                startActivity(intent);
//                break;
//            default:
//                break;
//        }
//    }
}
