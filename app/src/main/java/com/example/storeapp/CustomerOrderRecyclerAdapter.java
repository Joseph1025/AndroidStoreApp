package com.example.storeapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomerOrderRecyclerAdapter extends RecyclerView.Adapter<CustomerOrderRecyclerAdapter.CustomerOrderViewHolder>{
    private ArrayList<String> orders;
    private CustomerOrderRecyclerAdapter.RecyclerViewClickListener listener;

    public CustomerOrderRecyclerAdapter(ArrayList<String> orders, CustomerOrderRecyclerAdapter.RecyclerViewClickListener listener){
        this.orders = orders;
        this.listener = listener;

    }

    public class CustomerOrderViewHolder extends RecyclerView.ViewHolder{
        private TextView nameTxt1;
        public CustomerOrderViewHolder(final View view) {
            super(view);
            nameTxt1 = view.findViewById(R.id.order_id);
            view.findViewById(R.id.order_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v,getAdapterPosition());
                }
            });
        }
    }
    @NonNull
    @Override
    public CustomerOrderRecyclerAdapter.CustomerOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_singleorders,parent,false);
        return new CustomerOrderRecyclerAdapter.CustomerOrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerOrderRecyclerAdapter.CustomerOrderViewHolder holder, int position) {
        String name = orders.get(position);
        holder.nameTxt1.setText(name);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);

    }
}

