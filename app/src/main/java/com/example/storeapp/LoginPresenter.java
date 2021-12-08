package com.example.storeapp;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginPresenter {
    public View view;
    public LoginModel model;

    public LoginPresenter(View view, LoginModel model) {
        this.view = view;
        this.model = model;
    }

    public void userLogin(String userType){
        String name = view.getUserName();
        String password = view.getPassWord();

        if(name.equals("") || password.equals("")){
            view.displayMessage("username/password cannot be empty");
        }else{
            model.userLogin(name, password, userType, this.view);
        }
    }

    public static void loginSuccess(String userType, View view){
        if(userType.equals("Customer")){
            view.navigateToCusPage();
        }else if(userType.equals("Store Owner")){
            view.navigateToStrPage();
        }
    }
    public static void loginFailed(View view){
        view.displayMessage("wrong username/password, please try again");
    }

    public interface View {
        String getUserName();
        String getPassWord();
        void navigateToCusPage();
        void navigateToStrPage();
        void displayMessage(String s);
    }
}
