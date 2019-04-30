package com.example.jiahan.foodhouse;

import android.Manifest;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
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

import java.util.ArrayList;
import java.util.Random;

public class cartActivity extends AppCompatActivity{
    ListView listView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    TextView totalPriceView;
    double total;
    Button button;
    String addressName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Intent intent = getIntent();
        //addressName = intent.getExtras().getString("address");
        //Toast.makeText(cartActivity.this,addressName,Toast.LENGTH_SHORT).show();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog alertDialog = new AlertDialog.Builder(cartActivity.this).create(); //Read Update
                alertDialog.setTitle("Are you sure want to reset your cart?? ");

                alertDialog.setButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        databaseReference.removeValue();
                        Toast.makeText(cartActivity.this,"Reset Successfully",Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(getIntent());


                    }
                });
                alertDialog.show();
                //Intent intent=new Intent(getApplicationContext(),MainActivity.class);

            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Cart");

        button = findViewById(R.id.btnPay);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(arrayList.isEmpty()){
                    Toast.makeText(cartActivity.this,"Your Cart is empty!",Toast.LENGTH_SHORT).show();

                }
                else{


                    AlertDialog alertDialog = new AlertDialog.Builder(cartActivity.this).create(); //Read Update
                    alertDialog.setTitle("Are you sure to proceed to payment? ");

                    alertDialog.setButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            databaseReference.removeValue();
                            Toast.makeText(cartActivity.this,"Proceed to address details",Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(cartActivity.this,LastAddressActivity.class));
                        }
                    });
                    alertDialog.show();
                    //Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                }



            }
        });
        totalPriceView = findViewById(R.id.totalPrice);
        listView = findViewById(R.id.lvCart);



        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Food value=dataSnapshot.getValue(Food.class);
                Double totalItemPrice = value.getPrice() * value.getNumber();
                arrayList.add(value.getFruit() + "\t" + Integer.toString(value.getNumber()) + " x " + Double.toString(value.getPrice())+ "\t\t\t\t" + totalItemPrice);
                total += totalItemPrice;
                System.out.println(arrayList.size());
                arrayAdapter = new ArrayAdapter<String>(cartActivity.this, android.R.layout.simple_list_item_1, arrayList);
                listView.setAdapter(arrayAdapter);
                totalPriceView.setText("Total: " + total);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });



    }




}