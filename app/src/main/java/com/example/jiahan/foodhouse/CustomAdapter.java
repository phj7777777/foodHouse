package com.example.jiahan.foodhouse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by hardik on 9/1/17.
 */
public class CustomAdapter  extends BaseAdapter {

    private Context context;


    public CustomAdapter(Context context) {

        this.context = context;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return RestaurantActivity.modelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return RestaurantActivity.modelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder(); LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.foodlist, null, true);



            holder.tvFruit = (TextView) convertView.findViewById(R.id.animal);
            holder.tvPrice = (TextView) convertView.findViewById(R.id.pricetxt);
            holder.tvnumber = (TextView) convertView.findViewById(R.id.number);
            holder.btn_plus = (Button) convertView.findViewById(R.id.plus);
            holder.btn_minus = (Button) convertView.findViewById(R.id.minus);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        holder.tvFruit.setText(RestaurantActivity.modelArrayList.get(position).getFruit());
        holder.tvPrice.setText("RM "+ String.valueOf(RestaurantActivity.modelArrayList.get(position).getPrice()));
        holder.tvnumber.setText("0");


        holder.btn_plus.setTag(R.integer.btn_plus_view, convertView);
        holder.btn_plus.setTag(R.integer.btn_plus_pos, position);
        holder.btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View tempview = (View) holder.btn_plus.getTag(R.integer.btn_plus_view);
                TextView tv = (TextView) tempview.findViewById(R.id.number);
                Integer pos = (Integer) holder.btn_plus.getTag(R.integer.btn_plus_pos);


                int number = Integer.parseInt(tv.getText().toString()) + 1;
                tv.setText(Integer.toString(number));

                RestaurantActivity.modelArrayList.get(pos).setNumber(number);

            }
        });

        holder.btn_minus.setTag(R.integer.btn_minus_view, convertView);
        holder.btn_minus.setTag(R.integer.btn_minus_pos, position);
        holder.btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View tempview = (View) holder.btn_minus.getTag(R.integer.btn_minus_view);
                TextView tv = (TextView) tempview.findViewById(R.id.number);
                Integer pos = (Integer) holder.btn_minus.getTag(R.integer.btn_minus_pos);
                int number = 0;
                if(Integer.parseInt(tv.getText().toString())>0){
                    number = Integer.parseInt(tv.getText().toString()) - 1;
                    tv.setText(Integer.toString(number));
                }


                RestaurantActivity.modelArrayList.get(pos).setNumber(number);

            }
        });

        return convertView;
    }

    private class ViewHolder {

        protected Button btn_plus, btn_minus;
        private TextView tvFruit, tvnumber, tvPrice;

    }

}