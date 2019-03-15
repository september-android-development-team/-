package com.test.september.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.test.september.R;
import com.test.september.util.HttpUtil;
import com.test.september.util.MD5Util;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class SignUp extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    private EditText _nameText;
    private EditText _phoneText;
    private EditText _passwordText;
    private Button _signupButton;
    private TextView _loginLink;
    private EditText _input_Vcard;


    private String getCode_url="https://api.cnsepte.com:448/code";
//            "http://www.domain/code";
    private String register_url="http://www.domain/user/register";
    private String token;
    private int code;       //状态码
    private String msg;     //消息
    private String responseData;     //获取验证码返回的json数据
    private String register_parseJSONWithGSON;    //获取注册返回json数据
    private String name ;            //用户昵称
    private String username;         //用户手机，邮箱
    private String password ;        //密码
    private String time;
    private String data="";
    private String is_Exist="0";
    private Button timeButton;
    private String VCode;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        _nameText=findViewById(R.id.input_name);
        _phoneText=findViewById(R.id.input_phone_signUp);
        _passwordText=findViewById(R.id.input_password_signUp);
        _signupButton=findViewById(R.id.btn_signup);
        _loginLink=findViewById(R.id.link_login);
        _input_Vcard=findViewById(R.id.input_Vcard);

        final MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000,1000);

        timeButton = (Button)findViewById(R.id.get_Vcard);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });


        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validate()) {
                    onSignupFailed();
                }else {
                    sendRequestWithOkHttp();
                    Toast.makeText(SignUp.this,"已经发送验证码!请注意接收!",Toast.LENGTH_LONG).show();
                    myCountDownTimer.start();
                }
            }
        });
    }

    /**
     * 设置验证码倒计时
     * ***/

    //倒计时函数
    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //计时过程
        @Override
        public void onTick(long l) {
            //防止计时过程中重复点击
            timeButton.setClickable(false);
            timeButton.setText(l/1000+"秒");
            timeButton.setBackground(getResources().getDrawable(R.drawable.gray_btn_color));
        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            //重新给Button设置文字
            timeButton.setText("重新获取");
            //设置可点击
            timeButton.setClickable(true);
            timeButton.setBackground(getResources().getDrawable(R.drawable.green_btn_dra));
        }
    }

    /***
     * 清除倒计时
     * ***/

//    private void clearTimer() {
//        if (task != null) {
//            task.cancel();
//            task = null;
//        }
//        if (timer != null) {
//            timer.cancel();
//            timer = null;
//        }
//    }
    /*********
     * 注册
     * *******/

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }else {
//            password = _passwordText.getText().toString();
//            name = _nameText.getText().toString();
            VCode=_input_Vcard.getText().toString();
            sendRequestWithOkHttp(username,password,VCode);
        }

        /****
         *
         * 提示弹窗
         * **/
        _signupButton.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(SignUp.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("正在注册中...");
        progressDialog.show();

//        String name = _nameText.getText().toString();
//        String phone = _phoneText.getText().toString();
//        String password = _passwordText.getText().toString();

        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        if (code==200) {
                            onSignupSuccess();
                            progressDialog.dismiss();
                        }
                        if(code==400){
                            onSignupFailed();
                            progressDialog.dismiss();
                            _signupButton.setEnabled(true);
                        }else{
                            _signupButton.setEnabled(true);
                        }
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        /****
         * 插入相应跳转界面
         * **/
        Intent intent=new Intent(SignUp.this,Home.class);
        startActivity(intent);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "请检查您的信息是否填写正确!", Toast.LENGTH_LONG).show();
        _signupButton.setEnabled(true);
    }
    public boolean validate() {
        boolean valid = true;
        String name = _nameText.getText().toString();
        String email = _phoneText.getText().toString();
        String password = _passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("请输入长度大于三的用户名!");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || !Patterns.PHONE.matcher(email).matches()) {
            _phoneText.setError("输入一个正确的手机号!");
            valid = false;
        } else {
            _phoneText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("请输入长度为4到10的密码!");
            valid = false;
        } else {
            _passwordText.setError(null);
        }
        return valid;
    }

    public String getTime(){

        long time=System.currentTimeMillis()/1000;//获取系统时间的10位的时间戳

        String  str=String.valueOf(time);

        return str;
    }

/*****
 * 请求验证码
 * 方式：Get
 * *****/
    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                name = _nameText.getText().toString();
                username = _phoneText.getText().toString();
                password =MD5Util.md5(_passwordText.getText().toString());
                time=getTime();
                int times=Integer.parseInt(time);
                token= MD5Util.md5(MD5Util.md5(time)+MD5Util.md5(username)+MD5Util.md5(is_Exist));
                Log.d("Token",token);
                Log.d("time",time);
                Log.d("name",name);
                String GET=getCode_url+"/"+time+"/"+token+"/"+username+"/"+"0";
                //在子线程中执行Http请求，并将最终的请求结果回调到okhttp3.Callback中
                HttpUtil.sendOkHttpRequest(GET, new okhttp3.Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        //在这里进行异常情况处理
                    }
                    @Override
                    public void onResponse(Call call, @NonNull Response response) throws IOException {
                        //得到服务器返回的具体内容
                        String responseData = null;
                        if (response.body() != null) {
                            responseData = response.body().string();
                        }
                        Log.d("sign_code_data",responseData);
                        parseJSONWithGSON(responseData);
                        //显示UI界面，调用的showResponse方法
                        // showResponse(responseData);
                    }
                });
            }
        }).start();
    }


    public void sendRequestWithOkHttp(final String Username,final String password,final String code) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();   //定义一个OKHttpClient实例
                    RequestBody requestBody = new FormBody.Builder()
                            .add("username",Username)
                            .add("password", password)
                            .add("code", String.valueOf(code))
                            .build();
                    Log.d("Username", Username);
                    Log.d("Password", password);
                    //实例化一个Respon对象，用于发送HTTP请求
                    Request request = new Request.Builder()
                            .url("https://api.cnsepte.com:448/user/register")             //设置目标网址
                            .post(requestBody)
                            .build();
                    Response response = okHttpClient.newCall(request).execute();  //获取服务器返回的数据
                    if (response.body() != null) {
                        responseData = response.body().string();//存储服务器返回的数据
                        Log.d("register_data", responseData);
                        register_parseJSONWithGSON(responseData);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /******
     * json数据解析
     * 方法：Gson
     * 针对：注册
     * *******/
    private void register_parseJSONWithGSON(String json) {
        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken<App>() {}.getType();
        App app = gson.fromJson(json, type);
        code = app.getCode();           // 获取登录状态
        String c=String.valueOf(code);  //转换
        msg = app.getMsg();             //获取消息
        Log.d("code",c);
        Log.d("msg", msg);
//        if(code==400) {
//            data=app.getData();             //获取data
//            Log.d("data", data);
//        }
//        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

/******
 * json数据解析
 * 方法：Gson
 * 针对：短信码
 * *******/

    private void parseJSONWithGSON(String json) {
        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken<App>() {}.getType();
        App app = gson.fromJson(json, type);
        code = app.getCode();           // 获取登录状态
        String c=String.valueOf(code);  //转换
        msg = app.getMsg();             //获取消息
        Log.d("code",c);
        Log.d("msg", msg);
//        if(code==400) {
//            data=app.getData();             //获取data
//            Log.d("data", data);
//        }
//        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    public static class App {
        private int code;
        private String msg;
        private  App.data data;

        public static class data{
            private String test;

            public String getTest() {
                return test;
            }

            public void setTest(String test) {
                this.test = test;
            }
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
        public App.data getData() {
            return data;
        }

        public void setData(App.data data) {
            this.data = data;
        }

    }



}
