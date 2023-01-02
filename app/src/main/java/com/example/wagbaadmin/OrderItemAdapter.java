package com.example.wagbaadmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.RecyclerViewHolder> {

    private ArrayList<OrderItemModel> ItemArrayList;
    private Context my_context;
    public OrderItemAdapter(ArrayList<OrderItemModel> recyclerDataArrayList, Context my_context) {
        this.ItemArrayList = recyclerDataArrayList;
        this.my_context = my_context;
    }



    @NonNull
    @Override
    public OrderItemAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_details, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemAdapter.RecyclerViewHolder holder, int position) {

        OrderItemModel recyclerData = ItemArrayList.get(position);
        holder.order_name.setText(recyclerData.getOrder_name());
        holder.order_price.setText(recyclerData.getOrder_item_price());
    }

    @Override
    public int getItemCount() {
        return ItemArrayList.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView order_name;
        private TextView order_price;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            order_name = itemView.findViewById(R.id.order_name_details);
            order_price = itemView.findViewById(R.id.price_details);
        }
    }
}
