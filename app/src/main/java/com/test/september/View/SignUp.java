package com.test.september.View;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.test.september.R;

import butterknife.ButterKnife;


public class SignUp extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    private EditText _nameText;
    private EditText _phoneText;
    private EditText _passwordText;
    private Button _signupButton;
    private TextView _loginLink;


    private Button timeButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        _nameText=findViewById(R.id.input_name);
        _phoneText=findViewById(R.id.input_phone_signUp);
        _passwordText=findViewById(R.id.input_password_signUp);
        _signupButton=findViewById(R.id.btn_signup);
        _loginLink=findViewById(R.id.link_login);

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
                    return;
                }
                myCountDownTimer.start();
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


        String name = _nameText.getText().toString();
        String phone = _phoneText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
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
}
