package com.example.jiahan.foodhouse;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Random;

public class VerifyActivity extends AppCompatActivity {

    Button buttonSubmit,buttonVerify;
    LinearLayout l1;
    EditText phoneEdit,codeEdit;
    static int code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(VerifyActivity.this, new String[]{Manifest.permission.SEND_SMS}, 1);
            }
        }
        l1 = findViewById(R.id.linear1);
        phoneEdit = findViewById(R.id.phoneTXT);
        codeEdit = findViewById(R.id.codeTXT);




        buttonSubmit = findViewById(R.id.submitBTN);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(phoneEdit.getText().toString().trim().equals("")){
                    Toast.makeText(VerifyActivity.this,"Phone Number Cannot be Blank",Toast.LENGTH_LONG).show();
                }
                else if(phoneEdit.getText().toString().trim().substring(0,3).equals("+60")){
                    int min = 1000;
                    int max = 9999;
                    Random rand = new Random();
                    code = rand.nextInt(max - min + 1) + min;
                    //Getting intent and PendingIntent instance
                    Intent intent=new Intent();
                    PendingIntent pi=PendingIntent.getActivity(getApplicationContext(), 0, intent,0);
                    //Get the SmsManager instance and call the sendTextMessage method to send message
                    SmsManager sms=SmsManager.getDefault();
                    sms.sendTextMessage(phoneEdit.getText().toString(), null, "FoodHouse Verification Code: " + code, pi,null);
                    Toast.makeText(VerifyActivity.this,"Verification Code Sent. You will received shortly",Toast.LENGTH_LONG).show();
                    l1.setVisibility(View.GONE);

                }
                else{

                   Toast.makeText(VerifyActivity.this,"Wrong Format (+60)",Toast.LENGTH_LONG).show();
                }

            }
        });

        buttonVerify = findViewById(R.id.verifyBTN);
        buttonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(codeEdit.getText().toString().trim().equals(Integer.toString(code))){
                    Toast.makeText(VerifyActivity.this,"Verify Successfully. Your order will be arrived soon!",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(VerifyActivity.this,homePage.class));
                }
                else{
                    Toast.makeText(VerifyActivity.this,"Wrong Code. Try again",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
