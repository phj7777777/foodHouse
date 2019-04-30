package com.example.jiahan.foodhouse;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class AddressActivity extends AppCompatActivity {

    String address;
    TextView currentAddress,houseAddress1,houseAddress2,city,state,postal;
    String houseAddress1String,houseAddress2String,cityString,stateString,postalString;
    boolean success = true;
    Button button;
    String total,username;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        Intent intent = getIntent();
        address = intent.getStringExtra("addressName");
        username = intent.getStringExtra("username");

        //initial the firebase authentication
        firebaseAuth = FirebaseAuth.getInstance();
        //to input current address, get from cartFragment intent
        currentAddress = findViewById(R.id.currentAddressTXT);
        if(address.equals("null")){
            currentAddress.setText("No yet input address");
        }
        else{
            currentAddress.setText(address);
        }

        houseAddress1 = findViewById(R.id.houseAddress1);
        houseAddress2 = findViewById(R.id.houseAddress2);
        city = findViewById(R.id.city);
        state = findViewById(R.id.state);
        postal = findViewById(R.id.postal);


        button = findViewById(R.id.confirmBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getStringFromEditText();
                if(success){
                    //total is the completed address
                    total = houseAddress1String + houseAddress2String + "," + postalString + "," + cityString + "," + stateString;
                    total = total.toUpperCase();
                    //when success, update the address
                    FirebaseDatabase.getInstance().getReference("Users").child(username).child("address").setValue(total);
                    Toast.makeText(AddressActivity.this,"Updated",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(AddressActivity.this,"All space must be fill up",Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    void getStringFromEditText(){
        //getText from  user input
        if(houseAddress1.getText().toString().equals("")){
            Toast.makeText(AddressActivity.this,"All space must be fill up",Toast.LENGTH_LONG).show();
            success = false;
            return;
        }
        else{
            houseAddress1String = houseAddress1.getText().toString().trim();
            success = true;

        }
        if(houseAddress2.getText().toString().equals("")){
            houseAddress2String=" ";
        }
        else{
            houseAddress2String = houseAddress2.getText().toString().trim();

        }
        if(city.getText().toString().equals("")){
            Toast.makeText(AddressActivity.this,"All space must be fill up",Toast.LENGTH_LONG).show();
            success = false;
            return;
        }
        else{
            cityString = city.getText().toString().trim();
            success = true;
        }
        if(state.getText().toString().equals("")){
            Toast.makeText(AddressActivity.this,"All space must be fill up",Toast.LENGTH_LONG).show();
            success = false;
            return;
        }
        else{
            stateString = state.getText().toString().trim();
            success =true;

        }
        if(postal.getText().toString().equals("")){
            Toast.makeText(AddressActivity.this,"All space must be fill up",Toast.LENGTH_LONG).show();
            success = false;
            return;
        }
        else{
            postalString = postal.getText().toString().trim();
            success = true;
        }
    }
}
