package com.example.storeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginScreen extends AppCompatActivity implements LoginPresenter.View {
    Button btnCustomer, btnStoreOwner, signUp;
    EditText logName, logPassword;

    LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        btnCustomer = (Button)findViewById(R.id.onCusLogin);
        btnStoreOwner = (Button)findViewById(R.id.onStrLogin);
        signUp = (Button)findViewById(R.id.onSignUp);
        presenter = new LoginPresenter(this);

        btnCustomer.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        presenter.getUser("Customer");
                        presenter.userLogin("Customer");
                    }
                });
        btnStoreOwner.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        presenter.getUser("Store Owner");
                        presenter.userLogin("Store Owner");
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

    @Override
    public String getUserName(){
        logName = (EditText)findViewById(R.id.name_input);
        return logName.getText().toString();
    }

    @Override
    public String getPassWord() {
        logPassword = (EditText)findViewById(R.id.passward_input);
        return logPassword.getText().toString();
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
    public void showEmptyText() {
        TextView hint = (TextView)findViewById(R.id.hint_text);
        hint.setText("user name/ password cannot be empty!");
    }

    @Override
    public void SignUpSuccess() {

    }

    @Override
    public void onBackPressed() {
    }
}