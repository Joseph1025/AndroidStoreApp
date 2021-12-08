package com.example.storeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpScreen extends AppCompatActivity implements SignUpPresenter.View{
    EditText regName, regPassword;
    Button btnCustomer, btnStoreOwner, btnBack;

    SignUpPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);

        btnCustomer = findViewById(R.id.btn_customer);
        btnStoreOwner = findViewById(R.id.btn_store_owner);
        btnBack = findViewById(R.id.btn_back);
        presenter = new SignUpPresenter(this);

        btnCustomer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                presenter.getUser("Customer");
                presenter.addUser("Customer");
            }
        });

        btnStoreOwner.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                presenter.getUser("Store Owner");
                presenter.addUser("Store Owner");
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                navigateToLogin();
            }
        });
    }

    @Override
    public String getUserName() {
        regName = findViewById(R.id.reg_name);
        return regName.getText().toString();
    }

    @Override
    public String getPassWord() {
        regPassword = findViewById(R.id.reg_password);
        return regPassword.getText().toString();
    }

    @Override
    public void displayMessage(String s) {
        TextView hint = (TextView)findViewById(R.id.login_hint_text);
        hint.setText(s);
    }

    @Override
    public void navigateToLogin() {
        Intent logPage = new Intent(SignUpScreen.this, LoginScreen.class);
        startActivity(logPage);
    }

}