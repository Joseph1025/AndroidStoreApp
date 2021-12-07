package com.example.storeapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.ProductViewHolder>{
    private ArrayList<Product> products;
    private ItemClickListener itemClickListener;

    public ProductRecyclerAdapter(ArrayList<Product> products,ItemClickListener itemClickListener){
        this.products = products;
        this.itemClickListener = itemClickListener;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        private TextView nameTxt;
        private TextView countText;
        private TextView addButton;
        private TextView minusButton;
        private TextView price;
        private TextView description;

        public ProductViewHolder(final View view) {
            super(view);
            nameTxt = view.findViewById(R.id.textView4);
            countText = view.findViewById(R.id.itemCount);
            addButton = view.findViewById(R.id.plus);
            minusButton = view.findViewById(R.id.minus);
            price = view.findViewById(R.id.price);
            description = view.findViewById(R.id.description);
        }
    }

    @NonNull
    @Override
    public ProductRecyclerAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_singleproduct, parent, false);
        return new ProductRecyclerAdapter.ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductRecyclerAdapter.ProductViewHolder holder, int position) {
        String name = products.get(position).getProductName();
        String count = String.valueOf(products.get(position).getProductCount());
        holder.nameTxt.setText(name);
        holder.countText.setText(count);
        holder.price.setText(String.valueOf(products.get(position).getPrice()));
        holder.description.setText(String.valueOf(products.get(position).getDescription()));
        holder.addButton.setOnClickListener(new PlusListener(position,products.get(position),holder.countText));
        holder.minusButton.setOnClickListener(new MinusListener(position,products.get(position),holder.countText));
    }

    
    @Override
    public int getItemCount() {
        return products.size();
    }


    public class PlusListener implements View.OnClickListener
    {
        private Product product;
        int position;
        TextView txtCount;

        PlusListener(int position,Product product,TextView txtCount)
        {
            this.product = product;
            this.position = position;
            this.txtCount= txtCount;

        }
        @Override
        public void onClick(View view) {

            product.addItem();
            int count  = product.getProductCount();
            txtCount.setText(""+count);
        }
    }
    public class MinusListener implements View.OnClickListener
    {
        private Product product;
        int position;
        TextView txtCount;

        MinusListener(int position,Product product,TextView txtCount)
        {
            this.product = product;
            this.position = position;
            this.txtCount= txtCount;

        }
        @Override
        public void onClick(View view) {
            int count;
            if(product.getProductCount()<=0){
                count = 0;
            }
            else {
                product.deleteItem();
                count = product.getProductCount();
                txtCount.setText("" + count);
            }
        }
    }



}


