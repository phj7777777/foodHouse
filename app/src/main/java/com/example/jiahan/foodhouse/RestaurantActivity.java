package com.example.jiahan.foodhouse;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RestaurantActivity extends AppCompatActivity {
    ListView listView;
    TextView textView,numberView;
    String[] listItem;
    String titleTxt;
    TextView title;
    private FirebaseAuth firebaseAuth;
    private ListView lv;
    public static ArrayList<Food> modelArrayList;
    private CustomAdapter customAdapter;
    private Button btnnext;
    private String[] fruitlist = new String[]{"Sticky Rice", "Thai MilkTea", "Tomyam", "Fried Rice","Pad Thai"};
    private String[] alist = new String[]{"Sushi", "Bento", "Sashimi", "Ramen","Sanjou"};
    private String[] blist = new String[]{"Nasi Lemak", "Satay", "Cendol", "Roti Canai","Asam Laksa"};
    private String[] clist = new String[]{"Steak", "Lamb Chop", "Chicken Chop", "Fish &Chip","Salad"};
    private String[] dlist = new String[]{"Noodles", "Mee Hun", "Yee Mee", "Laksa","Fried Rice"};
    private double[] pricelist = new double[]{19.90,20.90,30.90,10.90,19.90};
    private String[] current = new String[]{};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        Intent intent = getIntent();
        title = findViewById(R.id.restitle);
        titleTxt = intent.getStringExtra("title");
        title.setText(titleTxt);
        setList();

        numberView = findViewById(R.id.number);
        lv = (ListView) findViewById(R.id.lv);
        btnnext = (Button) findViewById(R.id.next);
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RestaurantActivity.this,cartActivity.class));
            }
        });


        modelArrayList = getModel();
        customAdapter = new CustomAdapter(this);
        lv.setAdapter(customAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Food food = new Food(current[position],pricelist[position]);
                FirebaseDatabase.getInstance().getReference("Cart").child(food.getFruit()).setValue(food).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Toast.makeText(RestaurantActivity.this, "Added To Cart", Toast.LENGTH_LONG).show();


                    }
                });

            }
        });



    }


    void setList() {

        if (titleTxt.equals("Thai Restaurant")) current = fruitlist;
        else if (titleTxt.equals("Noodles Shop")) current = dlist;
        else if (titleTxt.equals("Western House")) current = clist;
        else if (titleTxt.equals("Mamak Restaurant")) current = blist;
        else if (titleTxt.equals("Sushi King"))current = alist;

    }
    private ArrayList<Food> getModel(){
        ArrayList<Food> list = new ArrayList<>();
        for(int i = 0; i < 5; i++){

            Food model = new Food();

            model.setFruit(current[i]);
            model.setPrice(pricelist[i]);
            list.add(model);
        }
        return list;

    }

}
