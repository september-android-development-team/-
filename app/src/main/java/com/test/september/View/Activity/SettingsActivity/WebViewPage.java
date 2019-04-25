package com.test.september.View.Activity.SettingsActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.test.september.R;
import com.test.september.View.MyView.Dialogview.NiftyDialogBuilder;
import com.test.september.util.effects.control.Effectstype;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewPage extends AppCompatActivity {
    @BindView(R.id.web_title)
    TextView webTitle;
    private Effectstype effect;
    private String TAG = "GET_STOP";
    private WebView wView;
    private String out_url;
    private ProgressBar progressBar;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);

        wView = findViewById(R.id.webView);
        progressBar = findViewById(R.id.public_webview_progressbar);

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        String title = intent.getStringExtra("title");
        webTitle.setText(title);
        /* 设置为使用webview推荐的窗口 */
        wView.getSettings().setUseWideViewPort(true);
        /* 设置网页自适应屏幕大小 */
        wView.getSettings().setLoadWithOverviewMode(true);
//        wView.setProgressbarDrawable(getResources().getDrawable(R.drawable.progressbar_business_area_red));

        WebSettings wSet = wView.getSettings();
        wSet.setJavaScriptEnabled(true);

        //wView.loadUrl("file:///android_asset/index.html");
        //wView.loadUrl("content://com.android.htmlfileprovider/sdcard/index.html");
//        wView.loadUrl("http://wap.baidu.com");
        wView.loadUrl(url);
        wView.setWebViewClient(new MyWebViewClient());
        wView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                }
                super.onProgressChanged(view, newProgress);
            }


        });


    }

    //                Intent intent = new Intent();
//                intent.setAction("android.intent.action.VIEW");
//
//                //获取html文件复制到sdcard后的路径,test.html是我放到项目中assets目录下的网页文件
//                String path = new AssertToSdcard().transFilePath(
//                        this, "secert.html");
//                if (null != path) {
//                    // Uri content_url = Uri.parse("file:///storage/sdcard/test.html");
//                    //使用此种方法获取uri或者使用上面注释的方法获取uri
//                    Uri content_url = Uri.fromFile(new File(path));
//                    intent.setData(content_url);
//                    //intent.setClassName("com.android.browser","com.android.browser.BrowserActivity");
//                    intent.setComponent(new ComponentName("com.android.browser","com.android.browser.BrowserActivity"));
//                    startActivity(intent);
//                } else {
//                    Log.e("--->", "no path");
//                }
//
// 监听所有点击的链接，如果拦截到我们需要的，就跳转到相对应的页面。
    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d(TAG, "拦截到网页的点击事件url = " + url);


            if (url != null && !url.contains("baidu.com")) {

                out_url = url;
                Dialog();
                return true;
            }

            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageFinished(view, url);
        }
    }

    public void Dialog() {
        //实现处理
        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(this);
        effect = Effectstype.Flipv;
        dialogBuilder
                .withTitle("安全警告")                                  //.withTitle(null)  no title
                .withTitleColor("#FF0000")                                  //def
                .withDividerColor("#11000000")                              //def
                .withMessage("将要访问安全性未知的链接,可能存在风险" + ":" + out_url)                     //.withMessage(null)  no Msg
                .withMessageColor("#000000")                              //def  | withMessageColor(int resid)
                .withDialogColor("#FFFFFF")                               //def  | withDialogColor(int resid)                               //def
                .withIcon(getResources().getDrawable(R.drawable.ic_warning))
                .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
                .withDuration(300)                                          //def
                .withEffect(effect)                                         //def Effectstype.Slidetop
                .withButton1Text("打开链接")                                      //def gone
                .withButton2Text("取消")                                  //def gone
//                .setCustomView(R.layout.custom_view,v.getContext())         //.setCustomView(View or ResId,context)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                        wView.loadUrl(out_url);
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
        super.onDestroy();
        this.finish();
    }
}

