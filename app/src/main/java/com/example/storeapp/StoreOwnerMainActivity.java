package com.example.storeapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class StoreOwnerMainActivity extends AppCompatActivity {
    /**
     * This is the page that contain only two button: my product and
     * my store.
     */

    public String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userName = getIntent().getStringExtra("username");
        setContentView(R.layout.activity_storeownermain);
    }

    public void clickmyproducts(View v){
        Intent intent = new Intent(this, MyproductMainActivity.class);
        intent.putExtra("username",userName);
        startActivity(intent);
    }

    public void clickmyorders(View v){
        Intent intent = new Intent(this, MyorderMainActivity.class);
        intent.putExtra("username",userName);
        startActivity(intent);

    }

    public void clicklogout(View V){
        Intent intent = new Intent(this,LoginScreen.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed(){

    }

}
