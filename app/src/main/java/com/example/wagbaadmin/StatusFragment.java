package com.example.wagbaadmin;

import static com.example.wagbaadmin.OrdersFragment.ORDER;
import static com.example.wagbaadmin.OrdersFragment.user_hashmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class StatusFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public StatusFragment() {
    }

    public static StatusFragment newInstance(String param1, String param2) {
        StatusFragment fragment = new StatusFragment();
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

    String order_number;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_status, container, false);
        RadioGroup radioGroup =view.findViewById(R.id.radio_group);

/*

        Spinner spinner = view.findViewById(R.id.action_bar_spinner);
        ArrayAdapter<String> ArrAdapt = new ArrayAdapter<String>( view.getContext(), android.R.layout.simple_spinner_item, array);
        ArrAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ArrAdapt);
*/


        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference myRef = db.getReference("history_item/"+user_hashmap.get(ORDER)+"/"+ORDER);

        Button update = view.findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //order_number= spinner.getSelectedItem().toString();

                if(radioGroup.getCheckedRadioButtonId()==R.id.order_confirmed){
                    myRef.child("Status").setValue("Order Confirmed");
                }
                else if (radioGroup.getCheckedRadioButtonId()==R.id.preparing_order){
                    myRef.child("Status").setValue("Preparing Order");
                }
                else if (radioGroup.getCheckedRadioButtonId()==R.id.out_for_delivery){
                    myRef.child("Status").setValue("Order out for Delivery");
                }
                else if (radioGroup.getCheckedRadioButtonId()==R.id.order_delivered){
                    myRef.child("Status").setValue("Order Delivered");
                }
            }
        });



        return view;
    }
}