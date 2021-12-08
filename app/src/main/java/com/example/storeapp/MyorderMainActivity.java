package com.example.storeapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

    public class MyorderMainActivity extends AppCompatActivity implements RecyclerAdapterForm {
        private ArrayList<String> orders;
        private ArrayList<String> cusNames;
        private ArrayList<String> orderIds;
        private RecyclerView recyclerView;
        private MyorderRecyclerAdapter adapter;
        private MyorderRecyclerAdapter.RecyclerViewClickListener1 listener;
        private String storename;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_myordersmain);
            orders = new ArrayList<>();
            cusNames = new ArrayList<>();
            orderIds = new ArrayList<>();
            recyclerView = findViewById(R.id.storeowner_orders_recyclerview);
            storename = getIntent().getStringExtra("username");
            setUserInfo();
            setAdapter();



        }
        public void setAdapter() {
            setOnClickListener();
            this.adapter = new MyorderRecyclerAdapter(orders,listener);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
        }

        private void setOnClickListener() {
            listener = new MyorderRecyclerAdapter.RecyclerViewClickListener1() {
                @Override
                public void onClick(View v, int position) {

                    //storename
                    Intent intent = new Intent(getApplicationContext(), OrderdetailMainActivity.class);

                    intent.putExtra("customer_name",cusNames.get(position));
                    intent.putExtra("STORE_NAME", storename);
                    intent.putExtra("ORDER_ID", orderIds.get(position));

                    startActivity(intent);
                }
            };

        }

        public void setUserInfo() {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Store Owner");
            DatabaseReference subref = ref.child(storename).child("orders");
            subref.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot child: snapshot.getChildren()) { // traversing at order-key level
                        orderIds.add(child.getKey());
                        for (DataSnapshot grandChild: child.getChildren()) { // at certain order
                            boolean isComplete = false;
                            if (grandChild.getKey().equals("status")) {
                                if (grandChild.getValue(Boolean.class)) {
                                    isComplete = true;
                                }
                            }
                            if (grandChild.getKey().equals("customer_order")) {
                                cusNames.add(grandChild.getValue(String.class));
                                orders.add("Order from " + grandChild.getValue(String.class));
                            }

                            if (isComplete) {
                                String temp = orders.remove(orders.size() - 1);  // pop out the string newly added
                                temp = temp.concat(": is complete");
                                orders.add(temp);
                            }

                        }
                    }
                    setAdapter();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        @Override
        public void onBackPressed() {
            Intent intent = new Intent(MyorderMainActivity.this, StoreOwnerMainActivity.class);
            intent.putExtra("username", storename);
            startActivity(intent);
        }




    }


