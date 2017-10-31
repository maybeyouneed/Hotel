package com.example.hotel;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Button;

import com.example.hotel.Adapter.CityAdapter;
import com.example.hotel.Adapter.HotelAdapter;
import com.example.hotel.data.HotCity;
import com.example.hotel.data.Hotel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private List<HotCity> cityList = new ArrayList<>();
    private List<Hotel> selectionList = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView recyclerView2;
    SearchView searchView;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initList();
    }

    private void initViews(){
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView2 = (RecyclerView) findViewById(R.id.recycler_view2);
        searchView = (SearchView) findViewById(R.id.search_view);
        login = (Button)findViewById(R.id.jump_login);

        login.setOnClickListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView2.setLayoutManager(layoutManager2);
        CityAdapter cityAdapter = new CityAdapter(cityList);
        HotelAdapter hotelAdapter = new HotelAdapter(selectionList);
        recyclerView.setAdapter(cityAdapter);
        recyclerView2.setAdapter(hotelAdapter);

        SearchView.SearchAutoComplete textView = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
        searchView.setQueryHint("搜索城市、区域、楼宇、商圈");
        //searchView.onActionViewExpanded();
        textView.setHint("搜索城市、区域、楼宇、商圈");
        textView.setHintTextColor(Color.parseColor("#80ababab"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.jump_login:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void initList(){
        HotCity hangzhou = new HotCity("杭州", R.drawable.hangzhou);
        cityList.add(hangzhou);
        HotCity chongqing = new HotCity("重庆", R.drawable.chongqing);
        cityList.add(chongqing);
        HotCity shanghai = new HotCity("上海", R.drawable.shanghai);
        cityList.add(shanghai);
        HotCity ningbo = new HotCity("宁波", R.drawable.ningbo);
        cityList.add(ningbo);

        Hotel hotel1 = new Hotel("1号房间", R.drawable.hotel1);
        selectionList.add(hotel1);
        Hotel hotel2 = new Hotel("2号房间", R.drawable.hotel2);
        selectionList.add(hotel2);
        Hotel hotel3 = new Hotel("3号房间", R.drawable.hotel3);
        selectionList.add(hotel3);
        Hotel hotel4 = new Hotel("4号房间", R.drawable.hotel4);
        selectionList.add(hotel4);
    }
}
