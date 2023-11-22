package com.example.m_hike;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class FriendRequestFragment extends Fragment {
    private RecyclerView friendRequestRv;
    private DbHelper dbHelper;
    private AdapterFriendRequest friendRequest;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_friend_request,container,false);
        friendRequestRv = v.findViewById(R.id.friendRequestRv);
        dbHelper = new DbHelper(getContext());
        friendRequestRv.setHasFixedSize(true);

        loadData();
        return v;
    }

    public void loadData() {
        friendRequest  = new AdapterFriendRequest(getContext(), dbHelper.getAllRelationships(MainActivity.userID, "Request"), this);
        friendRequestRv.setAdapter(friendRequest);
    }


    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }


}