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
import android.widget.TextView;

import com.example.hotel.Activities.LoginActivity;
import com.example.hotel.R;


public class OrderFragment extends Fragment {

    private Button login;
    private TextView hintText;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /*   finish()掉MainActivity并重新启动的代码
        SharedPreferences pref = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        Boolean isLogin = pref.getBoolean("isLogin", false);
        if (!isLogin){
            view = inflater.inflate(R.layout.fragment_order, container, false);
            login = (Button) view.findViewById(R.id.jump_login);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.putExtra("index", 1);
                    startActivity(intent);
                    getActivity().finish();
                }
            });
            return view;
        }else {
            return inflater.inflate(R.layout.fragment_order2, container, false);
        }
        */


        //不重新加载布局2的代码
        view = inflater.inflate(R.layout.fragment_order, container, false);
        login = (Button) view.findViewById(R.id.jump_login);
        return view;
    }

    // 登录成功之后不重新加载布局2的代码
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                //getActivity().finish();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences pref = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        Boolean isLogin = pref.getBoolean("isLogin", false);
        Log.d("Order", "isLogin = " + isLogin);
        if (isLogin) {
            hintText = (TextView) view.findViewById(R.id.login_hint);
            hintText.setText("您暂时还没有订单哦");
            login.setVisibility(View.GONE);
        }
    }

}
