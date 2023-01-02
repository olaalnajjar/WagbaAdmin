package com.example.wagbaadmin;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.RecyclerViewHolder>{


    private ArrayList<HistoryModel> ItemArrayList;
    private Context my_context;
    public HistoryAdapter(ArrayList<HistoryModel> recyclerDataArrayList, Context my_context) {
        this.ItemArrayList = recyclerDataArrayList;
        this.my_context = my_context;
    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        HistoryModel recyclerData = ItemArrayList.get(position);
        holder.order_name.setText(recyclerData.getOrder_name());
        holder.order_date.setText(recyclerData.getOrder_date());
        holder.order_price.setText(recyclerData.getOrder_price());
        holder.order_status.setText(recyclerData.getOrder_status());


        if(!recyclerData.getOrder_status().toString().equals("")){

            if (recyclerData.getOrder_status().contains("Delivered")) {

                holder.order_status.setTextColor(Color.parseColor("#FA4A0C"));

            } else if (recyclerData.getOrder_status().contains("Processing")) {

                holder.order_status.setTextColor(Color.parseColor("#FDBF50"));

            } else if (recyclerData.getOrder_status().contains("Canceled")) {

                holder.order_status.setTextColor(Color.parseColor("#2A2C41"));

            }
        }

    }

    @Override
    public int getItemCount() {
        return ItemArrayList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {


        private TextView order_name;
        private TextView order_date;
        private TextView order_price;
        private TextView order_status;


        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            order_name = itemView.findViewById(R.id.order_name);
            order_date = itemView.findViewById(R.id.order_date);
            order_price = itemView.findViewById(R.id.order_price_main);
            order_status = itemView.findViewById(R.id.order_status);

        }
    }
}
