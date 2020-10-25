package com.e.toolsharing.adapters;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.e.toolsharing.R;
import com.e.toolsharing.activities.ToolDetailsActivity;
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
        CardView cvParent=(CardView)obj2.findViewById(R.id.cvParent);
        cvParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(cnt, ToolDetailsActivity.class);
                intent.putExtra("name", ar.get(pos).getName().toString());
                intent.putExtra("price", ar.get(pos).getPrice().toString());
                intent.putExtra("description", ar.get(pos).getDesc().toString());
                intent.putExtra("condition", ar.get(pos).getCondition().toString());
                intent.putExtra("status", ar.get(pos).getStatus().toString());
                intent.putExtra("category", ar.get(pos).getCategory().toString());
                intent.putExtra("image", ar.get(pos).getImage().toString());
                intent.putExtra("pid", ar.get(pos).getPid().toString());
                intent.putExtra("posted_by", ar.get(pos).getPosted_by().toString());
                intent.putExtra("booked_by", "");
                intent.putExtra("date", ar.get(pos).getDate().toString());
                intent.putExtra("time", ar.get(pos).getTime().toString());
                cnt.startActivity(intent);
            }
        });


        return obj2;






    }
}