package com.example.m_hike;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabWidget;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class Friend extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private Friend_ViewPagerAdapter adapter;
    private ActionBar actionBar;
    private DbHelper dbHelper = new DbHelper(this);

    private ArrayList<ModelRelationship> countFriend;
    private ArrayList<ModelRelationship> countRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Friend");

        countFriend = dbHelper.getAllRelationships(MainActivity.userID, "Friend");
        countRequest = dbHelper.getAllRelationships(MainActivity.userID, "Request");

        String s= "Request("+ "<font color=#DC0000>"+ String.valueOf(countRequest.size())+"</font>" + ")";

        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.view_pager);
        tabLayout.addTab(tabLayout.newTab().setText("All (" + countFriend.size() +")"));
        tabLayout.addTab(tabLayout.newTab().setText(Html.fromHtml(s)));
        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter = new Friend_ViewPagerAdapter(fragmentManager, getLifecycle());
        viewPager2.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_friend, menu);
        MenuItem btnSearch = menu.findItem(R.id.search);

        btnSearch.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                PopupSearchFriend popupSearchFriend =new PopupSearchFriend();
                popupSearchFriend.showPopup(Friend.this);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onResume() {
        super.onResume();
        countFriend = dbHelper.getAllRelationships(MainActivity.userID, "Friend");
        countRequest = dbHelper.getAllRelationships(MainActivity.userID, "Request");

        tabLayout.getTabAt(0).setText("All (" + countFriend.size() +")");
        String s= "Request("+ "<font color=#DC0000>"+ String.valueOf(countRequest.size())+"</font>" + ")";
        tabLayout.getTabAt(1).setText(Html.fromHtml(s));

        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter = new Friend_ViewPagerAdapter(fragmentManager, getLifecycle());
        viewPager2.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }
}