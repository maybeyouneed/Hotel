package com.example.hotel.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.hotel.R;

import java.util.HashMap;

public class RoomInfoActivity extends AppCompatActivity {

    private SliderLayout sliderLayout;
    private TextView tvHotelName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_info);

        sliderLayout = (SliderLayout) findViewById(R.id.slider);


        final HashMap<String,Integer> urlMaps = new HashMap<>();
        urlMaps.put("图1", R.drawable.hotel1);
        urlMaps.put("图2", R.drawable.hotel2);
        urlMaps.put("图3", R.drawable.hotel3);

        for(String name : urlMaps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            textSliderView
                    .description(name) //描述
                    .image(urlMaps.get(name)) //image方法可以传入图片url、资源id、File
                    .setScaleType(BaseSliderView.ScaleType.Fit); //图片缩放类型
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra",name);//传入参数
            sliderLayout.addSlider(textSliderView);//添加一个滑动页面

            sliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);//滑动动画
            //sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);//默认指示器样式
            sliderLayout.setCustomAnimation(new DescriptionAnimation());//设置图片描述显示动画
            sliderLayout.setDuration(4000);//设置滚动时间，也是计时器时间
            sliderLayout.addOnPageChangeListener(onPageChangeListener);
        }

        Intent intent = getIntent();
        final String hotelName = intent.getStringExtra("hotelName");
        tvHotelName = (TextView) findViewById(R.id.tv_room_name);
        tvHotelName.setText(hotelName);

        Button bookRoom = (Button) findViewById(R.id.book_room);
        bookRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(RoomInfoActivity.this, ConfirmActivity.class);
                intent1.putExtra("hotelName", hotelName);
                intent1.putExtra("img", urlMaps.get("图1"));
                startActivity(intent1);
            }
        });

    }

    //页面改变监听
    private ViewPagerEx.OnPageChangeListener onPageChangeListener=new ViewPagerEx.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageSelected(int position) {
            Log.d("RoomInfo", "Page Changed: " + position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {}
    };
}
