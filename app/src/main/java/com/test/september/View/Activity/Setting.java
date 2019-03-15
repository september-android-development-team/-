package com.test.september.View.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.test.september.Adapter.ItemAdapter;
import com.test.september.R;
import com.test.september.View.MyView.SwitchButton;
import com.test.september.widget.MessageA;
import com.test.september.widget.MessageB;

import java.util.ArrayList;
import java.util.List;

public class Setting extends AppCompatActivity implements View.OnClickListener{

    private SwitchButton switch_accept_news, switch_sound, switch_shock, switch_loundspeaker;
    private LinearLayout ll_sound, ll_shock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //接收消息通知开关
        switch_accept_news = (SwitchButton) findViewById(R.id.switch_accept_news);
        //声音开关
        switch_sound = (SwitchButton) findViewById(R.id.switch_sound);
        //震动开关
        switch_shock = (SwitchButton) findViewById(R.id.switch_shock);
        //使用扬声器播放语音
        switch_loundspeaker = (SwitchButton) findViewById(R.id.switch_loundspeaker);

        //声音item布局
        ll_sound = (LinearLayout) findViewById(R.id.ll_sound);
        //震动item布局
        ll_shock = (LinearLayout) findViewById(R.id.ll_shock);

        //开关布局
        switch_accept_news.setOnClickListener(this);
        switch_sound.setOnClickListener(this);
        switch_shock.setOnClickListener(this);
        switch_loundspeaker.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //声音开关
            case R.id.switch_accept_news:

                if (switch_accept_news.isSwitchOpen()) {//开关为打开状态
                    //关闭逻辑
                    Toast.makeText(getApplicationContext(),"关闭了",Toast.LENGTH_SHORT).show();
                    switch_accept_news.closeSwitch();
                    ll_sound.setVisibility(View.GONE);
                    ll_shock.setVisibility(View.GONE);
                } else {
                    //打开逻辑
                    Toast.makeText(getApplicationContext(),"打开了",Toast.LENGTH_SHORT).show();
                    switch_accept_news.openSwitch();
                    ll_sound.setVisibility(View.VISIBLE);
                    ll_shock.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.switch_sound:
                if (switch_sound.isSwitchOpen()) {
                    switch_sound.closeSwitch();
                } else {
                    switch_sound.openSwitch();
                }
                break;
            case R.id.switch_shock:
                if (switch_shock.isSwitchOpen()) {
                    switch_shock.closeSwitch();
                } else {
                    switch_shock.openSwitch();
                }
                break;
            case R.id.switch_loundspeaker:
                if (switch_loundspeaker.isSwitchOpen()) {
                    switch_loundspeaker.closeSwitch();
                } else {
                    switch_loundspeaker.openSwitch();
                }
                break;
        }
    }
}

