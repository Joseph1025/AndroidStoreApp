package com.example.storeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class NewProductMainActivity extends AppCompatActivity {
    public static final String NEW_PRODUCT = "com.example.StoreOwner.MESSAGE";
    public static final String NEW_DESCRIPTION = "com.example.StoreOwner.EMEESSAGE";
    public String storeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storeName = getIntent().getStringExtra("name");
        setContentView(R.layout.activity_publishproduct);
    }


    public void confirmNewProducts(View view) {
        EditText editText = (EditText) findViewById(R.id.editTextproductname);
        String message = editText.getText().toString();
        EditText editText2 = (EditText) findViewById(R.id.storeowner_editTextproductdescription);
        String message2 = editText2.getText().toString();
        EditText num = findViewById(R.id.storeowner_editTextNumber);
        String price = num.getText().toString();
        if(message.length()!=0 && message2.length()!=0 && price.length()!=0){
            addProduct(message, message2, price);

            // go back to all products page
            Intent intent = new Intent(this, MyproductMainActivity.class);
            intent.putExtra("username", storeName);
            startActivity(intent);}
        else{
            Toast.makeText(this,"invalid input",Toast.LENGTH_LONG).show();
        }
    }

    public void addProduct(String name, String description, String price) {
        /**
         * Create a product by given info (from textbox input in activities),
         * and then add the product to the store's databse.
         */
        int price_num = Integer.parseInt(price);
        Product myProd = new Product(name, description, price_num);
        // Update must be passed into firebase in the form of hashmap
        Map<String, Object> prodUpdate = new HashMap<>();
        FirebaseDatabase root = FirebaseDatabase.getInstance();
        DatabaseReference ref = root.getReference("Store Owner");
        prodUpdate.put(myProd.getProductName(), myProd);
        ref.child(storeName).child("products").updateChildren(prodUpdate);
    }



}

