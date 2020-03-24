package com.koronakiller.geolocation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class LogIn extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LogIn";
    private ProgressBar progressBar;
    private EditText etName, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        initUI();
        findViewById(R.id.bLogIn).setOnClickListener(this);
        findViewById(R.id.tv_not_user).setOnClickListener(this);
    }

    private void initUI() {
        progressBar = findViewById(R.id.progressBar);
        etName = findViewById(R.id.et_user_name);
        etPassword = findViewById(R.id.et_password);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bLogIn:
                logInUser();
                break;
            case R.id.tv_not_user:
                Intent i = new Intent(LogIn.this, SignUp.class);
                startActivity(i);
                break;
        }
    }

    private void logInUser() {
        verifyTexts();
        progressBar.setVisibility(View.VISIBLE);
    }

    private void verifyTexts() {
    }
}
