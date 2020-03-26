package com.koronakiller.stayqurantine;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.koronakiller.stayqurantine.utils.NetworkHelper;
import com.koronakiller.stayqurantine.models.User;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler handler = new Handler(getMainLooper());
        final User user = new User(SplashActivity.this);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                if (NetworkHelper.isConnected(getApplicationContext())) {
//                    Intent i = new Intent(SplashActivity.this, SignUp.class);
//                    startActivity(i);
//                } else
//                    Snackbar.make(findViewById(R.id.parent_view), "Turn data on", Snackbar.LENGTH_INDEFINITE).show();

                if(user.GetName().equals("")) {
                    Intent i = new Intent(SplashActivity.this, SignUp.class);
                    i.putExtra("name",user.GetName());
                    startActivity(i);
                }
                else {
                    Intent i = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(i);
                }
            }
        }, 2000);
    }
}
