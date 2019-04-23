package com.example.jiahan.foodhouse;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class recycleAdapter extends RecyclerView.Adapter<recycleAdapter.MyHolder>{

    List<Restaurant> list;
    Context context;



    public recycleAdapter(List<Restaurant> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.restaurant_card,parent,false);

        MyHolder myHolder = new MyHolder(view);


        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int i) {
        Restaurant mylist = list.get(i);
        holder.title.setText(mylist.getTitle());
        holder.desc.setText(mylist.getDesc());
        holder.type.setText(mylist.getType());
        holder.price.setText(mylist.getPrice());
        holder.current = mylist;
        if (mylist.getTitle().equals("Thai Restaurant")) {
           holder.img.setImageResource(R.drawable.thai);
        }
        else if(mylist.getTitle().equals("Noodles Shop")){
            holder.img.setImageResource(R.drawable.noodles);

        }
        else if(mylist.getTitle().equals("Western House")){
            holder.img.setImageResource(R.drawable.west);

        }
        else if(mylist.getTitle().equals("Mamak Restaurant")){
            holder.img.setImageResource(R.drawable.mamak);

        }
        else if(mylist.getTitle().equals("Sushi King")){
            holder.img.setImageResource(R.drawable.japan);
        }
        else{
            holder.img.setImageResource(R.drawable.logo);
        }

    }
    
    @Override
    public int getItemCount() {

        int arr = 0;

        try{
            if(list.size()==0){
                arr = 0;
            }
            else{
                arr=list.size();
            }

        }catch (Exception e){

        }

        return arr;

    }

    class MyHolder extends RecyclerView.ViewHolder{
        TextView title,desc,type,price;
        ImageView img;
        public View view;
        public Restaurant current;

        public MyHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            desc= (TextView) itemView.findViewById(R.id.desc);
            type= (TextView) itemView.findViewById(R.id.type);
            price = (TextView) itemView.findViewById(R.id.price);
            img = itemView.findViewById(R.id.img);
            view = itemView;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent(context,RestaurantActivity.class);
                    i.putExtra("title",current.getTitle());
                    context.startActivity(i);
                }
            });
        }
    }

}