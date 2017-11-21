package com.example.hotel.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotel.Activities.LoginActivity;
import com.example.hotel.Activities.SettingActivity;
import com.example.hotel.R;
import com.google.zxing.activity.CaptureActivity;


public class MineFragment extends Fragment {

    Button btn_login;
    TextView tv_account;
    ImageButton img_setting;
    TextView QRCode;

    //打开扫描界面请求码
    private int REQUEST_CODE = 0x01;
    //扫描成功返回码
    private int RESULT_OK = 0xA1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        btn_login = (Button) view.findViewById(R.id.login);
        tv_account = (TextView) view.findViewById(R.id.tv_account);
        img_setting = (ImageButton) view.findViewById(R.id.img_setting);
        QRCode = (TextView) view.findViewById(R.id.scan);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
        img_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SettingActivity.class));
                getActivity().finish();
            }
        });
        QRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {     //RESULT_OK = -1
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("qr_scan_result");
            Toast.makeText(getActivity(), scanResult, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences pref = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        Boolean isLogin = pref.getBoolean("isLogin", false);
        String account = pref.getString("account", null);
        Log.d("Mine", "isLogin = " + isLogin);
        if (isLogin){
            if (account != null){
                btn_login.setVisibility(View.GONE);
                tv_account.setText(account);
            }else {
                btn_login.setVisibility(View.GONE);
                tv_account.setText("账号ID获取失败");
            }
        }
    }
}
