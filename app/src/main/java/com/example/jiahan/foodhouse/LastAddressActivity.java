package com.example.jiahan.foodhouse;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LastAddressActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_last_address);



        //initial the firebase authentication
        firebaseAuth = FirebaseAuth.getInstance();
        //to input current address, get from cartFragment intent


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

                    AlertDialog alertDialog = new AlertDialog.Builder(LastAddressActivity.this).create(); //Read Update
                    alertDialog.setTitle("Are you sure the address is correct? ");

                    alertDialog.setButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Toast.makeText(LastAddressActivity.this,"Proceed to payment",Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(LastAddressActivity.this,VerifyActivity.class));
                        }
                    });
                    alertDialog.show();
                }
                else{
                    Toast.makeText(LastAddressActivity.this,"All space must be fill up",Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    void getStringFromEditText(){
        //getText from  user input
        if(houseAddress1.getText().toString().equals("")){
            Toast.makeText(LastAddressActivity.this,"All space must be fill up",Toast.LENGTH_LONG).show();
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
            Toast.makeText(LastAddressActivity.this,"All space must be fill up",Toast.LENGTH_LONG).show();
            success = false;
            return;
        }
        else{
            cityString = city.getText().toString().trim();
            success = true;
        }
        if(state.getText().toString().equals("")){
            Toast.makeText(LastAddressActivity.this,"All space must be fill up",Toast.LENGTH_LONG).show();
            success = false;
            return;
        }
        else{
            stateString = state.getText().toString().trim();
            success =true;

        }
        if(postal.getText().toString().equals("")){
            Toast.makeText(LastAddressActivity.this,"All space must be fill up",Toast.LENGTH_LONG).show();
            success = false;
            return;
        }
        else{
            postalString = postal.getText().toString().trim();
            success = true;
        }
    }
}
