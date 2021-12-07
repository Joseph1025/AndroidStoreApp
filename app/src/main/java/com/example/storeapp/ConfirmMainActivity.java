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
    private TextView text;
    private String storename;
    private TextView Totalprice;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmmain);
        recyclerView = findViewById(R.id.confirmrecyclerview);
        order = ProductMainActivity.products;
        text = (TextView)findViewById(R.id.itemslayout);
        Intent intent = getIntent();
        storename = intent.getStringExtra("storename");
        username = intent.getStringExtra("username");
        TextView textView = findViewById(R.id.confirmstorename);
        textView.setText(storename);
        Totalprice =(TextView)findViewById(R.id.confirm_totalprice);

        add_order_to_database(storename, username);

    }
    private int CaculateTotalPrice(){
        int totalprice = 0;
        for (Product p: order){
            totalprice += p.getPrice()*p.getProductCount();
        }
        return totalprice;
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
        intent.putExtra("storename", storename);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    private void add_order_to_database(String str, String username){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Order input = new Order(ProductMainActivity.products, str, username);

        ref.child("Customer").child(username).child("orders").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("demo", "Error getting data", task.getException());
                }
                else {
                    int count_child = (int) task.getResult().getChildrenCount();
                    write_to_database(ref.child("Customer").child(username).child("orders").child(String.valueOf(count_child+1)),input);

                }
            }
        });
        ref.child("Store Owner").child(str).child("orders").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("demo", "Error getting data", task.getException());
                }
                else {
                    int count_child = (int) task.getResult().getChildrenCount();
                    write_to_database(ref.child("Store Owner").child(str).child("orders").child(String.valueOf(count_child+1)),input);
                }
            }
        });
        Totalprice.setText(String.valueOf(CaculateTotalPrice()));
        setAdapter();

        /* ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int count_child = 0;
                count_child = (int) dataSnapshot.child("Customer").child("test").child("orders").getChildrenCount();
                write_to_database(ref.child("Customer").child("test").child("orders").child("Order" + String.valueOf(count_child+1)),input);
                setAdapter();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.i("demo_2", "Failed to read value.", error.toException());
            }
        };
        ref.addValueEventListener(listener);

         */
    }

    private void write_to_database(DatabaseReference databaseReference, Order input){
        String store_name = input.getStore_order();
        String customer_name = input.getCustomer_order();
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
        for(int i = 0; i < input_product.size(); i++){
            databaseReference.child("products").child(input_product.get(i).getProductName()).setValue(input_product.get(i));
        }
    }
}
