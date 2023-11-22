package com.example.m_hike;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton AddHikeBtn;
    private RecyclerView hikeRv;
    private DbHelper dbHelper;
    private AdapterHike adapterHike;
    Intent intent;
    public static String userID;
    public static String userEmail;
    private String sortByNameASC = Table_Hike.HIKE_NAME + " ASC";
    private String sortByNameDESC = Table_Hike.HIKE_NAME + " DESC";
    private String sortByLenghASC = Table_Hike.HIKE_LENGTH + " ASC";
    private String sortByLenghDESC = Table_Hike.HIKE_LENGTH + " DESC";
    private String sortByDateASC = Table_Hike.HIKE_LENGTH + " ASC";
    private String sortByDateDESC = Table_Hike.HIKE_LENGTH + " DESC";
    private String currentSort = sortByDateASC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = getIntent();
        userID = intent.getStringExtra("userID");
        userEmail = intent.getStringExtra("userEmail");
        map();
        hikeRv.setHasFixedSize(true);
        loadDataHike(currentSort);

        AddHikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditHike.class);
                intent.putExtra("isEdit",  false);
                intent.putExtra("userId", userID);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);

        MenuItem item = menu.findItem(R.id.search);
        MenuItem delete = menu.findItem(R.id.deleteAll);
        MenuItem sortHike = menu.findItem(R.id.sort);
        MenuItem profile = menu.findItem(R.id.profile);

        delete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Confirm deletion");
                builder.setMessage("Are you sure you want to delete all hikes?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dbHelper.deleteAllHike();
                        onResume();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.create().show();
                return true;
            }
        });

        sortHike.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                sortHikeDialog();
                return true;
            }
        });

        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchHike(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchHike(newText);
                return true;
            }
        });

        profile.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                Intent intent = new Intent(MainActivity.this, Profile.class);
                startActivity(intent);
                return true;
            }
        });

        return true;
    }

    private void sortHikeDialog() {
        String[] option = {"Name ASC", "Name DESC", "Lengh ASC", "Lengh DESC", "Date ASC", "Date DESC"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0){
                    loadDataHike(sortByNameASC);
                } else if (which == 1) {
                    loadDataHike(sortByNameDESC);
                } else if (which == 2) {
                    loadDataHike(sortByLenghASC);
                } else if (which == 3) {
                    loadDataHike(sortByLenghDESC);
                } else if (which == 4) {
                    loadDataHike(sortByDateASC);
                } else if (which == 5) {
                    loadDataHike(sortByDateDESC);
                }
            }
        });
        builder.create().show();
    }

    private void searchHike(String query) {
        adapterHike = new AdapterHike(this,dbHelper.getSearchHike(query));
        hikeRv.setAdapter(adapterHike);
    }

    private void loadDataHike(String currentSort){
        adapterHike = new AdapterHike(this,dbHelper.getAllData(currentSort, userID));
        hikeRv.setAdapter(adapterHike);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDataHike(currentSort);
    }

    private void map(){
        AddHikeBtn = (FloatingActionButton) findViewById(R.id.AddHikeBtn);
        hikeRv = findViewById(R.id.HikeRv);
        dbHelper = new DbHelper(this);
    }

    @Override
    public void onBackPressed() {}
}