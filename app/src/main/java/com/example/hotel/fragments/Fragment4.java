package com.example.hotel.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.hotel.adapter.RoomAdapter;
import com.example.hotel.R;
import com.example.hotel.data.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * 发现
 */

public class Fragment4 extends Fragment {

    private List<Room> roomList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home4, container, false);
        initRooms();
        RoomAdapter adapter = new RoomAdapter(getActivity(), R.layout.room_item, roomList);
        ListView listView = (ListView) view.findViewById(R.id.list_view4);
        listView.setAdapter(adapter);
        return view;
    }

    private void initRooms(){
        for (int i = 0; i < 2; i++) {
            Room room1 = new Room("宏信国际花园2栋1单元10楼", "1卧室1卫 可住2人", R.drawable.room3);
            roomList.add(room1);
            Room room2 = new Room("宏信国际花园2栋1单元10楼", "1卧室1卫 可住2人", R.drawable.room3);
            roomList.add(room2);
            Room room3 = new Room("宏信国际花园2栋1单元10楼", "1卧室1卫 可住2人", R.drawable.room3);
            roomList.add(room3);

        }
    }
}
