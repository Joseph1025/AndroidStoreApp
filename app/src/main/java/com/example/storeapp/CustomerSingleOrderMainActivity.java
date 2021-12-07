package com.example.storeapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextClock;
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

public class CustomerSingleOrderMainActivity extends AppCompatActivity {
    private ArrayList<Product> order;
    private RecyclerView recyclerView;
    private SingleOrderRecyclerAdapter adapter;
    private TextView text1;
    private TextView text2;
    private TextView Totalprice;
    private String username;
    private String ordername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleordermain);
        recyclerView = findViewById(R.id.productlayoutrecyclerview);
        order = new ArrayList<>();
        username = getIntent().getStringExtra("username");
        ordername = getIntent().getStringExtra("ordername");
        text1 = (TextView)findViewById(R.id.productlayout);
        text2 = (TextView)findViewById(R.id.notification);
        Totalprice =(TextView)findViewById(R.id.totalprice);

        setUserInfo(username, ordername);


    }
    private int CaculateTotalPrice(){
        int totalprice = 0;
        for (Product p: order){
            totalprice += p.getPrice()*p.getProductCount();
        }
        return totalprice;
    }
    private void setAdapter() {
        this.adapter = new SingleOrderRecyclerAdapter(order);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setUserInfo(String username, String order_name) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Customer");
        ref = ref.child(username).child("orders");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                order = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    if(child.getKey().equals(order_name)){
                        // at test_store node
                        ArrayList<Product> temp = setProduct(child);
                        order.addAll(temp);
                        Log.d("demo", String.valueOf(temp.size()));
                    }
                }
                Totalprice.setText(String.valueOf(CaculateTotalPrice()));
                setAdapter();
                /*
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String str = child.getKey();
                    order.add(str);
                }
                setAdapter();

                 */
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
                    int count = 0;
                    for(DataSnapshot temp_3: temp_2.getChildren()){
                        // this is loop over product 1
                        if(temp_3.getKey().equals("productName")){
                            Log.d("demo_8", temp_3.getKey());
                            name = temp_3.getValue(String.class);
                        }
                        else if(temp_3.getKey().equals("description")){
                            Log.d("demo_8", temp_3.getKey());
                            description = temp_3.getValue(String.class);
                        }
                        else if(temp_3.getKey().equals("price")){
                            Log.d("demo_8", temp_3.getKey());
                            price = temp_3.getValue(Integer.class);
                        }
                        else if(temp_3.getKey().equals("productCount")){
                            Log.d("demo_8", temp_3.getKey());
                            count = temp_3.getValue(Integer.class);
                        }
                    }
                    Product product_temp = new Product(name, description, price, count);
                    product.add(product_temp);
                }
            }
        }
        return product;
    }


    public void click_back_order(View v){
        Intent intent = new Intent(this, CustomerMainActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

}


