package com.example.hotel.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotel.R;
import com.example.hotel.activities.LoginActivity;
import com.example.hotel.data.CommonResponse;
import com.example.hotel.data.Constant;
import com.example.hotel.gson.OrderInfo;
import com.example.hotel.utils.Utility;
import com.google.zxing.activity.CaptureActivity;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;


public class MineFragment extends Fragment implements View.OnClickListener{

    private Button btn_login;
    private TextView tv_account;
    private ImageButton img_setting;
    private TextView QRCode;
    private TextView getCode;
    private RelativeLayout exitLogin;
    private String key;
    private String roomCode;   //当前进行中的房间代码

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
        getCode = (TextView) view.findViewById(R.id.get);
        exitLogin = (RelativeLayout) view.findViewById(R.id.exit);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btn_login.setOnClickListener(this);
        QRCode.setOnClickListener(this);
        exitLogin.setOnClickListener(this);
        getCode.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences pref = getActivity().getSharedPreferences("Login", MODE_PRIVATE);
        Boolean isLogin = pref.getBoolean("isLogin", false);
        String account = pref.getString("account", null);
        key = pref.getString("key", null);
        Log.d("Mine", "isLogin = " + isLogin);
        Log.d("Mine", "key = " + key);
        if (isLogin){
            if (account != null){
                btn_login.setVisibility(View.GONE);
                tv_account.setVisibility(View.VISIBLE);
                tv_account.setText(account);
            }else {
                btn_login.setVisibility(View.GONE);
                tv_account.setText("账号ID获取失败");
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            Log.d("我的", "我的隐藏");
        } else {
            Log.d("我的", "我的显示");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.scan:
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.exit:
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this.getActivity());
                dialogBuilder.setMessage("要退出登录吗？");
                dialogBuilder.setCancelable(true);
                dialogBuilder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences pref = getActivity().getSharedPreferences("Login", MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putBoolean("isLogin", false);
                        editor.putString("account", null);
                        editor.putString("key", null);
                        editor.apply();
                        btn_login.setVisibility(View.VISIBLE);
                        tv_account.setVisibility(View.GONE);

                    }
                });
                dialogBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialogBuilder.show();
                break;
            case R.id.get:
                if (!TextUtils.isEmpty(key)) {
                    SharedPreferences preferences = getActivity().getSharedPreferences("OrderInfo", 0);
                    OrderInfo orderInfo = Utility.handleOrdersResponse(preferences.getString("orderJSON", null));
                    if (orderInfo != null && orderInfo.isResult() && !orderInfo.getMessage().isEmpty()){
                        List<OrderInfo.MessageBean> orders = orderInfo.getMessage();
                        for (OrderInfo.MessageBean order: orders) {
                            if (order.getState() == 1){
                                roomCode = order.getRoom();
                                Log.d("点击获取密码时服务器给的房间号2", order.getRoom());
                            }
                        }
                    }else {
                        Toast.makeText(getActivity(), "目前没有正在进行的订单", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    if (!TextUtils.isEmpty(roomCode)) {
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder()
                                .url(Constant.URL + "lock?room=" + roomCode)
                                .header("key", key)
                                .build();
                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getActivity(), "获取密码失败，请确认网络连接", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String res = response.body().string();
                                CommonResponse commonResponse = Utility.handleLoginResponse(res);
                                Log.d("获取密码", res);
                                if (commonResponse.getResult()) {
                                    final String pwd = commonResponse.getMessage();
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                                            dialog.setMessage(pwd);
                                            dialog.setCancelable(true);
                                            dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            });
                                            dialog.show();
                                        }
                                    });
                                } else {
                                    final String message = commonResponse.getMessage();
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        });
                    }else {
                        Toast.makeText(getActivity(), "暂无订单信息", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {     //RESULT_OK = -1
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("qr_scan_result");        //房间代码
            Log.d("扫描结果", scanResult);
            if (!TextUtils.isEmpty(key)) {
                SharedPreferences preferences = getActivity().getSharedPreferences("OrderInfo", 0);
                OrderInfo orderInfo = Utility.handleOrdersResponse(preferences.getString("orderJSON", null));
                if (orderInfo != null && orderInfo.isResult() && !orderInfo.getMessage().isEmpty()){
                    List<OrderInfo.MessageBean> orders = orderInfo.getMessage();
                    for (OrderInfo.MessageBean order: orders) {
                        Log.d("扫码获得的房间号", scanResult);
                        Log.d("扫码时解析服务器数据出来的房间号", order.getRoom());
                        if (scanResult.equals(order.getRoom()) && order.getState() == 1){
                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder()
                                    .url(Constant.URL + "lock?room=" + scanResult)
                                    .header("key", key)
                                    .build();
                            client.newCall(request).enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getActivity(), "获取密码失败，请确认网络连接", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    String res = response.body().string();
                                    CommonResponse commonResponse = Utility.handleLoginResponse(res);
                                    Log.d("获取密码", res);
                                    if (commonResponse.getResult()) {
                                        final String pwd = commonResponse.getMessage();
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                                                dialog.setMessage(pwd);
                                                dialog.setCancelable(true);
                                                dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                    }
                                                });
                                                dialog.show();
                                            }
                                        });
                                    } else {
                                        final String message = commonResponse.getMessage();
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    }
                }else {
                    Toast.makeText(getActivity(), "你没有预订这个房间哦!", Toast.LENGTH_SHORT).show();
                }
            }
        }else {
            Toast.makeText(getActivity(), "扫描失败，请重试", Toast.LENGTH_SHORT).show();
        }
    }
}
