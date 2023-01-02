package com.example.wagbaadmin;

import static com.example.wagbaadmin.OrdersFragment.ORDER;
import static com.example.wagbaadmin.OrdersFragment.user_hashmap;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ConfirmOrderFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ConfirmOrderFragment() {
    }

    public static ConfirmOrderFragment newInstance(String param1, String param2) {
        ConfirmOrderFragment fragment = new ConfirmOrderFragment();
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


    int i =1;

    RecyclerView order_recyclerView;
    ArrayList<OrderItemModel> order_recyclerDataArrayList;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_confirm_order, container, false);

        order_recyclerView=view.findViewById(R.id.recyclerview_order_status);

        // created new array list..
        order_recyclerDataArrayList=new ArrayList<>();


        // added data from arraylist to adapter class.
        OrderItemAdapter adapter=new OrderItemAdapter(order_recyclerDataArrayList, view.getContext());

        // setting normal linear layout manger
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());

        // setting adapter to recycler view.
        order_recyclerView.setLayoutManager(linearLayoutManager);
        order_recyclerView.setAdapter(adapter);

        Button cancel=view.findViewById(R.id.cancel_button);
        Button confirm= view.findViewById(R.id.confirm_button);


        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference myRef = db.getReference("history_item/"+user_hashmap.get(ORDER)+"/"+ORDER);
        TextView order_name = view.findViewById(R.id.order_number_title);
        TextView order_date = view.findViewById(R.id.order_date_details);
        TextView order_price = view.findViewById(R.id.order_price_details);
        Log.d("path",ORDER);
        Log.d("path",user_hashmap.get(ORDER));
        Log.d("path",ORDER);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                order_name.setText(snapshot.child("order_name").getValue().toString());
                order_date.setText(snapshot.child("order_date").getValue().toString());
                order_price.setText(snapshot.child("order_price").getValue().toString());
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(snapshot.hasChild("order_item_"+String.valueOf(i))){
                        OrderItemModel item = new OrderItemModel();
                        item.setOrder_name(snapshot.child("order_item_"+String.valueOf(i)).getValue().toString());
                        item.setOrder_item_price(snapshot.child("order_item_"+String.valueOf(i)+"_price").getValue().toString());
                        order_recyclerDataArrayList.add(item);
                        i++;

                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                myRef.child("Status").setValue("Order Cancelled");
                            }
                        });
                        confirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(compareDates("10:30")&& snapshot.child("order_time").getValue().toString().equals("noon") ||(compareDates("13:30")&& snapshot.child("order_time").getValue().toString().equals("three"))) {

                                    myRef.child("Status").setValue("Order Confirmed");
                                    getParentFragmentManager().beginTransaction().replace(R.id.order_frag, new StatusFragment()).commit();

                                }else{
                                    if(snapshot.child("order_time").getValue().toString().equals("noon")){
                                        Toast.makeText(view.getContext(), "Cannot confirm order after 10:30",Toast.LENGTH_SHORT).show();
                                    }else if(snapshot.child("order_time").getValue().toString().equals("three")){
                                        Toast.makeText(view.getContext(), "Cannot confirm order after 01:30",Toast.LENGTH_SHORT).show();
                                    }
                                    myRef.child("Status").setValue("Order Cancelled");

                                }


                            }
                        });

                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }



    public static boolean compareDates(String date_string){
        Calendar now = Calendar.getInstance();
        Date date;
        Date dateCompareOne;
        int hour = now.get(Calendar.HOUR);
        int minute = now.get(Calendar.MINUTE);

        date = parseDate(hour + ":" + minute);
        dateCompareOne = parseDate(date_string);
        if ( date.before( dateCompareOne ) ) {
            return true;
        }
        return false;

    }
    public static final String inputFormat = "HH:mm 'Uhr'";
    public static Date parseDate(String date) {

        SimpleDateFormat inputParser = new SimpleDateFormat(inputFormat, Locale.US);

        try {
            return inputParser.parse(date);
        } catch (java.text.ParseException e) {
            return new Date(0);
        }
    }

}