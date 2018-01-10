package com.example.hotel.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.hotel.R;
import com.example.hotel.data.Constant;
import com.example.hotel.gson.MessageBean;
import com.example.hotel.gson.RoomInfo;
import com.example.hotel.utils.Utility;

import java.util.LinkedHashMap;
import java.util.List;

public class RoomInfoActivity extends AppCompatActivity {

    private SliderLayout sliderLayout;
    private LinkedHashMap<String, String> urlMaps = new LinkedHashMap<>();
    private String hotelName;
    private String price;
    private String number;   //房间号
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_info);

        //显示进度对话框
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        SharedPreferences pref = getSharedPreferences("RoomInfo", 0);
        String roomInfoText = pref.getString("responseText", null);
        if (roomInfoText != null){
            RoomInfo roomInfo = Utility.handleRoomInfoResponse(roomInfoText);
            List<MessageBean> rooms = roomInfo.getMessage();
            hotelName = getIntent().getStringExtra("hotelName");
            for (MessageBean room : rooms){
                if (hotelName.equals(room.getHotelName())) {
                    urlMaps.put("图1", Constant.URL_IMG + room.getImageUrl().getPic1());
                    urlMaps.put("图2", Constant.URL_IMG + room.getImageUrl().getPic2());
                    urlMaps.put("图3", Constant.URL_IMG + room.getImageUrl().getPic3());
                    price = room.getPrice();
                    number = getIntent().getStringExtra("hotelNumber");
                    String address = room.getAddress();
                    showRoomInfo(hotelName, price, number, address);
                    break;
                }
            }
        }else {
            Toast.makeText(this, "获取房间信息失败", Toast.LENGTH_SHORT).show();
        }

        //立即预定的事件监听
        Button bookRoom = (Button) findViewById(R.id.book_room);
        bookRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RoomInfoActivity.this, ConfirmActivity.class);
                intent.putExtra("hotelName", hotelName);
                intent.putExtra("img", urlMaps.get("图1"));
                intent.putExtra("price", price);    //每晚的价格
                intent.putExtra("hotelNumber", number);   //房间号
                startActivity(intent);
            }
        });

    }

    private void showRoomInfo(String hotelName, String price, String roomNumber, String address){
        TextView tvHotelName = (TextView) findViewById(R.id.tv_room_name);
        tvHotelName.setText(hotelName);

        sliderLayout = (SliderLayout) findViewById(R.id.slider);
        showSliderView(urlMaps);

        TextView tvPrice = (TextView) findViewById(R.id.tv_price);
        tvPrice.setText(price);
        TextView tvNumber = (TextView) findViewById(R.id.tv_room_number);
        tvNumber.setText(String.format(getResources().getString(R.string.room_number), roomNumber));
        TextView tvAddress = (TextView) findViewById(R.id.tv_address);
        tvAddress.setText(String.format(getResources().getString(R.string.room_address), address));

        progressDialog.dismiss();
    }

    private void showSliderView(LinkedHashMap<String, String> map){
        for(String name : map.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            textSliderView
                    .description(name) //描述
                    .image(map.get(name)) //image方法可以传入图片url、资源id、File
                    .setScaleType(BaseSliderView.ScaleType.Fit); //图片缩放类型
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra",name);//传入参数
            sliderLayout.addSlider(textSliderView);//添加一个滑动页面

            sliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);//滑动动画
            //sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);//默认指示器样式
            sliderLayout.setCustomAnimation(new DescriptionAnimation());//设置图片描述显示动画
            sliderLayout.setDuration(4000);//设置滚动时间，也是计时器时间
        }
    }

}
