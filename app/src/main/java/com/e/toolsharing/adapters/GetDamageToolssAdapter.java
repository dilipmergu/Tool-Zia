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
import com.e.toolsharing.models.PenaltyPojo;

import java.util.List;

public class GetDamageToolssAdapter extends BaseAdapter {

    List<PenaltyPojo> ar;
    Context cnt;

    public GetDamageToolssAdapter(List<PenaltyPojo> ar, Context cnt){
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
        View obj2 = obj1.inflate(R.layout.adapter_get_penalty,null);

        ImageView image_view =(ImageView)obj2.findViewById(R.id.image_view);
        Glide.with(cnt).load(ar.get(pos).getImage()).into(image_view);

        TextView tv_cname = (TextView)obj2.findViewById(R.id.tv_cname);
        tv_cname.setText("Tool Name  :"+ar.get(pos).getName());


        TextView tv_penalty_reason = (TextView)obj2.findViewById(R.id.tv_penalty_reason);
        tv_penalty_reason.setText("Reason for Penalty  :"+ar.get(pos).getPenalty_reason());

        TextView tv_penalty = (TextView)obj2.findViewById(R.id.tv_penalty);
        tv_penalty.setText("Penalty  :"+ar.get(pos).getDamage_penalty());




        return obj2;






    }
}