package com.e.toolsharing.adapters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.e.toolsharing.R;
import com.e.toolsharing.models.HomeDataPojo;

import java.util.List;

public class MyFavouritesAdapter extends BaseAdapter {

    List<HomeDataPojo> ar;
    Context cnt;

    public MyFavouritesAdapter(List<HomeDataPojo> ar,Context cnt){
        this.ar=ar;
        this.cnt=cnt;
    }

    @Override
    public int getCount() {
        return ar.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup parent) {
        LayoutInflater obj1=(LayoutInflater)cnt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View obj2 = obj1.inflate(R.layout.adapter_my_favourite,null);

        ImageView image_view =(ImageView)obj2.findViewById(R.id.image_view);
        Glide.with(cnt).load(ar.get(pos).getImage()).into(image_view);

        TextView tv_cname = (TextView)obj2.findViewById(R.id.tv_cname);
        tv_cname.setText(ar.get(pos).getName());

        return obj2;






    }
}