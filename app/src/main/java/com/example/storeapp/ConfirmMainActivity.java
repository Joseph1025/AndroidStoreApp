package com.example.storeapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ConfirmMainActivity extends AppCompatActivity {
    private ArrayList<Product> order;
    private RecyclerView recyclerView;
    private ConfirmRecyclerAdapter adapter;
    private String storeName;
    private TextView totalPrice;
    private String username;
    private int id;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmmain);
        recyclerView = findViewById(R.id.confirmrecyclerview);
        order = ProductMainActivity.products;
        text = (TextView)findViewById(R.id.itemslayout);
        Intent intent = getIntent();
        storeName = intent.getStringExtra("storename");
        username = intent.getStringExtra("username");
        TextView textView = findViewById(R.id.confirmstorename);
        textView.setText(storeName);
        totalPrice =(TextView)findViewById(R.id.confirm_totalprice);
        add_order_to_database();

    }
    private int CalculateTotalPrice(){
        int totalPrice = 0;
        for (Product p: order){
            totalPrice += p.getPrice()*p.getProductCount();
        }
        return totalPrice;
    }

    private void setAdapter() {
        this.adapter = new ConfirmRecyclerAdapter(order);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public void click_back_store(View v){
        Intent intent = new Intent(this, CustomerMainActivity.class);
        intent.putExtra("storename", storeName);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    private void add_order_to_database(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Order input = new Order(ProductMainActivity.products, storeName, username);
        //child("Customer").child(username).child("orders").get().
        ref.child("Customer").child(username).child("orders").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("demo", "Error getting data", task.getException());
                }
                else {
                    int count_child = (int) task.getResult().getChildrenCount();
                    //input.setOrderId(count_child + 1);
                    id = count_child + 1;
                    String key = input.getOrderId() + Integer.toString(id);
                    write_to_database(ref.child("Customer").child(username).child("orders").child(key),input);

                }
            }
        });

        ref.child("Store Owner").child(storeName).child("orders").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("demo", "Error getting data", task.getException());
                }
                else {
                    //int count_child = (int) task.getResult().getChildrenCount();
                    String key = input.getOrderId() + Integer.toString(id);
                    write_to_database(ref.child("Store Owner").child(storeName).child("orders").child(key),input);
                }
            }
        });
        totalPrice.setText(String.valueOf(CalculateTotalPrice()));
        setAdapter();
    }

    private void write_to_database(DatabaseReference databaseReference, Order input){
        String store_name = input.getStore_order();
        String customer_name = input.getCustomer_order();
        String orderId = input.getOrderId();
        ArrayList<Product> products = input.getProducts();
        ArrayList<Product> input_product = new ArrayList<>();
        for(Product product:products){
            if(product.getProductCount()!= 0){
                input_product.add(product);
            }
        }
        databaseReference.child("customer_order").setValue(customer_name);
        databaseReference.child("store_order").setValue(store_name);
        databaseReference.child("status").setValue(false);
        databaseReference.child("orderId").setValue(orderId);
        for(int i = 0; i < input_product.size(); i++){
            databaseReference.child("products").child(input_product.get(i).getProductName()).setValue(input_product.get(i));
        }
    }
}
