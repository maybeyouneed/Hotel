package com.example.hotel.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hotel.R;
import com.example.hotel.data.CommonResponse;
import com.example.hotel.data.Constant;
import com.example.hotel.gson.MessageBean;
import com.example.hotel.gson.RoomInfo;
import com.example.hotel.utils.Utility;
import com.example.hotel.wheelview.WheelView;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ConfirmActivity extends AppCompatActivity implements View.OnClickListener{

    private Calendar calendar;
    private TextView tvCheckIn;
    private TextView tvCheckOut;
    private TextView tvCheckTime;
    private TextView tvTotalPrice;   //订单总额
    private TextView etName;   //真实姓名
    private String time = null;
    private String roomNumber;   //房间号
    private String roomCode; //房间代码
    private static final String[] TIMES = new String[]{"14:00 ~ 15:00", "15:00 ~ 16:00",
            "16:00 ~ 17:00", "17:00 ~ 18:00", "18:00 ~ 19:00", "20:00 ~ 21:00", "21:00 ~ 22:00", "22:00 ~ 23:00", "23:00 ~ 24:00"};
    private int checkInDay;   //入住日
    private int checkOutDay;  //退房日
    private String unitPrice;  //每晚价格
    private String totalPrice;  //总共金额


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        etName = (EditText) findViewById(R.id.et_name);
        Intent intent = getIntent();
        String hotelName = intent.getStringExtra("hotelName");
        String imgUrl = intent.getStringExtra("img");
        roomNumber = intent.getStringExtra("hotelNumber");
        unitPrice = intent.getStringExtra("price");

        ImageView roomImg = (ImageView) findViewById(R.id.img_room);
        TextView tvRoomName = (TextView) findViewById(R.id.tv_room_name1);
        TextView tvRoomInfo = (TextView) findViewById(R.id.tv_room_info);
        TextView tvUnitPrice = (TextView) findViewById(R.id.unit_price);
        Glide.with(this).load(imgUrl).into(roomImg);
        tvRoomName.setText(hotelName);
        tvRoomInfo.setText("1卧室1卫|2人");
        tvUnitPrice.setText(String.format(getResources().getString(R.string.price), unitPrice));

        tvCheckIn = (TextView) findViewById(R.id.tv_check_in);
        tvCheckOut = (TextView) findViewById(R.id.tv_check_out);
        tvCheckTime = (TextView) findViewById(R.id.tv_check_in_time);

        LinearLayout checkInDate = (LinearLayout) findViewById(R.id.check_in_date);
        LinearLayout checkOutDate = (LinearLayout) findViewById(R.id.check_out_date);
        RelativeLayout checkInTime = (RelativeLayout) findViewById(R.id.check_in_time);
        checkInDate.setOnClickListener(this);
        checkOutDate.setOnClickListener(this);
        checkInTime.setOnClickListener(this);
        calendar = Calendar.getInstance();

        Button pay = (Button) findViewById(R.id.book_room);
        pay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.check_in_date:
                showDatePickerDialog(R.id.check_in_date);
                break;
            case R.id.check_out_date:
                showDatePickerDialog(R.id.check_out_date);
                break;
            case R.id.check_in_time:
                View view = LayoutInflater.from(this).inflate(R.layout.wheel_view, null);
                WheelView wv = (WheelView) view.findViewById(R.id.wheel_view_wv);
                wv.setOffset(2);     //显示的条目数，1、2、3代表3、5、7行
                wv.setItems(Arrays.asList(TIMES));    //加载的选项数据
                wv.setSeletion(3);    //选定框在第三条
                wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                    @Override
                    public void onSelected(int selectedIndex, String item) {
                        Log.d("时间选择", "selectedIndex: " + selectedIndex + ", item: " + item);
                        time = item;
                    }
                });

                new AlertDialog.Builder(this)
                        .setTitle("预计到店时间")
                        .setView(view)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tvCheckTime.setText(time);
                            }
                        })
                        .show();
                break;
            case R.id.book_room:
                submitOrder();
                break;
        }
    }


    private void showDatePickerDialog(final int id) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //monthOfYear 得到的月份会减1
                String time = String.valueOf(year) + "-" + String.valueOf(monthOfYear + 1) + "-" + Integer.toString(dayOfMonth);
                if (id == R.id.check_in_date){
                    Log.d("入住日期选择", time);
                    tvCheckIn.setText(time);
                    checkInDay = dayOfMonth;
                    showPrice();
                }if (id == R.id.check_out_date){
                    Log.d("退房日期选择", time);
                    tvCheckOut.setText(time);
                    checkOutDay = dayOfMonth;
                    showPrice();
                }
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
        //自动弹出键盘问题
        datePickerDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    //显示订单总额
    private void showPrice(){
        if ( !"选择日期".equals(tvCheckIn.getText().toString()) && !"选择日期".equals(tvCheckOut.getText().toString())){
            int price = Integer.valueOf(unitPrice);
            int days = checkOutDay - checkInDay;
            totalPrice = String.valueOf(price * days);
            tvTotalPrice = (TextView) findViewById(R.id.total_price);
            tvTotalPrice.setText(String.format(getResources().getString(R.string.price), totalPrice));
        }
    }

    //提交订单
    private void submitOrder(){
        SharedPreferences pref = getSharedPreferences("Login", 0);
        Boolean isLogin = pref.getBoolean("isLogin", false);
        String key = pref.getString("key", null);
        //String account = pref.getString("account", null);     //传给服务器的people
        if (!isLogin){
            Toast.makeText(this, "请先登录再进行预订酒店操作", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
        }else {
            SharedPreferences prefs = getSharedPreferences("RoomInfo", 0);
            String roomInfoText = prefs.getString("responseText", null);
            RoomInfo roomInfo = Utility.handleRoomInfoResponse(roomInfoText);
            List<MessageBean> rooms = roomInfo.getMessage();
            for (MessageBean room : rooms) {
                if (roomNumber.equals(room.getNumber())){
                    roomCode = room.getId();           //根据房间号获得房间的代码  传给服务器
                }
            }
            if ( !"选择日期".equals(tvCheckIn.getText().toString()) && !"选择日期".equals(tvCheckOut.getText().toString())
                    && !TextUtils.isEmpty(tvTotalPrice.getText().toString()) && !TextUtils.isEmpty(etName.getText().toString().trim())){
                String inTime = Utility.handleLocalTime(tvCheckIn.getText().toString());
                String outTime = Utility.handleLocalTime(tvCheckOut.getText().toString());
                String orderTime = Utility.getCurrentTime();
                String cost = totalPrice;
                Log.d("提交订单", key);
                if (!TextUtils.isEmpty(inTime) && !TextUtils.isEmpty(outTime) && !TextUtils.isEmpty(key)) {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = new FormBody.Builder()
                            .add("intime", inTime)
                            .add("outtime", outTime)
                            .add("ordertime", orderTime)
                            .add("room", roomCode)
                            .add("people", etName.getText().toString().trim())
                            .add("cost", cost)
                            .build();
                    Log.d("提交订单", inTime);
                    Log.d("提交订单", outTime);
                    Log.d("提交订单", orderTime);
                    Log.d("提交订单", roomCode);
                    Log.d("提交订单", etName.getText().toString().trim());
                    Log.d("提交订单", cost);
                    Request request = new Request.Builder()
                            .url(Constant.URL + "order")
                            .header("key", key)
                            .post(body)
                            .build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ConfirmActivity.this, "提交订单失败，请确认网络连接", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String res = response.body().string();
                            final CommonResponse commonResponse = Utility.handleLoginResponse(res);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (commonResponse.getResult()){
                                        Toast.makeText(ConfirmActivity.this, "下单成功", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(ConfirmActivity.this, MainActivity.class));
                                        finish();
                                    }else {
                                        Toast.makeText(ConfirmActivity.this, "服务器问题，提交订单失败，请稍后再试", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                }else {
                    Toast.makeText(this, "提交订单失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
