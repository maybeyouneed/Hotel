package com.example.hotel;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hotel.Utils.MyAsyncTask;
import com.example.hotel.data.Constant;

public class RegisterActivity extends AppCompatActivity {

    private EditText etAccount;
    private EditText etPassword;
    private Button btnRegister;

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
                    register(etAccount.getText().toString().trim(),etPassword.getText().toString().trim());
                }else {
                    Toast.makeText(RegisterActivity.this, "账号密码都不能为空！",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void register(String account, String password){
        String registerUrl = Constant.URL_Register + "?account=" + account + "&password=" + password;
        new MyAsyncTask(this).execute(registerUrl);
    }

}
