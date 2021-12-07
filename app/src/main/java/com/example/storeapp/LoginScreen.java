package com.example.storeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LoginScreen extends AppCompatActivity implements StartUpPresenter.View {
    Button btnCustomer, btnStoreOwner, signUp;
    EditText logName, logPassword;

    StartUpPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        btnCustomer = (Button)findViewById(R.id.onCusLogin);
        btnStoreOwner = (Button)findViewById(R.id.onStrLogin);
        signUp = (Button)findViewById(R.id.onSignUp);
        logName = (EditText)findViewById(R.id.name_input);
        logPassword = (EditText)findViewById(R.id.passward_input);
        presenter = new StartUpPresenter(this);

        btnCustomer.setOnClickListener(
            new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String name = logName.getText().toString();
                String password = logPassword.getText().toString();
                presenter.getUser(name, password, "Customer");
                presenter.userLogin(name, password, "Customer");
        }
        });
        //presenter = new StartUpPresenter(this);
        btnStoreOwner.setOnClickListener(
            new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    String name = logName.getText().toString();
                    String password = logPassword.getText().toString();
                    presenter.getUser(name, password, "Store Owner");
                    presenter.userLogin(name, password, "Store Owner");
                }
            }
        );


        signUp.setOnClickListener(
            new View.OnClickListener(){
                public void onClick(View view){
                    Intent signUpIntent = new Intent(LoginScreen.this, SignUpScreen.class);
                    startActivity(signUpIntent);
                }
            }
        );
    }

    public String getUserName(){
        logName = (EditText)findViewById(R.id.name_input);
        return logName.getText().toString();
    }

    @Override
    public void navigateToCusPage() {
        //change customer_page to navigate to the page u want
        Intent cusPage = new Intent(LoginScreen.this, CustomerMainActivity.class);
        cusPage.putExtra("username", getUserName());
        startActivity(cusPage);
    }

    @Override
    public void navigateToStrPage() {
        //change storeOwner_page to navigate to the page u want
        Intent strPage = new Intent(LoginScreen.this, StoreOwnerMainActivity.class);
        strPage.putExtra("username", getUserName());
        startActivity(strPage);
    }

    @Override
    public void onBackPressed() {
    }
}