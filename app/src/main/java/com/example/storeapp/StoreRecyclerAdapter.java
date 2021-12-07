package com.example.storeapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StoreRecyclerAdapter extends RecyclerView.Adapter<StoreRecyclerAdapter.StoreViewHolder> {
    private ArrayList<String> storesList;
    private RecyclerViewClickListener listener;

    public StoreRecyclerAdapter(ArrayList<String> storesList, RecyclerViewClickListener listener){
        this.storesList = storesList;
        this.listener = listener;
    }

    public class StoreViewHolder extends RecyclerView.ViewHolder{
        private TextView nameTxt;

        public StoreViewHolder(final View view) {
            super(view);
            nameTxt = view.findViewById(R.id.textView2);
            view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v,getAdapterPosition());
                }
            });
        }
    }

    @NonNull
    @Override
    public StoreRecyclerAdapter.StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_singlestore, parent, false);
        return new StoreViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreRecyclerAdapter.StoreViewHolder holder, int position) {
        String name = storesList.get(position);
        holder.nameTxt.setText(name);
    }

    @Override
    public int getItemCount() {
            return storesList.size();
        }


    public interface RecyclerViewClickListener{
        void onClick(View v, int position);

    }

}
