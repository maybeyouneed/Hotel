package com.example.hotel.activities;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hotel.interfaces.HttpCallBackListener;
import com.example.hotel.R;
import com.example.hotel.utils.HttpTask;
import com.example.hotel.data.CommonResponse;
import com.example.hotel.data.Constant;

public class RegisterActivity extends AppCompatActivity {

    private EditText etAccount;
    private EditText etPassword;
    private Button btnRegister;

    HttpCallBackListener listener = new HttpCallBackListener() {

        @Override
        public void onSuccess(CommonResponse response) {
            finish();
        }

        @Override
        public void onFailed() {
            Toast.makeText(RegisterActivity.this, "注册失败，请稍后重试！", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError() {
            Toast.makeText(RegisterActivity.this, "注册失败，网络连接错误，请确认网络连接后重试", Toast.LENGTH_SHORT).show();
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etAccount = (EditText) findViewById(R.id.et_account);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnRegister = (Button) findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(etAccount.getText().toString().trim()) &&
                        !TextUtils.isEmpty(etPassword.getText().toString().trim())){
                    Log.d("RegisterActivity", "账号：" + etAccount.getText().toString().trim() + "密码：" + etPassword.getText().toString().trim());
                    register(etAccount.getText().toString().trim(), etPassword.getText().toString().trim());
                }else {
                    Toast.makeText(RegisterActivity.this, "账号密码都不能为空！",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void register(String account, String password){
        String registerUrl = Constant.URL_Register;
        new HttpTask(this, listener).execute(registerUrl, account , password);
    }

}
