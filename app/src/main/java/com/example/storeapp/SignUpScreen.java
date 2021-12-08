package com.example.storeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpScreen extends AppCompatActivity implements StartUpPresenter.View{
    EditText regName, regPassword;
    Button btnCustomer, btnStoreOwner;

    StartUpPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);

        regName = findViewById(R.id.reg_name);
        regPassword = findViewById(R.id.reg_password);
        btnCustomer = findViewById(R.id.btn_customer);
        btnStoreOwner = findViewById(R.id.btn_store_owner);
        presenter = new StartUpPresenter(this);

        btnCustomer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String name = regName.getText().toString();
                String password = regPassword.getText().toString();

                presenter.getUser(name, password, "Customer");
                presenter.addUser("Customer");
                Intent back = new Intent(SignUpScreen.this, LoginScreen.class);
                startActivity(back);
            }
        });

        btnStoreOwner.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String name = regName.getText().toString();
                String password = regPassword.getText().toString();

                presenter.getUser(name, password, "Store Owner");
                presenter.addUser("Store Owner");
                Intent back = new Intent(SignUpScreen.this, LoginScreen.class);
                startActivity(back);
            }
        });
    }

//    public String getUserName(){
//        logName = (EditText)findViewById(R.id.name_input);
//        return logName.getText().toString();
//    }

    @Override
    public void navigateToCusPage() {
//        Intent cusPage = new Intent(SignUpScreen.this, CustomerMainActivity.class);
//        cusPage.putExtra("username", getUserName());
//        startActivity(cusPage);
    }

    @Override
    public void navigateToStrPage() {

    }
}