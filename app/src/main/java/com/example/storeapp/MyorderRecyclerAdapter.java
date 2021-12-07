package com.example.storeapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyorderRecyclerAdapter extends RecyclerView.Adapter<MyorderRecyclerAdapter.MyorderViewHolder> {
    private ArrayList<String> orders;
    private MyorderRecyclerAdapter.RecyclerViewClickListener1 listener;

    public MyorderRecyclerAdapter(ArrayList<String> orders, MyorderRecyclerAdapter.RecyclerViewClickListener1 listener){
        this.orders = orders;
        this.listener = listener;

    }

    public class MyorderViewHolder extends RecyclerView.ViewHolder{
        private TextView nameTxt1;
        public MyorderViewHolder(final View view) {
            super(view);
            nameTxt1 = view.findViewById(R.id.storeowner_textView22);
            view.findViewById(R.id.storeowner_orderdetail).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v,getAdapterPosition());
                }
            });
        }
    }
    @NonNull
    @Override
    public MyorderRecyclerAdapter.MyorderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_storeowner_singleorder2,parent,false);
        return new MyorderRecyclerAdapter.MyorderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyorderRecyclerAdapter.MyorderViewHolder holder, int position) {
        String name = orders.get(position);
        holder.nameTxt1.setText(name);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public interface RecyclerViewClickListener1{
        void onClick(View v, int position);

    }
}

