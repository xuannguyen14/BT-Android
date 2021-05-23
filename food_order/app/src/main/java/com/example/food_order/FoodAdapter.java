package com.example.food_order;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class FoodAdapter extends BaseAdapter {


    private Context context;
    private int layout;
    private List<Food> foodList;

    private int REQUEST_CODE = 1;

    public FoodAdapter(Context context, int layout, List<Food> foodList) {
        this.context = context;
        this.layout = layout;
        this.foodList = foodList;
    }

    @Override
    public int getCount() {
        return foodList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        ImageView imgFoodBig;
        TextView txtNameBig;
        ImageView imgIcon;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder = new ViewHolder();

            holder.txtNameBig = (TextView) convertView.findViewById(R.id.txtFoodname);
            holder.imgFoodBig = (ImageView) convertView.findViewById(R.id.imageFoodBig);
            holder.imgIcon = (ImageView) convertView.findViewById(R.id.iconimage);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Food food = foodList.get(position);
        holder.txtNameBig.setText(food.getNameFoodBig());
        holder.imgFoodBig.setImageResource(food.getImageFoodBig());

        String search = food.getNameFoodBig();
        search = search.replace(' ', '+');
        String finalSearch = search;

        holder.imgFoodBig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toWebsite = new Intent(Intent.ACTION_VIEW);
                toWebsite.setData(Uri.parse("https://www.google.com/search?q=" + finalSearch));
                context.startActivity(toWebsite);
            }
        });

        holder.txtNameBig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse(foodList.get(position).getDetails()));
//                context.startActivity(intent);

                Intent explicitIntent = new Intent(context, InforFoodActivity.class);
                String url = food.getDetails();

                explicitIntent.putExtra("url", url);

                context.startActivity(explicitIntent);
            }
        });

        holder.imgIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialogbig_custom);
                ImageView imageZoom = (ImageView) dialog.findViewById(R.id.imageFoodSmall);
                imageZoom.setImageResource(food.getImageFoodSmall());
                dialog.show();
            }
        });

        return convertView;
    }

    private void dialogImage(){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialogbig_custom);
        dialog.show();
    }

}
