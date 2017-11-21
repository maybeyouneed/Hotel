package com.example.hotel.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotel.Interfaces.HttpCallBackListener;
import com.example.hotel.R;
import com.example.hotel.Utils.HttpAsyncTask;
import com.example.hotel.data.Constant;

public class LoginActivity extends AppCompatActivity {

    private TextView registerText;
    private EditText etAccount;
    private EditText etPassword;
    private Button btnLogin;

    private HttpCallBackListener loginListener = new HttpCallBackListener() {
        @Override
        public void onSuccess() {
            SharedPreferences pref = LoginActivity.this.getSharedPreferences("Login", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("isLogin", true);
            editor.putString("account", etAccount.getText().toString().trim());
            editor.apply();
            LoginActivity.this.finish();
        }

        @Override
        public void onFailed() {
            Toast.makeText(LoginActivity.this, "登录失败，账号或密码错误！", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError() {
            Toast.makeText(LoginActivity.this, "登录失败，网络连接错误，请确认网络连接后重试", Toast.LENGTH_SHORT).show();
            /*
            SharedPreferences pref = LoginActivity.this.getSharedPreferences("Login", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("isLogin", true);
            editor.putString("account", etAccount.getText().toString().trim());
            editor.apply();
            */
            //Intent intent2 = getIntent();
            //Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            //intent.putExtra("index", intent2.getIntExtra("index", 0));
            //startActivity(intent);

            LoginActivity.this.finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.d("LoginActivity", "onCreate()");

        registerText = (TextView) findViewById(R.id.tv_register);
        etAccount = (EditText) findViewById(R.id.et_login_account);
        etPassword = (EditText) findViewById(R.id.et_login_password);
        btnLogin = (Button) findViewById(R.id.btn_login);

        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(etAccount.getText().toString().trim()) &&
                        !TextUtils.isEmpty(etPassword.getText().toString().trim()) ){
                    Log.d("LoginActivity", "账号：" + etAccount.getText().toString().trim() + "密码：" + etPassword.getText().toString().trim());
                    login(etAccount.getText().toString().trim(), etPassword.getText().toString().trim());
                }else {
                    Toast.makeText(LoginActivity.this, "帐号密码不能为空！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void login(String account, String password){
        String loginUrl = Constant.URL_Login;
        new HttpAsyncTask(this, loginListener).execute(loginUrl, account, password);
    }

}
