package com.example.m_hike;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class AddConfirmDetailHike extends AppCompatActivity {

    TextView nameHikeConfirmTv, locationConfirmTv, lengthHikeConfirmTv, descriptionConfirmTv;
    RadioButton parkingYesConfirmRdBtn, parkingNoConfirmRdBtn, levelLowConfirmRdBtn, levelNormalConfirmRdBtn, levelHighConfirmRdBtn;
    Button confirmBtn;
    DbHelper dbHelper;
    Intent intent;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_add_hike);

        map();

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Confirm Hike Information");

        intent = getIntent();

        if(intent.getStringExtra("CONFIRM_DETAIL").equals("DETAIL")){
            confirmBtn.setText("Show All Observation");
            actionBar.setTitle("Hike Detail");
        }

        nameHikeConfirmTv.setText(intent.getStringExtra("NAME"));
        locationConfirmTv.setText(intent.getStringExtra("LOCATION"));
        lengthHikeConfirmTv.setText(intent.getStringExtra("LENGTH"));
        descriptionConfirmTv.setText(intent.getStringExtra("DESCRIPTION"));

        parkingYesConfirmRdBtn.setChecked(true);
        if(intent.getStringExtra("PARKING").equals("No")){
            parkingNoConfirmRdBtn.setChecked(true);
        }

        levelLowConfirmRdBtn.setChecked(true);
        if(intent.getStringExtra("LEVEL").equals("Normal")){
            levelNormalConfirmRdBtn.setChecked(true);
        } else if (intent.getStringExtra("LEVEL").equals("High")) {
            levelHighConfirmRdBtn.setChecked(true);
        }

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(intent.getStringExtra("CONFIRM_DETAIL").equals("DETAIL")){
                    Intent intentObser = new Intent(AddConfirmDetailHike.this, MainObservation.class);
                    intentObser.putExtra("HIKE_ID", intent.getStringExtra("ID"));
                    intentObser.putExtra("userID", intent.getStringExtra("userId"));
                    startActivity(intentObser);
                    finish();
                }
                else {
                    confirmData();
                }
            }
        });

    }

    private void confirmData() {

        long id =  dbHelper.insertHike(
                ""+intent.getStringExtra("USER_ID"),
                ""+intent.getStringExtra("NAME"),
                ""+intent.getStringExtra("LOCATION"),
                ""+intent.getStringExtra("DATE"),
                ""+intent.getStringExtra("PARKING"),
                ""+intent.getStringExtra("LENGTH"),
                ""+intent.getStringExtra("LEVEL"),
                ""+intent.getStringExtra("DESCRIPTION")
        );

        toast("Inserted Successfully: "+intent.getStringExtra("NAME"));
        Intent intentSucces = new Intent(this, MainActivity.class);
        intentSucces.putExtra("userID", intent.getStringExtra("USER_ID"));
        intentSucces.putExtra("userEmail", MainActivity.userEmail);
        startActivity(intentSucces);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("userID", MainActivity.userID);
        intent.putExtra("userEmail", MainActivity.userEmail);
        startActivity(intent);
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {}

    private void map(){
        nameHikeConfirmTv = (TextView) findViewById(R.id.nameHikeConfirmTv);
        locationConfirmTv = (TextView) findViewById(R.id.locationConfirmTv);
        lengthHikeConfirmTv = (TextView) findViewById(R.id.lengthHikeConfirmTv);
        descriptionConfirmTv = (TextView) findViewById(R.id.descriptionConfirmTv);
        parkingYesConfirmRdBtn = (RadioButton) findViewById(R.id.parkingYesConfirmRdBtn);
        parkingNoConfirmRdBtn = (RadioButton) findViewById(R.id.parkingNoConfirmRdBtn);
        levelLowConfirmRdBtn = (RadioButton) findViewById(R.id.levelLowConfirmRdBtn);
        levelNormalConfirmRdBtn = (RadioButton) findViewById(R.id.levelNormalConfirmRdBtn);
        levelHighConfirmRdBtn = (RadioButton) findViewById(R.id.levelHighConfirmRdBtn);
        confirmBtn = (Button) findViewById(R.id.confirmBtn);
        dbHelper = new DbHelper(this);
    }
    private void toast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}