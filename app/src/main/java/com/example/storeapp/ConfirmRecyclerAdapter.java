package com.example.storeapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ConfirmRecyclerAdapter extends RecyclerView.Adapter<ConfirmRecyclerAdapter.ConfirmViewHolder>{
    private ArrayList<Product> order;

    public ConfirmRecyclerAdapter(ArrayList<Product> order){
        this.order = order;


    }

    public class ConfirmViewHolder extends RecyclerView.ViewHolder{
        private TextView nameTxt1;
        private TextView countText;
        private  TextView price;
        public ConfirmViewHolder(final View view){
            super(view);
            nameTxt1 = view.findViewById(R.id.Confirm_text);
            countText = view.findViewById(R.id.itemslayout);
            price = view.findViewById(R.id.confirmprice);
        }
    }

    @NonNull
    @Override
    public ConfirmRecyclerAdapter.ConfirmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_confirmitems,parent,false);
        return new ConfirmRecyclerAdapter.ConfirmViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ConfirmRecyclerAdapter.ConfirmViewHolder holder, int position) {
        String name = order.get(position).getProductName();
        String count = String.valueOf(order.get(position).getProductCount());
        String totalprice = String.valueOf(order.get(position).getPrice()*order.get(position).getProductCount());
        holder.nameTxt1.setText(name);
        holder.countText.setText(count);
        holder.price.setText(totalprice);
    }

    @Override
    public int getItemCount() {
        return order.size();
    }
}
