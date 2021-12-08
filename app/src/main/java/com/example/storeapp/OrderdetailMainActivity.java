package com.example.storeapp;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

public class OrderdetailMainActivity extends AppCompatActivity implements SetAdapter, SetUserInfo{
    private ArrayList<Product> products;
    private String customername;
    private String storename;
    private String orderId;
    private RecyclerView recyclerView;
    private OrderdetailRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        products = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetailmain);
        recyclerView = findViewById(R.id.storeowner_orderproduct_recyclerview);
        Intent intent = getIntent();
        customername = intent.getStringExtra("customer_name");
        storename = intent.getStringExtra("STORE_NAME");
        orderId = intent.getStringExtra("ORDER_ID");

        //I need an order based on intent customername and storename;
        setUserInfo();
        setAdapter();


    }
    public void setAdapter() {
        this.adapter = new OrderdetailRecyclerAdapter(products);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public void storeowner_clickback(View v){
        Intent intent = new Intent(this, StoreOwnerMainActivity.class);
        intent.putExtra("username", storename);
        startActivity(intent);
    }

    public void clickfinished(View v){

        setStatus(orderId);

        Intent intent = new Intent(this, MyorderMainActivity.class);
        intent.putExtra("username", storename);
        startActivity(intent);
    }

    public void setStatus(String orderId) {
        /**
         * Update order of id <orderId></orderId>.
         * Set its status from false to true.
         */
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Store Owner");
        ref = ref.child(storename).child("orders").child(orderId);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child: snapshot.getChildren()) {
                    if (child.getKey().equals("status")) {
                        child.getRef().setValue(true);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("Customer");
        ref2 = ref2.child(customername).child("orders").child(orderId);
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child: snapshot.getChildren()) {
                    if (child.getKey().equals("status")) {
                        child.getRef().setValue(true);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void setUserInfo(){
        /**
         * Update </order> with all the orders of the store.
         */
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Store Owner");
        ref = ref.child(storename).child("orders").child(orderId);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child: snapshot.getChildren()) { // traversing target order
                    if (child.getKey().equals("products")) {
                        products.addAll(prodWrapper(child));
                        break;
                    }
                }

                setAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public ArrayList<Product> prodWrapper(DataSnapshot snapshot) {
        /**
         * Snapshot is the path that contains all the products of the order.
         * Return an ArryList with elements of class product.
         */

        String nm = "";
        String des = "";
        int count = 0;
        int price = 0;
        ArrayList<Product> pList = new ArrayList<>();
        for (DataSnapshot prodKy: snapshot.getChildren()) {
            for (DataSnapshot snap : prodKy.getChildren()) {
                if (snap.getKey().equals("productName")) {
                    nm = snap.getValue(String.class);
                } else if (snap.getKey().equals("description")) {
                    des = snap.getValue(String.class);
                } else if (snap.getKey().equals("price")) {
                    price = snap.getValue(Integer.class);
                } else if (snap.getKey().equals("productCount")) {
                    count = snap.getValue(Integer.class);
                }
            }
            pList.add(new Product(nm, des, price, count));
        }

        return pList;

    }



}



