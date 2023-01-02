package com.example.wagbaadmin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class OrdersFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public OrdersFragment() {
    }

    public static OrdersFragment newInstance(String param1, String param2) {
        OrdersFragment fragment = new OrdersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    int i =0;
    RecyclerView History_recyclerView;
    ArrayList<HistoryModel> History_recyclerDataArrayList;
    String order;
    public static String ORDER;
    public static ArrayList<String> array;
    public static HashMap<String , String> user_hashmap;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_orders, container, false);

        user_hashmap= new HashMap<String, String>();
        array = new ArrayList<>();
        History_recyclerView=view.findViewById(R.id.history_recyclerview);

        // created new array list..
        History_recyclerDataArrayList=new ArrayList<>();


        // added data from arraylist to adapter class.
        HistoryAdapter adapter=new HistoryAdapter(History_recyclerDataArrayList,view.getContext());

        // setting normal linear layout manger
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());

        // setting adapter to recycler view.
        History_recyclerView.setLayoutManager(linearLayoutManager);
        History_recyclerView.setAdapter(adapter);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference myRef = db.getReference("history_item");




        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                History_recyclerDataArrayList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(i <= snapshot.getChildrenCount()) {


                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                            order = dataSnapshot1.getKey().toString();
                            if (!array.contains(order)) {
                                array.add(order);
                            }
                            user_hashmap.put(order,dataSnapshot.getKey().toString());
                            if(dataSnapshot1.hasChild("Status")) {
                                HistoryModel historyModel = dataSnapshot1.getValue(HistoryModel.class);
                                History_recyclerDataArrayList.add(historyModel);
                            }
                        }
                        i++;
                    }

                }
                adapter.notifyDataSetChanged();
                i=0;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        History_recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(view.getContext(), History_recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        ORDER =History_recyclerDataArrayList.get(position).getOrder_name();
                        String[] order_number=ORDER.split("#");
                        ORDER = order_number[1];
                        startActivity(new Intent(view.getContext(),ConfirmOrder.class));

                    }

                    @Override public void onLongItemClick(View view, int position) {

                    }
                })
        );


        return view;
    }

}