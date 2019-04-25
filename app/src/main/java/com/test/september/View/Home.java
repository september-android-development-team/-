package com.test.september.View;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.taobao.sophix.SophixManager;
import com.test.september.Adapter.PagerAdapter;
import com.test.september.App;
import com.test.september.R;
import com.test.september.Service.GrayService;
import com.test.september.Service.JobSchedulerService;
import com.test.september.Service.KeepliveService;
import com.test.september.Service.PatchService;
import com.test.september.View.Activity.SettingsActivity.Setting;
import com.test.september.View.Activity.UserActivity.Personal_data;
import com.test.september.View.Fragment.BaseFragment;
import com.test.september.View.Fragment.CommunityFragment;
import com.test.september.View.Fragment.HomeFragment;
import com.test.september.View.Fragment.RewardFragment;
import com.test.september.View.Fragment.StatusFragment;
import com.test.september.View.Fragment.UserCartFragment;
import com.test.september.View.MyView.Dialogview.NiftyDialogBuilder;
import com.test.september.util.SharedPreferences.SharedPrefUtil;
import com.test.september.util.StatusBarUtil;
import com.test.september.util.effects.control.Effectstype;
import com.yzq.zxinglibrary.common.Constant;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

//import com.nyl.shoppingmall.community.fragment.CommunityFragment;
//import com.nyl.shoppingmall.shoppingcart.fragment.ShoppingCartFragment;
//import com.nyl.shoppingmall.type.fragment.TypeCartFragment;
//import com.nyl.shoppingmall.user.fragment.UserCartFragment;


public class Home extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{
    //    int REQUEST_CODE_SCAN=111000;
//    @BindView(R.id.frameLayout)
//    FrameLayout frameLayout;
    @BindView(R.id.rb_home)
    RadioButton rbHome;
    @BindView(R.id.rb_type)
    RadioButton rbType;
    @BindView(R.id.rb_community)
    RadioButton rbCommunity;
    //    @BindView(R.id.rb_cart)
//    RadioButton rbCart;
    @BindView(R.id.rb_user)
    RadioButton rbUser;
    @BindView(R.id.rg_main)
    RadioGroup rgMain;

    //装fragment的实例集合
    private ArrayList<BaseFragment> fragments;
    private Effectstype effect;
//    private int position=0;
    RadioGroup mRadioGroup;
    private ViewPager mViewPager;
    int current = 0;
    private boolean isLogin=false;

    //缓存Fragment或上次显示的Fragment
    private Fragment tempFragment;
    private AlertDialog alertDialog;
    private BroadcastReceiver receiver;

    private Handler delayHandler = new Handler();
    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        SophixManager.getInstance().queryAndLoadNewPatch();

        setContentView(R.layout.activity_home);
        initViews();
        App.getInstance().addActivity(this);
        isLogin=(Boolean) SharedPrefUtil.getParam(this, SharedPrefUtil.IS_LOGIN, false);
        Log.d("isLogin",String.valueOf(isLogin));
        Log.d("keepalive", "进程编号= " + android.os.Process.myPid());
        //ButterKnife和当前Activity绑定
        ButterKnife.bind(this);
        SophixManager.getInstance().queryAndLoadNewPatch();

        //启动补丁更新服务
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            startForegroundService(new Intent(Home.this, PatchService.class));
//        } else {
//            startService(new Intent(Home.this, PatchService.class));
//        }

        //注册监听广播
        if (receiver == null) {
            receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (intent.getAction() != null) {
                        if (intent.getAction().equals("PATCH_RELAUNCH")) {
                            //显示补丁冷启动提示
                            ShowPatchRelaunchTip();
                            //停止延时跳转，处理补丁冷启动任务
//                            delayHandler.removeCallbacksAndMessages(null);
                        } else if (intent.getAction().equals("CHECK_UPGRADE")) {
                            //停止延时跳转，处理应用更新任务
//                            delayHandler.removeCallbacksAndMessages(null);
//                            String versionCodeName = intent.getStringExtra("VersionCodeName");
//                            String versionInfo = intent.getStringExtra("VersionInfo");
//                            String downloadURL = intent.getStringExtra("DownloadURL");
//                            String fileSize = intent.getStringExtra("FileSize");
//                            //显示更新提示
//                            ShowUpgradeTip(versionCodeName, versionInfo, downloadURL, fileSize);
                        }
                    }
                }
            };
            //注册广播监听
            this.registerReceiver(receiver, new IntentFilter("PATCH_RELAUNCH"));
            this.registerReceiver(receiver, new IntentFilter("CHECK_UPGRADE"));
        }

        if(isLogin) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                startForegroundService(new Intent(Home.this, GrayService.class));
            } else {
                startService(new Intent(Home.this, GrayService.class));
            }
        }else {
            Log.d("isLogin","还未登录");
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // 权限还没有授予，进行申请
            ActivityCompat.requestPermissions((Activity) this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 200); // 申请的 requestCode 为 200
        }
        //初始化Fragment
        Init();
        initFragment();
        //设置RadioGroup的监听
//        initListener();
        initEvents();
//        startService(KeepliveService.getIntentStart(this));
    }

    public void Init(){
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mRadioGroup = (RadioGroup) findViewById(R.id.rg_main);
        mRadioGroup.check(R.id.rb_home);
    }
    private void initEvents() {
        mRadioGroup.setOnCheckedChangeListener(this);
        mViewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), fragments));
        mViewPager.setCurrentItem(0);
//        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setOnPageChangeListener(new MyListener());
    }
    private void initViews() {
        //沉浸式处理

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (checkedId){
            case R.id.rb_home:
                current = 0;
                break;
            case R.id.rb_type:
                current = 1;
                break;
            case R.id.rb_community:
                current = 2;
                break;
            case R.id.rb_user:
                current = 3;
                break;
        }

        //根据位置得到相应的Fragment
//                BaseFragment baseFragment = getFragment(current);
//                switchFragment(mViewPager.getCurrentItem(), baseFragment);

        if(mViewPager.getCurrentItem() != current){
            mViewPager.setCurrentItem(current,false);
        }
    }


    class MyListener implements android.support.v4.view.ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            int currentItem = mViewPager.getCurrentItem();

            switch (currentItem){
                case 0:
                    mRadioGroup.check(R.id.rb_home);
                    break;
                case 1:
                    mRadioGroup.check(R.id.rb_type);
                    break;
                case 2:
                    mRadioGroup.check(R.id.rb_community);
                    break;
                case 3:
                    mRadioGroup.check(R.id.rb_user);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
//    private void initListener() {
//        rgMain.check(R.id.rb_home);
//        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                switch (i) {
//                    case R.id.rb_home: //首页
//                        position = 0;
//                        break;
//                    case R.id.rb_type: //悬赏
////                        if (!isLogin){
////                            position=4;
////                            Intent intent=new Intent(Home.this,Login.class);
////                            startActivity(intent);
////                        }else {
//                            position = 1;
////                        }
//                        break;
//                    case R.id.rb_community: //消息
//                        position = 2;
//                        break;
//                    case R.id.rb_user: //个人中心
////                        if (!isLogin){
////                            position=4;
////                            Intent intent=new Intent(Home.this,Login.class);
////                            startActivity(intent);
////                        }else {
//                            position = 3;
////                        }
//                        break;
//                    default:
//                        position = 2;
//                        break;
//                }
//                //根据位置得到相应的Fragment
//
//                BaseFragment baseFragment = getFragment(position);
//                /**
//                 * 第一个参数: 上次显示的Fragment
//                 * 第二个参数: 当前正要显示的Fragment
//                 */
//
////                if (!isLogin){
////                    initFragment();
////                }
//                switchFragment(tempFragment, baseFragment);
//            }
//        });
//    }



    /**
     * 添加的时候按照顺序
     */
    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());   //添加主页界面
        fragments.add(new RewardFragment()); //添加悬赏界面
        fragments.add(new CommunityFragment()); //添加消息界面
        fragments.add(new UserCartFragment());  //添加用户界面
//        fragments.add(new StatusFragment());  //状态过渡界面
//        /**初始化Fragment*/
//        BaseFragment baseFragment = getFragment(position);
//        switchFragment(tempFragment, baseFragment);
    }
    public void refreshFragment(){

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
//
//    /**
//     * 切换Fragment
//     *
//     * @param fragment
//     * @param nextFragment
//     */
//    private void switchFragment(Fragment fragment, BaseFragment nextFragment) {
//
//        if (tempFragment != nextFragment) {
//            tempFragment = nextFragment;
//            if (nextFragment != null) {
//
//                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//
//                //判断nextFragment是否添加成功
//                if (!nextFragment.isAdded()) {
//                    //隐藏当前的Fragment
//                    if (fragment != null) {
//                        transaction.hide(fragment);
//                    }
//                    //添加Fragment
//                    transaction.add(R.id.frameLayout, nextFragment).commit();
//                } else {
//
//                    //隐藏当前Fragment
//                    if (fragment != null) {
////                        if(!isLogin){
////                            transaction.replace(R.id.frameLayout,fragment);
////                            transaction.hide(nextFragment);
////                        }else {
//                            transaction.hide(fragment);
//                        }
////                    }
//                    transaction.show(nextFragment).commit();
//                }
//            }
//        }
//    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void dialog() {

        final NiftyDialogBuilder dialogBuilder=NiftyDialogBuilder.getInstance(this);
        effect=Effectstype.SlideBottom;
        dialogBuilder
                .withTitle("退出")                                  //.withTitle(null)  no title
                .withTitleColor("#00ffff")                                  //def
                .withDividerColor("#11000000")                              //def
                .withMessage("确定要离开吗?")                     //.withMessage(null)  no Msg
                .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
                .withDialogColor("#ABE2E1D9")                               //def  | withDialogColor(int resid)                               //def
                .withIcon(getResources().getDrawable(R.drawable.ic_launcher_foreground))
                .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
                .withDuration(300)                                          //def
                .withEffect(effect)                                         //def Effectstype.Slidetop
                .withButton1Text("确定")                                      //def gone
                .withButton2Text("取消")                                  //def gone
//                .setCustomView(R.layout.custom_view,v.getContext())         //.setCustomView(View or ResId,context)
                . setButton1Click(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder.dismiss();
                Home.this.finish();
//                System.exit(0);
                App.getInstance().destory();

            }
        })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                    }
                })
                .show();
    }


    /**
     * 显示更新提示
     *
     * @param versionCodeName
     * @param versionInfo
     * @param downloadURL
     */
    public void ShowUpgradeTip(String versionCodeName, String versionInfo, final String downloadURL, String fileSize) {
        AlertDialog.Builder updateDialogBuilder = new AlertDialog.Builder(this);
        updateDialogBuilder.setTitle("新版本更新");
        StringBuilder dialogMessage = new StringBuilder("新版本:" + versionCodeName + "，大小:" + fileSize);
        String infos[] = versionInfo.split(";");
        for (String string : infos) {
            dialogMessage.append("\n");
            dialogMessage.append(string);
        }
        updateDialogBuilder.setMessage(dialogMessage);
        updateDialogBuilder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                DownLoadNewVersion(downloadURL);
            }
        });
        updateDialogBuilder.setNegativeButton("暂不更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                SwitchPageAfterHandledUpdateTip();
            }
        });
        updateDialogBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
//                SwitchPageAfterHandledUpdateTip();
            }
        });
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        alertDialog = updateDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
//        RemoveCallBacksAndMessages();
        UnregisterReceiver();
    }



    /**
     * 注销广播
     */
    public void UnregisterReceiver() {
        if (receiver != null) {
            this.unregisterReceiver(receiver);
        }
    }

    /**
     * 弹出补丁冷启动提示
     */
    public void ShowPatchRelaunchTip(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("新补丁更新");
        alertDialogBuilder.setMessage("新补丁已成功加入，为了您的更优质体验，请重启应用以使得补丁生效");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("关闭应用", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                PatchRelaunchAndStopProcess();
            }
        });
        alertDialogBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                PatchRelaunchAndStopProcess();
            }
        });
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }





    /**
     * 结束应用程序使补丁生效
     */
    public void PatchRelaunchAndStopProcess(){
        SophixManager.getInstance().killProcessSafely();
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            dialog();
            return true;
        }
        return onKeyDown(keyCode,event);
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
////        App.getInstance().destory();
//    }
}