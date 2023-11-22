package com.example.m_hike;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AllFriendFragment extends Fragment {

    private RecyclerView allFriendRv;
    private DbHelper dbHelper;
    private AdapterAllFriend allFriend;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_all_friend,container,false);
        allFriendRv = v.findViewById(R.id.allFriend);
        dbHelper = new DbHelper(getContext());
        allFriendRv.setHasFixedSize(true);
        loadData();
        return v;
    }

    public void loadData() {
        allFriend  = new AdapterAllFriend(getContext(), dbHelper.getAllRelationships(MainActivity.userID, "Friend"), this);
        allFriendRv.setAdapter(allFriend);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }
}