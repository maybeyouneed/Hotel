package com.example.hotel.Activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hotel.R;
import com.example.hotel.Wheelview.WheelView;

import java.util.Arrays;
import java.util.Calendar;

public class ConfirmActivity extends AppCompatActivity implements View.OnClickListener{

    private Calendar calendar;
    private TextView tvCheckIn;
    private TextView tvCheckOut;
    private static final String[] TIMES = new String[]{"14:00 ~ 15:00", "15:00 ~ 16:00",
            "16:00 ~ 17:00", "17:00 ~ 18:00", "18:00 ~ 19:00", "20:00 ~ 21:00", "21:00 ~ 22:00", "22:00 ~ 23:00", "23:00 ~ 24:00"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        Intent intent = getIntent();
        String hotelName = intent.getStringExtra("hotelName");
        int imgId = intent.getIntExtra("img", 0);

        ImageView roomImg = (ImageView) findViewById(R.id.img_room);
        TextView tvRoomName = (TextView) findViewById(R.id.tv_room_name1);
        TextView tvRoomInfo = (TextView) findViewById(R.id.tv_room_info);
        roomImg.setImageResource(imgId);
        tvRoomName.setText(hotelName);
        tvRoomInfo.setText("1卧室1卫|2人");

        tvCheckIn = (TextView) findViewById(R.id.tv_check_in);
        tvCheckOut = (TextView) findViewById(R.id.tv_check_out);

        LinearLayout checkInDate = (LinearLayout) findViewById(R.id.check_in_date);
        LinearLayout checkOutDate = (LinearLayout) findViewById(R.id.check_out_date);
        RelativeLayout checkInTime = (RelativeLayout) findViewById(R.id.check_in_time);
        checkInDate.setOnClickListener(this);
        checkOutDate.setOnClickListener(this);
        checkInTime.setOnClickListener(this);
        calendar = Calendar.getInstance();
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
                        TextView tvCheckTime = (TextView) findViewById(R.id.tv_check_in_time);
                        tvCheckTime.setText(item);
                    }
                });

                new AlertDialog.Builder(this)
                        .setTitle("预计到店时间")
                        .setView(view)
                        .setPositiveButton("确定", null)
                        .show();
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
                }if (id == R.id.check_out_date){
                    Log.d("退房日期选择", time);
                    tvCheckOut.setText(time);
                }
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
        //自动弹出键盘问题
        datePickerDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }



}
