package com.test.september.View.Activity.SettingsActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test.september.App;
import com.test.september.R;
import com.test.september.View.Login;
import com.test.september.View.MyView.Dialogview.NiftyDialogBuilder;
import com.test.september.View.MyView.SwitchButton;
import com.test.september.util.SharedPreferences.SharedPrefUtil;
import com.test.september.util.effects.control.Effectstype;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Setting extends AppCompatActivity implements View.OnClickListener {

    TextView aboutPage;
    @BindView(R.id.to_AccountSafe_page)
    TextView toAccountSafePage;
    @BindView(R.id.to_VoiceSetting_page)
    TextView toVoiceSettingPage;
    @BindView(R.id.to_secret_page)
    TextView toSecretPage;
    @BindView(R.id.usual_page)
    TextView usualPage;
    @BindView(R.id.problem_back_page)
    TextView problemBackPage;
    @BindView(R.id.sign_out)
    TextView signOut;

    private Effectstype effect;
    private SwitchButton switch_accept_news, switch_sound, switch_shock, switch_loundspeaker, switch_Red;
    private LinearLayout ll_sound, ll_shock, Theme_change;

    private View mLeftNavigationView, mRightNavigationView;
    private View[] mChildViews;
    private boolean isLogin=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        App.getInstance().addActivity(this);
        ButterKnife.bind(this);
        aboutPage = findViewById(R.id.about_page);
        aboutPage.setOnClickListener(this);
        toAccountSafePage.setOnClickListener(this);
        toVoiceSettingPage.setOnClickListener(this);
        toSecretPage.setOnClickListener(this);
        usualPage.setOnClickListener(this);
        problemBackPage.setOnClickListener(this);
        signOut.setOnClickListener(this);

        isLogin=(boolean) SharedPrefUtil.getParam(this, SharedPrefUtil.IS_LOGIN, false);
        if(isLogin){
            signOut.setVisibility(View.VISIBLE);
        }else {
            signOut.setVisibility(View.INVISIBLE);
        }
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

//        initStatusBar();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.to_AccountSafe_page: {

            }
            break;
            case R.id.to_VoiceSetting_page: {

            }
            break;
            case R.id.to_secret_page: {
                Intent intent = new Intent();
                String secert_page_url="file:///android_asset/secert.html";
                intent.putExtra("url", secert_page_url);
                intent.putExtra("title","隐私政策");
                intent.setClass(Setting.this, WebViewPage.class);
                startActivity(intent);
//                Intent intent = new Intent(Setting.this, WebViewPage.class);
//                startActivity(intent);
            }
            break;
            case R.id.usual_page: {

            }
            break;
            case R.id.problem_back_page: {

            }
            break;
            case R.id.about_page: {
                Log.d("hehe", "呵呵");
                Intent intent = new Intent(Setting.this, AboutActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.sign_out:{
                Dialog();

            }
            break;

        }
    }
    public void Dialog() {
        //实现处理
        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(this);
        effect = Effectstype.Flipv;
        dialogBuilder
                .withTitle("退出登录")                                  //.withTitle(null)  no title
                .withTitleColor("#FF0000")                                  //def
                .withDividerColor("#11000000")                              //def
                .withMessage("退出登录将无法继续使用相关服务,确定要继续吗?")                     //.withMessage(null)  no Msg
                .withMessageColor("#000000")                              //def  | withMessageColor(int resid)
                .withDialogColor("#FFFFFF")                               //def  | withDialogColor(int resid)                               //def
                .withIcon(getResources().getDrawable(R.drawable.ic_sign_out_button))
                .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
                .withDuration(300)                                          //def
                .withEffect(effect)                                         //def Effectstype.Slidetop
                .withButton1Text("确认")                                      //def gone
                .withButton2Text("取消")                                  //def gone
//                .setCustomView(R.layout.custom_view,v.getContext())         //.setCustomView(View or ResId,context)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                        //保存登录状态
                        SharedPrefUtil.setParam(Setting.this, SharedPrefUtil.IS_LOGIN, false);
                        Intent intent=new Intent(Setting.this, Login.class);
                        startActivity(intent);
                        Setting.this.finish();
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

    @Override
    protected void onDestroy() {
//        App.getInstance().destory();
        super.onDestroy();
    }
}

