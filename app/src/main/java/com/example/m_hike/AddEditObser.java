package com.example.m_hike;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddEditObser extends AppCompatActivity {

    EditText nameObserEdt, commentObserEdt;
    ImageView imageObserImgV;
    Button saveObserBtn;
    String hike_id, obser_id, obserName, obserComment, obserImage, obserTime;
    ActionBar actionBar;
    Boolean isEditObser;
    private Uri imageUri = Uri.parse("android.resource://com.example.m_hike/drawable/no_image_obser");
    DbHelper dbHelper;
    Intent intent;
    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int STORAGE_PERMISSION_CODE = 200;
    private static final int IMAGE_FROM_GALLERY_CODE = 300;
    private static final int IMAGE_FROM_CAMERA_CODE = 400;

    private String[] cameraPermission;
    private String[] storagePermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_obser);

        map();

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        cameraPermission = new String[]{android.Manifest.permission.CAMERA,android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE};

        intent = getIntent();
        isEditObser = intent.getBooleanExtra("isEditObser", false);
        if (isEditObser){

            actionBar.setTitle("Update Observation");

            hike_id = intent.getStringExtra("HIKE_ID");
            obser_id = intent.getStringExtra("ID");
            obserName = intent.getStringExtra("NAME");
            obserTime = intent.getStringExtra("TIME");
            obserComment = intent.getStringExtra("COMMENT");
            obserImage = intent.getStringExtra("IMAGE");

            nameObserEdt.setText(obserName);
            commentObserEdt.setText(obserComment);

            imageUri = Uri.parse(obserImage);
            imageObserImgV.setImageURI(imageUri);

        }
        else {
            actionBar.setTitle("Add New Observation");
            imageObserImgV.setImageURI(imageUri);
        }

        saveObserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        imageObserImgV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePickerDialog();
            }
        });

    }

    private void showImagePickerDialog() {

        String options[] = {"Camera","Gallery"};

        AlertDialog.Builder builder  = new AlertDialog.Builder(this);

        builder.setTitle("Choose An Option");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (which == 0){
                    if (!checkCameraPermission()){
                        requestCameraPermission();
                    }else {
                        pickFromCamera();
                    }
                }else if (which == 1){
                    if (!checkStoragePermission()){
                        requestStoragePermission();
                    }else {
                        pickFromGallery();
                    }
                }
            }
        }).create().show();
    }

    private void pickFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*"); // only Image

        startActivityForResult(galleryIntent,IMAGE_FROM_GALLERY_CODE);
    }

    private void pickFromCamera() {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"IMAGE_TITLE");
        values.put(MediaStore.Images.Media.DESCRIPTION,"IMAGE_DETAIL");
        
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);

        startActivityForResult(cameraIntent,IMAGE_FROM_CAMERA_CODE);
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this,storagePermission,STORAGE_PERMISSION_CODE);
    }

    private boolean checkStoragePermission() {
        boolean result1 = ContextCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result1;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this,cameraPermission,CAMERA_PERMISSION_CODE);
    }

    private boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result & result1;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PERMISSION_CODE:
                    pickFromCamera();
                break;
            case STORAGE_PERMISSION_CODE:
                    pickFromGallery();
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            if (requestCode == IMAGE_FROM_GALLERY_CODE){

                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(AddEditObser.this);

            }else if (requestCode == IMAGE_FROM_CAMERA_CODE){

                CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(AddEditObser.this);
            }else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                imageUri = result.getUri();
                imageObserImgV.setImageURI(imageUri);

            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                toast("Something wrong");
            }
        }
    }

    private void saveData() {
        obserName = nameObserEdt.getText().toString();
        obserComment = commentObserEdt.getText().toString();

        if (!(obserName.isEmpty())){
            if(obserComment.equals("")){
                obserComment = "No Comment";
            }
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            obserTime = dtf.format(now);

            if(isEditObser){
                dbHelper.updateObser(
                        ""+obser_id,
                        ""+obserName,
                        ""+obserTime,
                        ""+obserComment,
                        ""+imageUri
                );

                toast("Updated Successfully "+obser_id);
                Intent intentUpdate = new Intent(this, MainActivity.class);
                startActivity(intentUpdate);
            } else {
                long id = dbHelper.insertObser(
                        ""+intent.getStringExtra("HIKE_ID"),
                        ""+obserName,
                        ""+obserTime,
                        ""+obserComment,
                        ""+imageUri
                );
                toast("Inserted Successfully: "+obserName);
            }
            Intent intentBack = new Intent(this, MainObservation.class);
            intentBack.putExtra("HIKE_ID", intent.getStringExtra("HIKE_ID"));
            startActivity(intentBack);
        }else {
            toast("Please enter observation name!");
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void toast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void map(){
        nameObserEdt = (EditText) findViewById(R.id.nameObserEdt);
        commentObserEdt = (EditText) findViewById(R.id.commentObserEdt);
        imageObserImgV = (ImageView) findViewById(R.id.imageObserImgV);
        saveObserBtn = (Button) findViewById(R.id.saveObserBtn);
        dbHelper = new DbHelper(this);
    }

}