package com.example.m_hike;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Splash_Screen extends AppCompatActivity {
    TextView txtHiking, txtTrack;
    RelativeLayout relativeLayout;
    Animation txtAnimation, layoutAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        txtAnimation = AnimationUtils.loadAnimation(Splash_Screen.this, R.anim.fall_down);
        layoutAnimation = AnimationUtils.loadAnimation(Splash_Screen.this, R.anim.bottom_to_top);

        txtHiking = findViewById(R.id.txtHiking);
        txtTrack = findViewById(R.id.txtTrack);
        relativeLayout = findViewById(R.id.relMain);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                relativeLayout.setVisibility(View.VISIBLE);
                relativeLayout.setAnimation(layoutAnimation);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        txtHiking.setVisibility(View.VISIBLE);
                        txtTrack.setVisibility(View.VISIBLE);

                        txtHiking.setAnimation(layoutAnimation);
                        txtTrack.setAnimation(layoutAnimation);

                    }
                },900);
            }
        },500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent1 = getIntent();
                Intent intent = new Intent(Splash_Screen.this,MainActivity.class);
                intent.putExtra("userID", intent1.getStringExtra("userID"));
                intent.putExtra("userEmail", intent1.getStringExtra("userEmail"));
                startActivity(intent);
            }
        },4500);
    }

    @Override
    public void onBackPressed() {}
}
