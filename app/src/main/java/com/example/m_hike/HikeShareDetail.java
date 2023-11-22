package com.example.m_hike;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class HikeShareDetail extends AppCompatActivity {

    String hikeId;
    TextView nameHikeConfirmTv, locationConfirmTv, lengthHikeConfirmTv, descriptionConfirmTv, parking, level;
    RecyclerView observationRv;
    AdapterObserShare adapterObser;
    DbHelper dbHelper;
    Intent intent;
    private ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hike_share_detail);
        map();
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Hike Details");

        observationRv.setHasFixedSize(true);

        intent = getIntent();
        hikeId = intent.getStringExtra("HIKE_ID");
        nameHikeConfirmTv.setText(intent.getStringExtra("NAME"));
        locationConfirmTv.setText(intent.getStringExtra("LOCATION"));
        lengthHikeConfirmTv.setText(intent.getStringExtra("LENGTH"));
        descriptionConfirmTv.setText(intent.getStringExtra("DESCRIPTION"));
        parking.setText("Parking Available: "+intent.getStringExtra("PARKING"));
        level.setText("Level of Difficulty: "+intent.getStringExtra("LEVEL"));


        loadDataObser(hikeId, Table_Observations.OBSERVATION_NAME + " ASC");
    }
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return super.onSupportNavigateUp();
    }
    private void loadDataObser(String idObser, String currentSort) {
        adapterObser = new AdapterObserShare(this,dbHelper.getAllObserData(idObser, currentSort));
        observationRv.setAdapter(adapterObser);
    }
    private void map(){
        nameHikeConfirmTv = findViewById(R.id.nameHikeConfirmTv);
        locationConfirmTv = findViewById(R.id.locationConfirmTv);
        lengthHikeConfirmTv = findViewById(R.id.lengthHikeConfirmTv);
        descriptionConfirmTv = findViewById(R.id.descriptionConfirmTv);
        observationRv = findViewById(R.id.observationRv);
        parking = findViewById(R.id.parking);
        level = findViewById(R.id.level);
        dbHelper = new DbHelper(getApplicationContext());
    }

}