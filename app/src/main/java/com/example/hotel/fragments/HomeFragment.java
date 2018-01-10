package com.example.hotel.fragments;

import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hotel.adapter.FragAdapter;
import com.example.hotel.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener{

    private SearchView searchView;
    private FragAdapter adapter;
    private ViewPager viewPager;
    private List<Fragment> fragments;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;


    private ImageView cursor;
    private int width = 0; // 游标宽度
    private int offset = 0;// // 动画图片偏移量
    private int currIndex = 0;// 当前页卡编号

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        cursor = (ImageView) view.findViewById(R.id.cursor);
        searchView = (SearchView) view.findViewById(R.id.search_view);
        SearchView.SearchAutoComplete textView = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
        searchView.setQueryHint("搜索城市、区域、楼宇、商圈");
        //searchView.onActionViewExpanded();
        textView.setHint("搜索城市、区域、楼宇、商圈");
        textView.setHintTextColor(Color.parseColor("#80ababab"));

        initAdapter();
        initCursorPos();
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new MyPageChangeListener());

        tv1 = (TextView) view.findViewById(R.id.tv1);
        tv2 = (TextView) view.findViewById(R.id.tv2);
        tv3 = (TextView) view.findViewById(R.id.tv3);
        tv4 = (TextView) view.findViewById(R.id.tv4);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv1:
                viewPager.setCurrentItem(0);
                break;
            case R.id.tv2:
                viewPager.setCurrentItem(1);
                break;
            case R.id.tv3:
                viewPager.setCurrentItem(2);
                break;
            case R.id.tv4:
                viewPager.setCurrentItem(3);
                break;

        }
    }

    private void initAdapter(){
        fragments = new ArrayList<>();
        fragments.add(new Fragment1());
        fragments.add(new Fragment2());
        fragments.add(new Fragment3());
        fragments.add(new Fragment4());
        adapter = new FragAdapter(getChildFragmentManager(), fragments);
    }

    //初始化cursor位置
    public void initCursorPos() {
        // 获取图片宽度
        width = ContextCompat.getDrawable(getActivity(), R.drawable.shape_cursor).getIntrinsicWidth();
        //width = BitmapFactory.decodeResource(getResources(), R.drawable.shape_cursor).getWidth();  //shape不是bitmap文件

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;      // 获取屏幕分辨率宽度
        offset = (screenW / fragments.size() - width) / 2;     // 计算偏移量
        Log.d("游标宽度和屏幕宽度和偏移量", String.valueOf(width) + " " + String.valueOf(screenW) + " " + String.valueOf(offset));

        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        cursor.setImageMatrix(matrix);// 设置动画初始位置 XML文件里scaleType属性要设置为matrix！
    }

    //页面改变监听器
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        int one = offset * 2 + width;  // 页卡1 -> 页卡2 偏移量
        int two = one * 2;  // 页卡1 -> 页卡3 偏移量
        int three = one * 3;  //页卡1 -> 页卡4 偏移量

        @Override
        public void onPageSelected(int arg0) {
            Animation animation = null;
            switch (arg0) {
                case 0:
                    if (currIndex == 1) {
                        animation = new TranslateAnimation(one, 0, 0, 0);
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(two, 0, 0, 0);
                    } else if (currIndex == 3) {
                        animation = new TranslateAnimation(three, 0, 0, 0);
                    }
                    break;
                case 1:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, one, 0, 0);
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(two, one, 0, 0);
                    } else if (currIndex == 3) {
                        animation = new TranslateAnimation(three, one, 0, 0);
                    }
                    break;
                case 2:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, two, 0, 0);
                    } else if (currIndex == 1) {
                        animation = new TranslateAnimation(one, two, 0, 0);
                    } else if (currIndex == 3) {
                        animation = new TranslateAnimation(three, two, 0, 0);
                    }
                    break;
                case 3:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, three, 0, 0);
                    } else if (currIndex == 1) {
                        animation = new TranslateAnimation(one, three, 0, 0);
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(two, three, 0, 0);
                    }
                    break;
            }
            currIndex = arg0;
            animation.setFillAfter(true);// True:图片停在动画结束位置
            animation.setDuration(300);
            cursor.startAnimation(animation);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }




}
