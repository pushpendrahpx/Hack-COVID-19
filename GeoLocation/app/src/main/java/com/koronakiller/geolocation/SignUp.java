package com.koronakiller.geolocation;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {
    private static final String TAG = "SignUp";
    private ProgressBar progressBar;
    private EditText etName, etPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initUI();
    }

    private void initUI() {
        progressBar = findViewById(R.id.progressBar);
        etName = findViewById(R.id.et_user_name);
        etPassword = findViewById(R.id.et_password);
    }
}
