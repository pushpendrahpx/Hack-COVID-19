package com.koronakiller.stayqurantine;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.koronakiller.stayqurantine.utils.NetworkHelper;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler handler = new Handler(getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (NetworkHelper.isConnected(getApplicationContext())) {
                    Intent i = new Intent(SplashActivity.this, SignUp.class);
                    startActivity(i);
                } else
                    Snackbar.make(findViewById(R.id.parent_view), "Turn data on", Snackbar.LENGTH_INDEFINITE).show();
            }
        }, 2000);
    }
}
