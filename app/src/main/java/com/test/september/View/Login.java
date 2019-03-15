package com.test.september.View;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
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


public class Login extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private EditText _phoneText;
    private EditText _passwordText;
    private Button _loginButton;
    private TextView _signupLink;
    private String url="www.domain/user/login";
    private String responseData;

    private int code;
    private String S_Username;
    private String S_Password;
    private String uuid;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        _phoneText=findViewById(R.id.input_phone_login);
         _passwordText=findViewById (R.id.input_password);
        _loginButton=findViewById(R.id.btn_login);
        _signupLink=findViewById(R.id.link_signup);
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");
        if (!validate()) {
            onLoginFailed();
        }else{
            String email = _phoneText.getText().toString();
            String password = _passwordText.getText().toString();
            sendRequestWithOkHttp(email,password);
            _loginButton.setEnabled(false);
            final ProgressDialog progressDialog = new ProgressDialog(Login.this,
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("登录中...");
            progressDialog.show();
            // TODO: Implement your own authentication logic here.
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            // On complete call either onLoginSuccess or onLoginFailed
                                if(code == 200) {
                                    onLoginSuccess();
                                    progressDialog.dismiss();
                                }
                                if(code == 400){
                                    _phoneText.setText("");
                                    _passwordText.setText("");
                                    progressDialog.dismiss();
                                    onLoginFailed();
                                }else{
                                    _loginButton.setEnabled(true);
                                }
                        }
                    }, 3000);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }


    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        /****
         * 插入相应跳转界面
         * **/
        Intent intent=new Intent(this,Home.class);
        startActivity(intent);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "登录失败!", Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
    }


    public boolean validate() {
        boolean valid = true;

        String phone = _phoneText.getText().toString();
        String password = _passwordText.getText().toString();

        if (phone.isEmpty()) {
//            || !Patterns.PHONE.matcher(phone).matches()
            _phoneText.setError("请输入正确的手机号!");
            valid = false;
        } else {
            _phoneText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 40) {
            _passwordText.setError("请输入长度为4到20的密码!");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    /************************************************向服务器发送登录请求**************************************************************/


    public void sendRequestWithOkHttp(final String Username,final String Password ) {
//        final int Time,final String Token,
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();   //定义一个OKHttpClient实例
                    RequestBody requestBody = new FormBody.Builder()
                            .add("username", Username)
                            .add("password",Password)
                            .build();
                    Log.d("username", Username);
                    Log.d("Password", Password);
                    //实例化一个Respon对象，用于发送HTTP请求
                    Request request = new Request.Builder()
                            .url("https://api.cnsepte.com:448/user/login")             //设置目标网址
//                    http://www.domain/user/login
//                    https://api.cnsepte.com:88/user/login
                            .post(requestBody)
                            .build();
                    Response response = okHttpClient.newCall(request).execute();  //获取服务器返回的数据
                    if (response.body() != null) {
                        responseData = response.body().string();//存储服务器返回的数据
                        Log.d("data", responseData);
                        parseJSONWithGSON(responseData);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    private void parseJSONWithGSON(String json) {
        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken<Data>() {}.getType();
        Data data = gson.fromJson(json, type);
//        value = (float) data.getResultObj().getValue();
        uuid=data.getData().getUuid();
        code=data.getCode();
        String S_code=String.valueOf(code);
        if(code==400)
        {

        }else {
            Log.d("code", S_code);
            Log.d("uuid", uuid);
        }
    }


    public static class Data {

        private int code;
        private String msg;
        private Data.data data;
        public static class data{
            private int uid;
            private String username;
            private String phone;
            private String email;
            private String rtime;
            private String uuid;
            private String sessionid;

            public int getUid() {
                return uid;
            }
            public void setUid(int uid) {
                this.uid = uid;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getRtime() {
                return rtime;
            }

            public void setRtime(String rtime) {
                this.rtime = rtime;
            }

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }

            public String getSessionid() {
                return sessionid;
            }

            public void setSessionid(String sessionid) {
                this.sessionid = sessionid;
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

        public Data.data getData() {
            return data;
        }

        public void setData(Data.data data) {
            this.data = data;
        }


    }



}
