package com.example.jiahan.foodhouse;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ebanx.swipebtn.OnStateChangeListener;
import com.ebanx.swipebtn.SwipeButton;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.FoldingCube;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class randomFragment extends Fragment {
    ProgressBar progressBar;
    FirebaseDatabase database;
    DatabaseReference myRef ;
    List<Restaurant> list;
    RecyclerView recycle;
    String addressName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_random, null);
        recycle = v.findViewById(R.id.recycle);
        database = FirebaseDatabase.getInstance();

        addressName = getActivity().getIntent().getExtras().getString("address");
        //for the progress bar to disable at start
        progressBar = (ProgressBar)v.findViewById(R.id.spin_kit);
        progressBar.setVisibility(View.GONE);

        FloatingActionButton fab = v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),cartActivity.class);
                i.putExtra("address",addressName);
                startActivity(i);
            }
        });

        //swipe button actionlistener
        SwipeButton enableButton = (SwipeButton) v.findViewById(R.id.swipe_btn);
        enableButton.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                progressBar.setVisibility(View.VISIBLE);
                Sprite doubleBounce = new FoldingCube();
                progressBar.setIndeterminateDrawable(doubleBounce);
                showDialog(5000);
            }
        });

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


    public void showDialog(int timeInMilliSeconds) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                Random rand = new Random();
                int n = rand.nextInt(list.size());
                final String randomTitle = list.get(n).getTitle();
                final String randomDesc = list.get(n).getDesc();
                final String randomType = list.get(n).getType();
                final String randomPrice = list.get(n).getPrice();
                AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create(); //Read Update
                alertDialog.setTitle("After the calculation! We suggest: ");
                alertDialog.setMessage("Restaurant: " + randomTitle +"\n" + "Type: " + randomType + "\n" +"Price: " + randomPrice);
                alertDialog.setButton("View Details", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(getActivity(),RestaurantActivity.class);
                        i.putExtra("address",addressName);
                        i.putExtra("title",randomTitle);
                        i.putExtra("desc",randomDesc);
                        i.putExtra("price",randomPrice);
                        i.putExtra("type",randomType);
                        startActivity(i);
                    }
                });


                alertDialog.show();
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(runnable, timeInMilliSeconds);
    }
}