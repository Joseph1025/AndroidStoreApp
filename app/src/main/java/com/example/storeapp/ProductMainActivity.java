package com.example.storeapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

public class ProductMainActivity extends AppCompatActivity {
    public static ArrayList<Product> products;
    private RecyclerView recyclerView;
    private ProductRecyclerAdapter adapter;
    private ItemClickListener listener;
    private TextView text;
    private String storeName;
    private String username;
    private boolean empty = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productmain);
        recyclerView = findViewById(R.id.product_recyclerview);
        products = new ArrayList<>();
        Intent intent = getIntent();
        storeName = intent.getStringExtra("storename");
        username = intent.getStringExtra("username");
        TextView textView = findViewById(R.id.singlestorename);
        textView.setText(storeName);
        setUserInfo();
    }

    private void setAdapter() {
        setOnClickListener();
        ProductRecyclerAdapter adapter = new ProductRecyclerAdapter(products,listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setOnClickListener() {
        listener = new ItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                products.get(position).addItem();
                int count = products.get(position).getProductCount();
                text.setText(count);
            }
        };
    }

    public void checkOrder(){
        for(Product p:products){
            if (p.getProductCount() !=0){
                empty = false;
            }
        }
    }

    public void confirm(View v){
        checkOrder();
        if(!empty) {
            Intent intent = new Intent(this, ConfirmMainActivity.class);
            intent.putExtra("storename", storeName);
            intent.putExtra("username", username);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "order can not be empty", Toast.LENGTH_LONG).show();
        }
    }

    private void setUserInfo() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Store Owner");
        // need to get the store name and add their products to productsList
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                products = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    if(child.getKey().equals(storeName)){
                        // at test_store node
                        ArrayList<Product> temp = setProduct(child);
                        products.addAll(temp);
                        Log.d("demo", String.valueOf(temp.size()));
                    }
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

    // give a store name, get all it's products list
    private ArrayList<Product> setProduct(DataSnapshot child){
        ArrayList<Product> product = new ArrayList<>();
        for (DataSnapshot temp : child.getChildren()) {
            if(temp.getKey().equals("products")){
                for(DataSnapshot temp_2 : temp.getChildren()){
                    // this is product 1
                    String name = "";
                    String description = "";
                    int price = 0;
                    for(DataSnapshot temp_3: temp_2.getChildren()){
                        // this is loop over product 1
                        if(temp_3.getKey().equals("productName")){
                            Log.d("demo_demo", temp_3.getKey());
                            name = temp_3.getValue(String.class);
                        }
                        else if(temp_3.getKey().equals("description")){
                            Log.d("demo_demo", temp_3.getKey());
                            description = temp_3.getValue(String.class);
                        }
                        else if(temp_3.getKey().equals("price")){
                            Log.d("demo_demo", temp_3.getKey());
                            price = temp_3.getValue(Integer.class);
                        }
                    }
                    Product product_temp = new Product(name, description, price);
                    product.add(product_temp);
                }
            }
        }
        return product;
    }
}


