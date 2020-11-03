package com.e.toolsharing.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.e.toolsharing.R;
import com.e.toolsharing.models.ReviewsPojo;

import java.util.List;

public class ToolReviewAdapter extends BaseAdapter {
    List<ReviewsPojo> ar;
    private String parentDbName = "Products";
    Context cnt;

    public ToolReviewAdapter(List<ReviewsPojo> ar, Context cnt) {
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
        View obj2 = obj1.inflate(R.layout.adapter_tool_reviews, null);

        ImageView image_view=(ImageView)obj2.findViewById(R.id.image_view);
        Glide.with(cnt).load(ar.get(pos).getImage()).into(image_view);

        TextView tv_cname = (TextView) obj2.findViewById(R.id.tv_cname);
        tv_cname.setText("Tool Name  :"+ar.get(pos).getName());

        TextView tv_review = (TextView) obj2.findViewById(R.id.tv_review);
        tv_review.setText("Reviews  :"+ar.get(pos).getReview());

        RatingBar ratingBar=(RatingBar)obj2.findViewById(R.id.ratingBar);
        ratingBar.setRating(Float.parseFloat(ar.get(pos).getRating()));


        return obj2;
    }
}
