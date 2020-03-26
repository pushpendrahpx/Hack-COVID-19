package com.koronakiller.stayqurantine;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.koronakiller.stayqurantine.models.User;
import com.koronakiller.stayqurantine.utils.HttpHelper;
import com.koronakiller.stayqurantine.utils.RequestPackage;

import java.io.IOException;

public class LogIn extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LogIn";
    private static final String LOGIN_PAYLOAD = "LOGIN_PAYLOAD";
    private ProgressBar progressBar;
    private EditText etPhone, etPassword;

    private String password, phoneNo;
    private String LOGIN_ENDPOINT = "https://uhdcnkdf4.herokuapp.com/api/users/login/android"; //TODO add here

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
        etPhone = findViewById(R.id.et_user_name);
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
        getText();

        if (phoneNo == null || phoneNo.length() == 0) {
            showError(etPhone, "Please enter your phone no!");
            return;
        }
        if (phoneNo.length() < 10) {
            showError(etPhone, "Please enter valid phone no!");
            return;
        }
        if (password == null || password.length() == 0) {
            showError(etPassword, "Please enter password");
            return;
        }
        if (password.length() < 6) {
            showError(etPassword, "Password is too short!!");
            return;
        }

        Log.d(TAG, "logInUser: all text check");
        progressBar.setVisibility(View.VISIBLE);
        //TODO : Check internet conn.
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: thread id " + Thread.currentThread().getId());
                RequestPackage requestPackage = new RequestPackage();
                requestPackage.setEndPoint(LOGIN_ENDPOINT);
                requestPackage.setMethod("POST");
                User user = new User(phoneNo, password);
                user.setName(phoneNo);
                requestPackage.setParams(HttpHelper.KEY_USER_PARAMS, user);
                try {
                    String data = HttpHelper.getJsonData(requestPackage);
                    Log.d(TAG, "run: data "+data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void getText() {
        phoneNo = getTextFromEditText(etPhone);
        password = getTextFromEditText(etPassword);
    }

    private void showError(EditText editText, String error) {
        editText.setError(error);
        editText.requestFocus();
    }

    private String getTextFromEditText(EditText editText) {
        Log.d(TAG, "getTextFromEditText: text = " + editText.getText().toString().trim());
        return editText.getText().toString().trim();
    }
}
