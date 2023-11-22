package com.example.m_hike;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class ChangePassword extends AppCompatActivity {
    private ActionBar actionBar;
    EditText curPas, newPas, confirmPas;
    String curPassword, newPassword, confirmPassword;
    Button saveButton;
    ModelUser user;
    ImageView showHideCurPass, showHideNewPass, showHideConfirPass;
    DbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Change Password");
        map();
        user = dbHelper.getUserByEmail(MainActivity.userEmail);

        showHideCurPass.setImageResource(R.drawable.ic_offeyes);
        showHideNewPass.setImageResource(R.drawable.ic_offeyes);
        showHideConfirPass.setImageResource(R.drawable.ic_offeyes);

        showHideCurPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(curPas.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    curPas.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showHideCurPass.setImageResource(R.drawable.ic_offeyes);
                } else {
                    curPas.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showHideCurPass.setImageResource(R.drawable.baseline_remove_red_eye_24);
                }
            }
        });

        showHideNewPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newPas.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    newPas.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showHideNewPass.setImageResource(R.drawable.ic_offeyes);
                } else {
                    newPas.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showHideNewPass.setImageResource(R.drawable.baseline_remove_red_eye_24);
                }
            }
        });

        showHideConfirPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(confirmPas.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    confirmPas.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showHideConfirPass.setImageResource(R.drawable.ic_offeyes);
                } else {
                    confirmPas.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showHideConfirPass.setImageResource(R.drawable.baseline_remove_red_eye_24);
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curPassword = curPas.getText().toString();
                curPassword = curPassword.replaceAll(" ", "");
                newPassword = newPas.getText().toString();
                newPassword = newPassword.replaceAll(" ", "");
                confirmPassword = confirmPas.getText().toString();
                confirmPassword = confirmPassword.replaceAll(" ", "");
                if(curPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()){
                    Toast.makeText(ChangePassword.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        String decodePas = EncryptDecrypy.decrypt(dbHelper.checkPass(user.getUser_email()));
                        if(decodePas.equals(curPassword)){
                            if (isPasswordValid(newPassword)) {
                                if(newPassword.equals(confirmPassword)){
                                    String encode = EncryptDecrypy.encrypt(newPassword);
                                    dbHelper.updateUser(user.getUser_id(), user.getUser_name(), user.getUser_email(), user.getUser_dayOfBirth(),
                                            user.getUser_gender(), encode, user.getUser_image());
                                    Toast.makeText(ChangePassword.this, "Change Password Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ChangePassword.this, Profile.class);
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(ChangePassword.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(ChangePassword.this, "The password is least 8 characters, least 1 uppercase, 1 lowercase letter", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(ChangePassword.this, "Please enter correct current password", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NoSuchPaddingException e) {
                        throw new RuntimeException(e);
                    } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    } catch (InvalidAlgorithmParameterException e) {
                        throw new RuntimeException(e);
                    } catch (InvalidKeyException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalBlockSizeException e) {
                        throw new RuntimeException(e);
                    } catch (BadPaddingException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

    }

    private boolean isPasswordValid(String password) {
        if (password.length() < 8) {
            return false;
        }
        boolean hasUppercase = false;
        boolean hasLowercase = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUppercase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowercase = true;
            }
        }
        return hasUppercase && hasLowercase;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void map(){
        curPas = findViewById(R.id.curPas);
        newPas = findViewById(R.id.newPas);
        confirmPas = findViewById(R.id.confirmPas);
        dbHelper = new DbHelper(this);
        saveButton = findViewById(R.id.saveButton);
        showHideCurPass = findViewById(R.id.showHideCurPass);
        showHideNewPass = findViewById(R.id.showHideNewPass);
        showHideConfirPass = findViewById(R.id.showHideConfirPass);
    }
}