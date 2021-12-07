package com.example.storeapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class CustomerMainActivity extends AppCompatActivity {
    public String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customermain);
        Bundle bundle = getIntent().getExtras();
        userName = bundle.getString("username");
        Log.v("userName is", userName);

    }

    public void click_store(View v){
        Intent intent = new Intent(this, StoreMainActivity.class);
        intent.putExtra("username",userName);
        startActivity(intent);
    }

    public void click_order(View v){
        Intent intent = new Intent(this, CustomerOrderMainActivity.class);
        intent.putExtra("username",userName);
        startActivity(intent);
    }

    public void click_logout(View v){
        Intent intent = new Intent(this, LoginScreen.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
    }
}
