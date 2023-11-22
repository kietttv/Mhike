package com.example.m_hike;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class LoginTabFragment extends Fragment implements Serializable {
    private EditText login_email;
    private EditText login_password;
    private Button login_button;
    private  DbHelper dbHelper;
    private ImageView showHidePass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_tab, container, false);
        showHidePass = view.findViewById(R.id.showHidePass);
        login_email = view.findViewById(R.id.login_email);
        login_password = view.findViewById(R.id.login_password);
        login_button = view.findViewById(R.id.login_button);

        showHidePass.setImageResource(R.drawable.ic_offeyes);

        showHidePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(login_password.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    login_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showHidePass.setImageResource(R.drawable.ic_offeyes);
                } else {
                    login_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showHidePass.setImageResource(R.drawable.baseline_remove_red_eye_24);
                }
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = login_email.getText().toString();
                email = email.replaceAll(" ", "");
                String password = login_password.getText().toString();
                password = password.replaceAll(" ", "");

                dbHelper = new DbHelper(getContext());
                if(dbHelper.checkEmail(email)){
                    try {
                        String decryPass = EncryptDecrypy.decrypt(dbHelper.checkPass(email));
                        if(decryPass.equals(password)){
                            Toast.makeText(getContext(), "Login successful", Toast.LENGTH_SHORT).show();
                            ModelUser user = dbHelper.getUserByEmail(email);
                            Intent intentAdapter = new Intent(getContext(), AdapterHike.class);
                            intentAdapter.putExtra("userID", user.getUser_id());
                            login_password.setText("");
                            Intent animation = new Intent(getContext(), Splash_Screen.class);
                            animation.putExtra("userID", user.getUser_id());
                            animation.putExtra("userEmail", user.getUser_email());
                            startActivity(animation);
                        }else {
                            Toast.makeText(getContext(), "Login failed", Toast.LENGTH_SHORT).show();
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
                }else {
                    Toast.makeText(getContext(), "Login failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}