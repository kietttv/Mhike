package com.example.m_hike;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Profile extends AppCompatActivity {
    TextView user_name;
    TextView uname;
    TextView user_email;
    ImageView userIv;
    TextView user_dob;
    TextView user_gender;
    LinearLayout friend, editProfile, changePass;
    Button logoutBtn;
    ModelUser user = null;
    DbHelper dbHelper;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        map();

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Profile");

        dbHelper = new DbHelper(getApplicationContext());
        user = dbHelper.getUserByEmail(MainActivity.userEmail);
        user_email.setText("Email: " + user.getUser_email());
        user_name.setText(user.getUser_name());
        uname.setText("Name: " +user.getUser_name());
        user_dob.setText("Birthday: " +user.getUser_dayOfBirth());
        user_gender.setText("Gender: " +user.getUser_gender());
        Uri image = Uri.parse(user.getUser_image());
        userIv.setImageURI(image);

        friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, Friend.class);
                startActivity(intent);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, LoginAndRegister.class);
                startActivity(intent);
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, EditProfile.class);
                startActivity(intent);
            }
        });

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, ChangePassword.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void map(){
        user_email = findViewById(R.id.user_email);
        user_name = findViewById(R.id.user_name);
        uname = findViewById(R.id.uname);
        user_dob = findViewById(R.id.user_dob);
        user_gender = findViewById(R.id.user_gender);
        friend = findViewById(R.id.friend);
        logoutBtn = findViewById(R.id.logoutBtn);
        editProfile = findViewById(R.id.editProfile);
        changePass = findViewById(R.id.changePass);
        userIv = findViewById(R.id.userIv);
    }
}