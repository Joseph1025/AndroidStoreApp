package com.example.storeapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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

public class CustomerOrderMainActivity extends AppCompatActivity implements SetUserInfo, SetAdapter{
    private ArrayList<String> orders;
    private RecyclerView recyclerView;
    private CustomerOrderRecyclerAdapter adapter;
    private TextView text;
    private CustomerOrderRecyclerAdapter.RecyclerViewClickListener listener;
    private String storename;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordersmain);
        orders = new ArrayList<>();
        recyclerView = findViewById(R.id.ordersrecyclerview);
        username = getIntent().getStringExtra("username");
        setUserInfo();

    }
    public void setAdapter() {
        setOnClickListener();
        this.adapter = new CustomerOrderRecyclerAdapter(orders,listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setOnClickListener() {
        listener = new CustomerOrderRecyclerAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), CustomerSingleOrderMainActivity.class);
                intent.putExtra("username",username);
                intent.putExtra("ordername",orders.get(position));
                startActivity(intent);
            }
        };
    }

    public void setUserInfo() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Customer");
        ref = ref.child(username).child("orders");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                orders = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String str = child.getKey();
                    boolean status = false;
                    for(DataSnapshot temp : child.getChildren()){
                        if(temp.getKey().equals("status")){
                            if(temp.getValue(Boolean.class).equals(true))
                                status = true;
                        }
                    }
                    if(!status) str = str.concat(": is not completed");
                    if(status) str = str.concat(": is completed");
                    orders.add(str);
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

