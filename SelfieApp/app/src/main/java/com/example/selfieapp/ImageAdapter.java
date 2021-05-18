package com.example.selfieapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ImageAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<SelfieImage> images;

    public ImageAdapter(Context context, int layout, List<SelfieImage> images) {
        this.context = context;
        this.layout = layout;
        this.images = images;
    }

    @Override
    public int getCount() { // tra ve so dong hien thi trong listview
        return images.size();
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
        ImageView imgSelfie;
        TextView txtName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) { // tra ve moi dong tren item khi goi Adapter
        ViewHolder holder;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder = new ViewHolder();

            // anh xa convertView
            holder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            holder.imgSelfie = (ImageView) convertView.findViewById(R.id.imgSelfie);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        // gan gia tri
        SelfieImage selfieImage = images.get(position);
        holder.txtName.setText(selfieImage.getName());
        holder.imgSelfie.setImageBitmap(selfieImage.getImage());

        return convertView;
    }

}
