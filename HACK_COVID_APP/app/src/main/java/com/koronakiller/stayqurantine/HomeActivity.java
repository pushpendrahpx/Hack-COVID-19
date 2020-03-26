package com.koronakiller.stayqurantine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.koronakiller.stayqurantine.models.User;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void logOut (View view)
    {
        new User(HomeActivity.this).removeUser();
        Intent intent = new Intent(HomeActivity.this,LogIn.class);
        startActivity(intent);
        finish();
    }
}
