package com.e.toolsharing.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.e.toolsharing.R;
import com.e.toolsharing.models.MyToolsPojo;

import java.util.List;

public class MyBookedToolsAdapter extends BaseAdapter {
    List<MyToolsPojo> ar;
    private String parentDbName = "Products";
    Context cnt;

    public MyBookedToolsAdapter(List<MyToolsPojo> ar, Context cnt) {
        this.ar = ar;
        this.cnt = cnt;
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
        View obj2 = obj1.inflate(R.layout.adapter_my_booked_tools, null);

        ImageView image_view=(ImageView)obj2.findViewById(R.id.image_view);
        Glide.with(cnt).load(ar.get(pos).getImage()).into(image_view);

        TextView tv_cname = (TextView) obj2.findViewById(R.id.tv_cname);
        tv_cname.setText(ar.get(pos).getName());

        TextView tv_booked_by = (TextView) obj2.findViewById(R.id.tv_booked_by);
        tv_booked_by.setText("Booked By : "+ar.get(pos).getBooked_by());

        TextView tv_price = (TextView) obj2.findViewById(R.id.tv_price);
        tv_price.setText("Price : "+ar.get(pos).getPrice());

        TextView tv_status = (TextView) obj2.findViewById(R.id.tv_status);

        if(ar.get(pos).getApprove_by_owner() ==null){
            tv_status.setText("Status : "+"Pending");

        }
        else if(ar.get(pos).getApprove_by_owner().isEmpty()){
            tv_status.setText("Status : "+"Pending");
        }
        else
        {
            tv_status.setText("Status : "+ar.get(pos).getApprove_by_owner());
        }

        return obj2;
    }
}
