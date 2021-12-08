package com.example.storeapp;

import android.util.Log;

import com.example.storeapp.Customer;
import com.example.storeapp.LoginPresenter;
import com.example.storeapp.StoreOwner;
import com.example.storeapp.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class SignUpPresenter {
    private SignUpPresenter.View view;
    private User user;

    public SignUpPresenter(SignUpPresenter.View view) {
        this.user = null;
        this.view = view;
    }

    public void getUser(String userType){
        String name = view.getUserName();
        String password = view.getPassWord();
        if(userType == "Customer"){
            this.user = new Customer(name, password);
        }else if(userType == "Store Owner"){
            this.user = new StoreOwner(name, password);
        }
    }

    public void addUser(String userType){
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference(userType);

        String name = this.user.getName();
        String password = this.user.getPassword();
        User new_user = this.user;

        if(name.equals("") || password.equals("")){
            view.displayMessage("user name/ password cannot be empty");
        }else{
            reference.addListenerForSingleValueEvent(
                new ValueEventListener(){
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (!snapshot.hasChild(name)) {
                            Log.v(name, "you don't exist yet");
                            reference.child(name).setValue(new_user);
                            view.displayMessage("registered successfully");
                        }else{
                            Log.v(name, "u exist!!!");
                            view.displayMessage("username already taken");
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                    });
        }
    }

    public interface View {
        String getUserName();
        String getPassWord();
        void displayMessage(String s);
        void navigateToLogin();
    }
}
