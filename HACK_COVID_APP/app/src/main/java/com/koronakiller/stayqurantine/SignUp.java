package com.koronakiller.stayqurantine;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.koronakiller.stayqurantine.models.User;
import com.koronakiller.stayqurantine.utils.HttpHelper;
import com.koronakiller.stayqurantine.utils.RequestPackage;

import java.io.IOException;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "SignUp";
    private ProgressBar progressBar;
    private EditText etName, etPassword, etEmail, etPhone;
    private String userName, password, emailId, phoneNo;
    private Handler handler;
    private String REGISTER_BASE_URL = "https://uhdcnkdf4.herokuapp.com/api/users/register/android";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: started in UI thread" + Thread.currentThread().getId());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initUI();
        handler = new Handler(getMainLooper());
        findViewById(R.id.tvAlreadyUser).setOnClickListener(this);
        findViewById(R.id.bSingup).setOnClickListener(this);
    }

    private void initUI() {
        progressBar = findViewById(R.id.progressBar);
        etName = findViewById(R.id.et_user_name);
        etPassword = findViewById(R.id.et_password);
        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_phone_no);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bSingup:
                signingUser();
                break;
            case R.id.tvAlreadyUser:
                Intent i = new Intent(SignUp.this, LogIn.class);
                startActivity(i);
                break;
        }
    }

    private void signingUser() {
        getText();

        if (userName == null || userName.length() == 0) {
            showError(etName, "Please Enter Name");
            return;
        }
        if (userName.length() < 3) {
            showError(etName, "Name is too short!!");
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
        if (emailId == null || emailId.length() == 0) {
            showError(etEmail, "Please enter email id");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailId).matches()) {
            showError(etEmail, "Please enter valid Email id");
            return;
        }
        if (phoneNo == null || phoneNo.length() == 0) {
            showError(etPhone, "Please enter your phone no!");
            return;
        }
        if (phoneNo.length() < 10) {
            showError(etPhone, "Please enter valid phone no!");
            return;
        }
        Log.d(TAG, "signingUser: all text are checked");
        progressBar.setVisibility(View.VISIBLE);
        //TODO : Check internet conn.
        // FIXME
        Thread thread = new Thread() {

            @Override
            public void run() {
                Log.d(TAG, "run: started a new Thread " + Thread.currentThread().getId());
                User user = User.getUser(userName, password, phoneNo, emailId);
                RequestPackage requestPackage = new RequestPackage();
                requestPackage.setEndPoint(REGISTER_BASE_URL);
                requestPackage.setMethod("POST");
                requestPackage.setParams(HttpHelper.KEY_USER_PARAMS, user);
                try {
                    String data = HttpHelper.getJsonData(requestPackage);
                    Log.d(TAG, "run: data is " + data);
                    Intent i = new Intent(SignUp.this, HomeActivity.class);
                    i.putExtra(LogIn.KEY_USER, user);
                    startActivity(i);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });

            }
        };
        thread.start();
    }

    private void getText() {
        userName = getTextFromEditText(etName);
        password = getTextFromEditText(etPassword);
        emailId = getTextFromEditText(etEmail);
        phoneNo = getTextFromEditText(etPhone);
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
