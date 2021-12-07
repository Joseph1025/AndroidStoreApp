package com.example.storeapp;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyproductRecyclerAdapter extends RecyclerView.Adapter<MyproductRecyclerAdapter.ProductsViewHolder> {
    private ArrayList<String> productsList;
    private  RecyclerViewClickListener listener;


    public MyproductRecyclerAdapter(ArrayList<String> storesList, RecyclerViewClickListener listener){
        this.productsList = storesList;
        this.listener = listener;

    }

    public class ProductsViewHolder extends RecyclerView.ViewHolder{
        private TextView nameTxt;

        public ProductsViewHolder(final View view) {
            super(view);
            nameTxt = view.findViewById(R.id.storeowner_productname);
            view.findViewById(R.id.storeowner_productdetail).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v,getAdapterPosition());
                }
            });

        }


    }

    @NonNull
    @Override
    public MyproductRecyclerAdapter.ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_storeowner_singleproduct2, parent, false);
        return new ProductsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyproductRecyclerAdapter.ProductsViewHolder holder, int position) {
        String name = productsList.get(position);
        holder.nameTxt.setText(name);
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }


    public interface RecyclerViewClickListener{
        void onClick(View v, int position);

    }

}
