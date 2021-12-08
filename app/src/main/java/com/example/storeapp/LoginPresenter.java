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
    private View view;

    public LoginPresenter(View view) {
        this.view = view;
    }

    public void userLogin(String userType){
        String name = view.getUserName();
        String password = view.getPassWord();

        if(name.equals("") || password.equals("")){
            view.displayMessage("user name/ password cannot be empty");
        }else{
            FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
            DatabaseReference reference = rootNode.getReference(userType);

            reference.addListenerForSingleValueEvent(
                new ValueEventListener(){
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (!snapshot.hasChild(name)) {
                            // The child doesn't exist
                            Log.v(name, "you don't exist");
                            view.displayMessage("wrong user name, please try again!");
                        }else{
                            if(snapshot.child(name).child("password").getValue().toString().equals(password)){
                                Log.v(name, "logged in success");
                                if(userType.equals("Customer")){
                                    view.navigateToCusPage();
                                }else if(userType.equals("Store Owner")){
                                    view.navigateToStrPage();
                                }
                            }else{
                                view.displayMessage("wrong password, please try again!");
                            }
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
        void navigateToCusPage();
        void navigateToStrPage();
        void displayMessage(String s);
    }
}
