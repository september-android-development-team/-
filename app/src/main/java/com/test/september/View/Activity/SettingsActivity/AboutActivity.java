package com.test.september.View.Activity.SettingsActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.test.september.App;
import com.test.september.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.License_Information)
    TextView LicenseInformation;
    @BindView(R.id.Copyright_information)
    TextView CopyrightInformation;
    @BindView(R.id.Software_License_Agreement)
    TextView SoftwareLicenseAgreement;
    @BindView(R.id.September_Platform_Service_Agreement)
    TextView SeptemberPlatformServiceAgreement;
    @BindView(R.id.problem_back_page)
    TextView problemBackPage;
    @BindView(R.id.about_page)
    TextView aboutPage;
    @BindView(R.id.newVersion)
    TextView newVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getInstance().addActivity(this);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        CopyrightInformation.setOnClickListener(this);
        SoftwareLicenseAgreement.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Software_License_Agreement:{
                Intent intent = new Intent();
                String licenses_url="file:///android_asset/software_licenses.html";
                intent.putExtra("url", licenses_url);
                intent.putExtra("title","软件许可使用协议");
                intent.setClass(AboutActivity.this, WebViewPage.class);
                startActivity(intent);
            }break;
            case R.id.Copyright_information:{
                Intent intent = new Intent();
                String licenses_url="file:///android_asset/licenses.html";
                intent.putExtra("url", licenses_url);
                intent.putExtra("title","版权信息");
                intent.setClass(AboutActivity.this, WebViewPage.class);
                startActivity(intent);
            }break;
        }
    }
}
