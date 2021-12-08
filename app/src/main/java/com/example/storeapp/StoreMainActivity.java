package com.example.storeapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StoreMainActivity extends AppCompatActivity implements RecyclerAdapterForm {

    private ArrayList<String> storesList;
    private RecyclerView recyclerView;
    private StoreRecyclerAdapter.RecyclerViewClickListener listener;
    private String storename;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storemain);
        recyclerView = findViewById(R.id.store_recyclerview);
        storesList = new ArrayList<>();
        username = getIntent().getStringExtra("username");
        setUserInfo();
    }

    public void setAdapter() {
        setOnClickListener();
        StoreRecyclerAdapter adapter = new StoreRecyclerAdapter(storesList,listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setOnClickListener() {
        listener = new StoreRecyclerAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), ProductMainActivity.class);
                storename = storesList.get(position);
                intent.putExtra("storename",storename);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        };
    }


    public void setUserInfo() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Store Owner");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                storesList = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String str = child.getKey();
                    storesList.add(str);
                }
                setAdapter();
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.i("demo_2", "Failed to read value.", error.toException());
            }
        };
        ref.addValueEventListener(listener);
    }
}
