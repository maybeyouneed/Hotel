package com.example.hotel.fragments;

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
import android.widget.TextView;

import com.example.hotel.activities.LoginActivity;
import com.example.hotel.R;



public class MessageFragment extends Fragment {

    private Button login;
    private TextView hintText;
    private View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_message, container, false);
        login = (Button) view.findViewById(R.id.message_jump_login);
        hintText = (TextView) view.findViewById(R.id.message_login_hint);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences pref = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        Boolean isLogin = pref.getBoolean("isLogin", false);
        Log.d("Message", "isLogin = " + isLogin);
        if (isLogin) {
            hintText.setText("您还没有新消息哦");
            login.setVisibility(View.GONE);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            Log.d("消息", "消息隐藏");
        } else {
            Log.d("消息", "消息显示");
            SharedPreferences pref = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
            Boolean isLogin = pref.getBoolean("isLogin", false);
            Log.d("消息界面的onHiddenChanged()", "isLogin = " + isLogin);
            if (!isLogin) {
                hintText.setText("登录后才能查看您的消息哦");
                login.setVisibility(View.VISIBLE);
            }
        }
    }
}
