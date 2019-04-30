package com.example.jiahan.foodhouse;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class homeFragment extends Fragment {

    FirebaseDatabase database;
    DatabaseReference myRef ;
    List<Restaurant> list;
    RecyclerView recycle;
    Button button;
    String addressName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_home, null);
        recycle = v.findViewById(R.id.recycle);
        database = FirebaseDatabase.getInstance();
        button = v.findViewById(R.id.button);
        addressName = getActivity().getIntent().getExtras().getString("address");
        FloatingActionButton fab = v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),cartActivity.class);
                i.putExtra("address",addressName);
                startActivity(i);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recycleAdapter recyclerAdapter = new recycleAdapter(list,getContext());
                RecyclerView.LayoutManager recyce;
                recyce = new GridLayoutManager(getActivity(),1);
                recycle.setLayoutManager(recyce);
                recycle.setItemAnimator( new DefaultItemAnimator());
                recycle.setAdapter(recyclerAdapter);
                button.setVisibility(View.INVISIBLE);

            }
        });

        //button.performClick();

        myRef = database.getReference("Restaurant");
        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                list = new ArrayList<Restaurant>();
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){

                    Restaurant value = dataSnapshot1.getValue(Restaurant.class);
                    //System.out.println(value);
                    Restaurant restaurant = new Restaurant();
                    String title = value.getTitle();
                    String desc = value.getDesc();
                    String type = value.getType();
                    String price  = value.getPrice();
                    restaurant.setTitle(title);
                    restaurant.setDesc(desc);
                    restaurant.setType(type);
                    restaurant.setPrice(price);

                    list.add(restaurant);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("faile");

            }


        });

        return v;
        
    }


}