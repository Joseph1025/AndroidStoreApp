package com.example.storeapp;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginModel {
    boolean userExist;

    LoginModel(){
        userExist = false;
    }
    public void userLogin(String name, String password, String userType, LoginPresenter p){
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference(userType);

        reference.addListenerForSingleValueEvent(
            new ValueEventListener(){
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (!snapshot.hasChild(name)) {
                        // The child doesn't exist
                        LoginPresenter.loginFailed(p.view);
                    }else{
                        if(snapshot.child(name).child("password").getValue().toString().equals(password)){
                            Log.v(name, "logged in success");
                            LoginPresenter.loginSuccess(userType, p.view);
                        }else{
                            LoginPresenter.loginFailed(p.view);
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
    }
}
