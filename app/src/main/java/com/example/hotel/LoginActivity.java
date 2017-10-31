package com.example.hotel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotel.Utils.MyAsyncTask;
import com.example.hotel.data.Constant;

public class LoginActivity extends AppCompatActivity {

    private TextView registerText;
    private EditText etAccount;
    private EditText etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
        String loginUrl = Constant.URL_Login + "?account=" + account + "&password=" + password;
        new MyAsyncTask(this).execute(loginUrl);
    }
}
