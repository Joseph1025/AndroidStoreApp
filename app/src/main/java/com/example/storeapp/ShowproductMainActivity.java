package com.example.storeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ShowproductMainActivity extends AppCompatActivity {

    private Product mainProd;
    private String storeName;
    private String prodName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showproductmain);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra("PRODUCT_NAME");
        prodName = message;
        String des = intent.getStringExtra("DES");
        storeName = intent.getStringExtra("STORE_NAME");
        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.storeowner_storeowner_productname2);
        textView.setText(message);
        TextView textView2 = findViewById(R.id.storeowner_description);
        textView2.setText(des);
    }

    public void deletemyproducts(View v){
        delProduct(prodName, storeName);

        // Go back to MyProductMainActivity
        Intent intent = new Intent(this, MyproductMainActivity.class);
        intent.putExtra("username", storeName);
        startActivity(intent);
    }

    public void delProduct(String pName, String storeName) {
        /**
         * Delete target product info given its product name and store name.
         */
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Store Owner");
        DatabaseReference subRef = ref.child(storeName).child("products").child(pName);
        subRef.setValue(null);
    }

    public Product prodWrapper(DataSnapshot snapshot) {
        String nm = "";
        String des = "";
        int count = 0;
        int price = 0;
        for (DataSnapshot snap: snapshot.getChildren()) {
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

        return new Product(nm, des, price, count);
    }

}
