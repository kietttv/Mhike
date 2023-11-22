package com.example.m_hike;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Friend_Hike extends AppCompatActivity {
    private ActionBar actionBar;
    private Intent intent;
    DbHelper dbHelper;
    AdapterShareHike adapterShareHike;
    RecyclerView hikeShareRv;
    String idFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_hike);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        intent = getIntent();
        idFriend = intent.getStringExtra("idFriend");
        hikeShareRv = findViewById(R.id.hikeShareRv);
        dbHelper = new DbHelper(this);

        ModelUser user = dbHelper.getUserById(idFriend);
        actionBar.setTitle(user.getUser_name());
        hikeShareRv.setHasFixedSize(true);
        loadData();
    }

    private void loadData() {
        adapterShareHike = new AdapterShareHike(this,dbHelper.getAllShare(MainActivity.userID, idFriend));
        hikeShareRv.setAdapter(adapterShareHike);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}