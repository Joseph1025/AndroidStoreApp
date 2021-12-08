package com.example.storeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class MyproductMainActivity extends AppCompatActivity implements SetUserInfo, SetAdapter{

    public static final String PRODUCT_NAME = "com.example.StoreOwner.MESSAGE";
    private ArrayList<String> productsList;
    private ArrayList<Product> prodList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MyproductRecyclerAdapter.RecyclerViewClickListener listener;

    // Fields that connects to firebase
    String storeName;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myproductmain);
        recyclerView = findViewById(R.id.storeowner_products_recyclerview);
        productsList = new ArrayList<>();

        // Connect to the specific owner on database
        Intent intent = getIntent();
        storeName = intent.getStringExtra("username");

        setUserInfo();
        setAdapter();

    }

    public void setAdapter() {
        setOnClickListener();
        MyproductRecyclerAdapter adapter = new MyproductRecyclerAdapter(productsList,listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setOnClickListener() {
        listener = new MyproductRecyclerAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), ShowproductMainActivity.class);
                String message = productsList.get(position);
                String des = prodList.get(position).getDescription();
                int pr = prodList.get(position).getPrice();
                String price = String.valueOf(pr);
                intent.putExtra("PRODUCT_NAME", message);
                intent.putExtra("DES", des);
                intent.putExtra("STORE_NAME", storeName);
                intent.putExtra("PRICE",price);
                startActivity(intent);
            }
        };

    }

    public void clicknewproduct(View v){
        Intent intent = new Intent(this, NewProductMainActivity.class);
        intent.putExtra("name", storeName);
        startActivity(intent);

    }


    public void setUserInfo() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Store Owner");
        // need to get the store name and add their products to productsList
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                productsList = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    if(Objects.equals(child.getKey(), storeName)){
                        // at test_store node
                        ArrayList<Product> temp = setProduct(child);
                        for (Product p: temp) {
                            productsList.add(p.getProductName());
                            prodList.add(p);
                        }
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
                        else if(temp_3.getKey().equals("price")) {
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MyproductMainActivity.this, StoreOwnerMainActivity.class);
        intent.putExtra("username", storeName);
        startActivity(intent);
    }

}






