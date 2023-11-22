package com.example.m_hike;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class PopupShare  {

    private EditText editTextSearch;
    private ImageButton searchBtn;
    private RecyclerView searchRv;
    private DbHelper dbHelper;
    private AdapteShare adapterShare;
    Intent intent;

    public void showPopup(final Activity activity, String idHike){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_popup_search_friend, null);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        searchRv = dialogView.findViewById(R.id.searchRv);
        searchRv.setLayoutManager(new LinearLayoutManager(dialogView.getContext()));
        searchRv.setHasFixedSize(true);
        editTextSearch = dialogView.findViewById(R.id.editTextSearch);
        searchBtn = dialogView.findViewById(R.id.searchBtn);
        dbHelper = new DbHelper(dialogView.getContext());
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchContent = String.valueOf(editTextSearch.getText());
                if(!searchContent.isEmpty()){
                    adapterShare = new AdapteShare(dialogView.getContext(), dbHelper.getAllRelationships(MainActivity.userID, "Friend"), idHike);
                    searchRv.setAdapter(adapterShare);
                }else {
                    searchRv.setAdapter(null);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
}