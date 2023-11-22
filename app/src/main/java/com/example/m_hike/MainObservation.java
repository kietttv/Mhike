package com.example.m_hike;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
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
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainObservation extends AppCompatActivity {

    private ActionBar actionBar;
    private FloatingActionButton AddObserBtn;
    private RecyclerView ObservationRv;
    private DbHelper dbHelper;
    private AdapterObser adapterObser;
    private Intent intent;
    private String idObser;
    private String sortByNameASC = Table_Observations.OBSERVATION_NAME + " ASC";
    private String sortByNameDESC = Table_Observations.OBSERVATION_NAME + " DESC";
    private String sortByDateASC = Table_Observations.OBSERVATION_TIME + " ASC";
    private String sortByDateDESC = " DATE(" +  Table_Observations.OBSERVATION_TIME + ") DESC";
    private String currentSort = sortByDateASC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_observation);

        map();

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Observation");

        ObservationRv.setHasFixedSize(true);

        intent = getIntent();
        idObser = intent.getStringExtra("HIKE_ID");
        loadDataObser(idObser, currentSort);

        AddObserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAdd = new Intent(MainObservation.this, AddEditObser.class);
                intentAdd.putExtra("isEditObser",  false);
                intentAdd.putExtra("HIKE_ID", idObser);
                startActivity(intentAdd);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);

        MenuItem item = menu.findItem(R.id.search);
        MenuItem delete = menu.findItem(R.id.deleteAll);
        MenuItem sort = menu.findItem(R.id.sort);
        MenuItem profile = menu.findItem(R.id.profile);
        
        delete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainObservation.this);
                builder.setTitle("Confirm deletion");
                builder.setMessage("Are you sure you want to delete all observations?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dbHelper.deleteAllObser(idObser);
                        onResume();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.create();
                builder.show();
                return true;
            }
        });

        sort.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                sortObserDialog();
                return true;
            }
        });

        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchObser(idObser, query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchObser(idObser, newText);
                return true;
            }
        });

        profile.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                Intent intent = new Intent(MainObservation.this, Profile.class);
                startActivity(intent);
                return true;
            }
        });

        return true;
    }

    private void searchObser( String idHike, String query) {
        adapterObser = new AdapterObser(this,dbHelper.getSearchObser(idHike,query));
        ObservationRv.setAdapter(adapterObser);
    }

    private void sortObserDialog() {
        String[] option = {"Name ASC", "Name DESC", "Date ASC", "Date DESC"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0){
                    loadDataObser(idObser, sortByNameASC);
                } else if (which == 1) {
                    loadDataObser(idObser, sortByNameDESC);
                } else if (which == 4) {
                    loadDataObser(idObser, sortByDateASC);
                } else if (which == 5) {
                    loadDataObser(idObser, sortByDateDESC);
                }
            }
        });
        builder.create().show();
    }

    private void loadDataObser(String idObser, String currentSort) {
        adapterObser = new AdapterObser(this,dbHelper.getAllObserData(idObser, currentSort));
        ObservationRv.setAdapter(adapterObser);
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

    @Override
    protected void onResume() {
        loadDataObser(idObser, idObser);
        super.onResume();
    }

    private void map(){
        AddObserBtn = (FloatingActionButton) findViewById(R.id.AddObserBtn);
        ObservationRv = findViewById(R.id.ObservationRv);
        dbHelper = new DbHelper(this);
    }

}