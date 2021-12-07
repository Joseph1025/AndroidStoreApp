package com.example.storeapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SingleOrderRecyclerAdapter extends RecyclerView.Adapter<SingleOrderRecyclerAdapter.SingleOrderViewHolder> {
    private ArrayList<Product> order;

    public SingleOrderRecyclerAdapter(ArrayList<Product> order){
        this.order = order;


    }

    public class SingleOrderViewHolder extends RecyclerView.ViewHolder{
        private TextView nameTxt1;
        private TextView countText;
        private  TextView price;
        public SingleOrderViewHolder(final View view){
            super(view);
            nameTxt1 = view.findViewById(R.id.product_id);
            countText = view.findViewById(R.id.productlayout);
            price = view.findViewById(R.id.orderprice);
        }


    }
    @NonNull
    @Override
    public SingleOrderRecyclerAdapter.SingleOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_singleorderlayout,parent,false);
        return new SingleOrderRecyclerAdapter.SingleOrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SingleOrderRecyclerAdapter.SingleOrderViewHolder holder, int position) {
        String name = order.get(position).getProductName();
        String count = String.valueOf(order.get(position).getProductCount());
        String price = String.valueOf(order.get(position).getPrice()*order.get(position).getProductCount());
        holder.nameTxt1.setText(name);
        holder.countText.setText(count);
        holder.price.setText(price);
    }

    @Override
    public int getItemCount() {
        return order.size();
    }
}


