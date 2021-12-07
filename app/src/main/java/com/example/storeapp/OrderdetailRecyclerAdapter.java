package com.example.storeapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderdetailRecyclerAdapter extends RecyclerView.Adapter<OrderdetailRecyclerAdapter.OrderdetailViewHolder>{
    private ArrayList<Product> products;

    public OrderdetailRecyclerAdapter(ArrayList<Product> products){
        this.products = products;


    }

    public class OrderdetailViewHolder extends RecyclerView.ViewHolder{
        private TextView nameTxt1;
        private TextView countText;
        public OrderdetailViewHolder(final View view){
            super(view);
            nameTxt1 = view.findViewById(R.id.storeowner_reqproduct_text);
            countText = view.findViewById(R.id.storeowner_reqnum);
        }
    }

    @NonNull
    @Override
    public OrderdetailRecyclerAdapter.OrderdetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_storeowner_singleproduct,parent,false);
        return new OrderdetailRecyclerAdapter.OrderdetailViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderdetailRecyclerAdapter.OrderdetailViewHolder holder, int position) {
        String name = products.get(position).getProductName();
        String count = String.valueOf(products.get(position).getProductCount());
        holder.nameTxt1.setText(name);
        holder.countText.setText(count);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}