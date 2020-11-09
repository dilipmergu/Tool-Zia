package com.e.toolsharing.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.e.toolsharing.R;
import com.e.toolsharing.activities.ToolDetailsActivity;
import com.e.toolsharing.models.HomeDataPojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchAdapter extends BaseAdapter {
    List<HomeDataPojo> ar;
    List<HomeDataPojo> homeDataPojos;
    Context cnt;

    public SearchAdapter(List<HomeDataPojo> datapojo, Context cnt) {
        this.homeDataPojos=datapojo;
        this.cnt = cnt;
        this.ar = new ArrayList<HomeDataPojo>();
        this.ar.addAll(datapojo);
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
    public View getView(final int pos, View view, ViewGroup viewGroup) {
        LayoutInflater obj1 = (LayoutInflater) cnt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View obj2 = obj1.inflate(R.layout.adapter_home, null);

        ImageView image_view=(ImageView)obj2.findViewById(R.id.image_view);
        Glide.with(cnt).load(ar.get(pos).getImage()).into(image_view);

        TextView tv_cname = (TextView) obj2.findViewById(R.id.tv_cname);
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
                intent.putExtra("to_date",ar.get(pos).getTo_date().toString());
                cnt.startActivity(intent);
            }
        });

        return obj2;
    }
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        ar.clear();
        if (charText.length() == 0) {
            ar.addAll(homeDataPojos);
        }
        else
        {
            for (HomeDataPojo wp : homeDataPojos)
            {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    ar.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
