package com.example.jiahan.foodhouse;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    TextView address;
    TextView signout;
    TextView name;
    String addressName;
    String username;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_cart, null);

        //get value from login page and set to user_profile_name
        username = getActivity().getIntent().getExtras().getString("username");
        addressName = getActivity().getIntent().getExtras().getString("address");

        name = v.findViewById(R.id.user_profile_name);
        name.setText(username);


        address = v.findViewById(R.id.addressBtn);
        address.setOnClickListener(new View.OnClickListener(
        ) {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),AddressActivity.class);
                i.putExtra("addressName",addressName);
                i.putExtra("username",username);
                startActivity(i);

            }
        });
        signout = v.findViewById(R.id.signOutBtn);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),MainActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        return v;

    }


}