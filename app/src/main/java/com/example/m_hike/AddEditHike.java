package com.example.m_hike;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.time.LocalDate;

public class AddEditHike extends AppCompatActivity {

    EditText nameHikeEdt, locationEdt, lengthHikeEdt, descriptionEdt;
    RadioButton parkingYesRdBtn, parkingNoRdBtn, levelLowRdBtn, levelNormalRdBtn, levelHighRdBtn;
    Button saveBtn;
    String userId, nameHike, location, lengthHike, description, parkingHike, levelHike;
    //userid
    Boolean isEdit;
    DbHelper dbHelper;
    Intent intent;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //some code
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_hike);

        map();

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Update Hike");

        intent = getIntent();
        isEdit = intent.getBooleanExtra("isEdit",false);

        if(isEdit){
            saveBtn.setText("Hike Update");
            nameHikeEdt.setText(intent.getStringExtra("NAME"));
            locationEdt.setText(intent.getStringExtra("LOCATION"));
            lengthHikeEdt.setText(intent.getStringExtra("LENGTH"));
            descriptionEdt.setText(intent.getStringExtra("DESCRIPTION"));

            parkingYesRdBtn.setChecked(true);
            if(intent.getStringExtra("PARKING").equals("No")){
                parkingNoRdBtn.setChecked(true);
            }

            levelLowRdBtn.setChecked(true);
            if(intent.getStringExtra("LEVEL").equals("Normal")){
                levelNormalRdBtn.setChecked(true);
            } else if (intent.getStringExtra("LEVEL").equals("High")) {
                levelHighRdBtn.setChecked(true);
            }
        }
        else {
            actionBar.setTitle("Add New Hike");
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }

    private void saveData() {
        //some code
        userId = intent.getStringExtra("userId");
        nameHike = nameHikeEdt.getText().toString();
        location= locationEdt.getText().toString();
        lengthHike= lengthHikeEdt.getText().toString();
        String date = ""+ LocalDate.now();

        description= descriptionEdt.getText().toString();
        if (description.isEmpty()){
            description = "No Description";
        }

        parkingHike = "Yes";
        if(parkingNoRdBtn.isChecked()){
            parkingHike = "No";
        }

        levelHike = "Low";
        if(levelNormalRdBtn.isChecked()){
            levelHike = "Normal";
        } else if (levelHighRdBtn.isChecked()) {
            levelHike = "High";
        }

        if(!nameHike.isEmpty() && !location.isEmpty() && !lengthHike.isEmpty()){
            if(isEdit){
                dbHelper.updateHike(
                        ""+intent.getStringExtra("ID"),
                        ""+nameHike,
                        ""+location,
                        ""+date,
                        ""+parkingHike,
                        ""+lengthHike,
                        ""+levelHike,
                        ""+description
                );
                toast("Updated Successfully....");
                Intent intentSucces = new Intent(this, MainActivity.class);
                intentSucces.putExtra("userID", MainActivity.userID);
                intentSucces.putExtra("userEmail", MainActivity.userEmail);
                startActivity(intentSucces);
            }
            else {
                Intent intentConfirm = new Intent(this, AddConfirmDetailHike.class);
                intentConfirm.putExtra("CONFIRM_DETAIL", "CONFIRM");
                intentConfirm.putExtra("ID", intent.getStringExtra("ID"));
                intentConfirm.putExtra("USER_ID", userId);
                intentConfirm.putExtra("NAME",nameHike);
                intentConfirm.putExtra("LOCATION",location);
                intentConfirm.putExtra("LENGTH",lengthHike);
                intentConfirm.putExtra("DESCRIPTION",description);
                intentConfirm.putExtra("DATE",date);
                intentConfirm.putExtra("PARKING",parkingHike);
                intentConfirm.putExtra("LEVEL",levelHike);
                startActivity(intentConfirm);
                finish();
            }
        }
        else {
            toast("Please enter full fill in formation");
        }
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
        nameHikeEdt = (EditText) findViewById(R.id.nameHikeEdt);
        locationEdt = (EditText) findViewById(R.id.locationEdt);
        lengthHikeEdt = (EditText) findViewById(R.id.lengthHikeEdt);
        descriptionEdt = (EditText) findViewById(R.id.descriptionEdt);
        parkingYesRdBtn = (RadioButton) findViewById(R.id.parkingYesRdBtn);
        parkingNoRdBtn = (RadioButton) findViewById(R.id.parkingNoRdBtn);
        levelLowRdBtn = (RadioButton) findViewById(R.id.levelLowRdBtn);
        levelNormalRdBtn = (RadioButton) findViewById(R.id.levelNormalRdBtn);
        levelHighRdBtn = (RadioButton) findViewById(R.id.levelHighRdBtn);
        saveBtn = (Button) findViewById(R.id.saveBtn);
        dbHelper = new DbHelper(this);
    }

    private void toast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}