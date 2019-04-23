package com.example.jiahan.foodhouse;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText username,password;
    Button btnlogin;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.etRUserPassword);
        firebaseAuth = FirebaseAuth.getInstance();
        btnlogin = (Button)findViewById(R.id.btnLogin);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference().child("Users").child(username.getText().toString());
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        System.out.println(user);
                        if(user != null){
                            if(password.getText().toString().equals(user.password)){
                                Toast.makeText(MainActivity.this, "Login Successfully!",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(MainActivity.this,homePage.class));
                            }

                        }
                        else{
                            Toast.makeText(MainActivity.this, "Invalid Username or password",Toast.LENGTH_LONG).show();

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }
                });




            }
        });
    }


    void jumpToSignUpPage(View v){
        Intent i = new Intent(this,SignUpActivity.class );
        startActivity(i);
    }
}

