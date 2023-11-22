package com.example.m_hike;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailObservation extends AppCompatActivity {

    ImageView imageDetailIv;
    TextView obserNameDetailTv, obserCommentDetailTv, obserTimeDetailTv;
    ActionBar actionBar;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_observation);

        map();

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Observations Detail");

        Intent intent = getIntent();
        String id = intent.getStringExtra("OBSER_ID");

        loadDataById(id);
    }

    private void loadDataById(String id) {

        String selectQuery =  "SELECT * FROM "+ Table_Observations.TABLE_NAME + " WHERE " + Table_Observations.OBSERVATION_ID + " = " + id;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do {
                String obserName =  ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_Observations.OBSERVATION_NAME));
                String obserComment = ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_Observations.OBSERVATION_COMMENT));
                String obserTime = ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_Observations.OBSERVATION_TIME));
                String obserImage = ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_Observations.OBSERVATION_IMAGE));

                obserNameDetailTv.setText(obserName);
                obserCommentDetailTv.setText(obserComment);
                obserTimeDetailTv.setText(obserTime);
                imageDetailIv.setImageURI(Uri.parse(obserImage));

            }while (cursor.moveToNext());
        }

        db.close();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void map(){
        imageDetailIv = (ImageView) findViewById(R.id.imageDetailIv);
        obserNameDetailTv = (TextView) findViewById(R.id.obserNameDetailTv);
        obserCommentDetailTv = (TextView) findViewById(R.id.obserCommentDetailTv);
        obserTimeDetailTv = (TextView) findViewById(R.id.obserTimeDetailTv);
        dbHelper = new DbHelper(this);
    }

}