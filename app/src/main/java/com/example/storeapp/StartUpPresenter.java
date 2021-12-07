package com.example.storeapp;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StartUpPresenter {
    private View view;
    private User user;

    public StartUpPresenter(View view) {
        this.user = null;
        this.view = view;
    }

    public void getUser(String name, String password, String userType){
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
        User new_user = this.user;
        reference.addListenerForSingleValueEvent(
                new ValueEventListener(){
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (!snapshot.hasChild(name)) {
                            // The child doesn't exist
                            Log.v(name, "you don't exist yet");
                            reference.child(name).setValue(new_user);
                        }else{
                            Log.v(name, "u exist!!!");
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    public void userLogin(String name, String password, String userType){
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference(userType);
        Log.v(name, "hello!!!");

        reference.addListenerForSingleValueEvent(
            new ValueEventListener(){
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (!snapshot.hasChild(name)) {
                        // The child doesn't exist
                        Log.v(name, "you don't exist");
                    }else{
                        if(snapshot.child(name).child("password").getValue().toString().equals(password)){
                            Log.v(name, "logged in success");
                            if(userType.equals("Customer")){
                                view.navigateToCusPage();
                            }else if(userType.equals("Store Owner")){
                                view.navigateToStrPage();
                            }
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
    }
    public interface View {
        void navigateToCusPage();
        void navigateToStrPage();
    }
}
