package com.example.hotel.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.hotel.Fragments.HomeFragment;
import com.example.hotel.Fragments.MessageFragment;
import com.example.hotel.Fragments.MineFragment;
import com.example.hotel.Fragments.OrderFragment;
import com.example.hotel.R;
import com.hjm.bottomtabbar.BottomTabBar;


public class MainActivity extends AppCompatActivity {

    BottomTabBar bottomTabBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomTabBar = (BottomTabBar) findViewById(R.id.bottom_tab_bar);
        bottomTabBar.init(getSupportFragmentManager())
                .setImgSize(70, 70)
                .setFontSize(11)
                .setTabPadding(10, 6, 10)
                .setChangeColor(Color.parseColor("#3483EE"), Color.parseColor("#707070"))
                .addTabItem("首页", R.drawable.home, HomeFragment.class)
                .addTabItem("订单", R.drawable.order, OrderFragment.class)
                .addTabItem("消息", R.drawable.message, MessageFragment.class)
                .addTabItem("我的", R.drawable.mine, MineFragment.class)
                .setTabBarBackgroundResource(R.drawable.layer_list_bottom)
                .isShowDivider(false);

        //启动别的Activity之后finish() this
        Intent intent = getIntent();
        int index = intent.getIntExtra("index", 0);
        setCurrentTab(index);

    }

     //启动LoginActivity之后finish() this
    private void setCurrentTab(int index){
        bottomTabBar.setCurrentTab(index);
    }

}
