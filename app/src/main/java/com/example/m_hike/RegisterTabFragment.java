package com.example.m_hike;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class RegisterTabFragment extends Fragment {
    EditText register_email;
    EditText register_name;
    EditText register_pass;
    EditText register_comf_pass;
    RadioGroup radioGroupGender;
    RadioButton radioMale;
    RadioButton radioFemale;
    TextView textViewDob;
    Button signup_button;
    ImageView showHidePass, showHideConfirPass;
    private String imageUri = "android.resource://com.example.m_hike/drawable/user";

    private DbHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_regitser_tab, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbHelper = new DbHelper(getContext());

        Button button = (Button) view.findViewById(R.id.btn_Dob);
        TextView textView = view.findViewById(R.id.txt_Dob);

        register_email = view.findViewById(R.id.register_email);
        register_name = view.findViewById(R.id.register_name);
        register_pass = view.findViewById(R.id.register_pass);
        register_comf_pass = view.findViewById(R.id.register_comf_pass);
        radioGroupGender = view.findViewById(R.id.radioGroupGender);
        radioMale = view.findViewById(R.id.radioMale);
        radioFemale = view.findViewById(R.id.radioFemale);
        signup_button = view.findViewById(R.id.signup_button);
        textViewDob = view.findViewById(R.id.txt_Dob);
        showHidePass = view.findViewById(R.id.showHidePass);
        showHideConfirPass = view.findViewById(R.id.showHideConfirPass);

        showHidePass.setImageResource(R.drawable.ic_offeyes);
        showHideConfirPass.setImageResource(R.drawable.ic_offeyes);

        showHidePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(register_pass.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    register_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showHidePass.setImageResource(R.drawable.ic_offeyes);
                } else {
                    register_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showHidePass.setImageResource(R.drawable.baseline_remove_red_eye_24);
                }
            }
        });

        showHideConfirPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(register_comf_pass.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    register_comf_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showHideConfirPass.setImageResource(R.drawable.ic_offeyes);
                } else {
                    register_comf_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showHideConfirPass.setImageResource(R.drawable.baseline_remove_red_eye_24);
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                textView.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });
        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = register_email.getText().toString();
                email = email.replaceAll(" ", "");
                String name = register_name.getText().toString();
                name = name.trim();
                String password = register_pass.getText().toString();
                password = password.replaceAll(" ", "");
                String confirmPassword = register_comf_pass.getText().toString();
                confirmPassword = confirmPassword.replaceAll(" ", "");
                String gender = radioFemale.isChecked() ? "Female" : "Male";
                String dob = textViewDob.getText().toString();

                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)
                        && !TextUtils.isEmpty(confirmPassword) && !TextUtils.isEmpty(gender) && !TextUtils.isEmpty(dob)) {
                    if(isEmailValid(email)){
                        if(!dbHelper.checkEmail(email)){
                            if(isPasswordValid(password)){
                                if (password.equals(confirmPassword)) {
                                    String encrypytPassword = "";
                                    try {
                                        encrypytPassword = EncryptDecrypy.encrypt(password);
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
                                    long result = dbHelper.insertUser(email, name, dob, gender, encrypytPassword, imageUri);
                                    if (result != -1) {
                                        Intent intent = new Intent(getContext(), LoginAndRegister.class);
                                        startActivity(intent);
                                        Toast.makeText(getContext(), "Registration successful", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getContext(), "Registration failed", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getContext(), "Password do not match", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getContext(), "The password is least 8 characters, least 1 uppercase, 1 lowercase letter", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "Email already exists. Please enter another email", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Please enter the correct email format", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
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
    private boolean isEmailValid(String email){
        String EmailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(EmailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}