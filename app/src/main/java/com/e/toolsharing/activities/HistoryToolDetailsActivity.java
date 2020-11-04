package com.e.toolsharing.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.e.toolsharing.R;

public class HistoryToolDetailsActivity extends AppCompatActivity {
    TextView tv_name,tv_status,tv_price,tv_reviews,tv_desc,tv_condition,tv_category;
    ImageView image_view;
    Button btn_book;
    SharedPreferences sharedPreferences;
    String session,rating_num;
    RatingBar ratingBar;
    EditText et_tool_review;
    Button btn_rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_details);

        sharedPreferences =getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("user_name", "def-val");


        getSupportActionBar().setTitle("Tool Details");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_name=(TextView)findViewById(R.id.tv_name);
        tv_status=(TextView)findViewById(R.id.tv_status);
        tv_price=(TextView)findViewById(R.id.tv_price);
//        tv_reviews=(TextView)findViewById(R.id.tv_reviews);
        tv_desc=(TextView)findViewById(R.id.tv_desc);
        tv_condition=(TextView)findViewById(R.id.tv_condition);
        image_view=(ImageView)findViewById(R.id.image_view);
        tv_category = (TextView) findViewById(R.id.tv_category);





        tv_name.setText(" :"+getIntent().getStringExtra("name"));
        tv_status.setText(" :"+getIntent().getStringExtra("status"));
        tv_price.setText(" :"+getIntent().getStringExtra("price"));
        tv_desc.setText(" :"+getIntent().getStringExtra("description"));
        tv_condition.setText(" :"+getIntent().getStringExtra("condition"));
        tv_category.setText(" :"+getIntent().getStringExtra("category"));
        Glide.with(HistoryToolDetailsActivity.this).load(getIntent().getStringExtra("image")).into(image_view);

        ratingBar=(RatingBar)findViewById(R.id.ratingBar);
        et_tool_review=(EditText)findViewById(R.id.et_tool_review);
        btn_rating=(Button)findViewById(R.id.btn_rating);
        btn_rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(HistoryToolDetailsActivity.this, ToolReviewActivity.class);
                intent.putExtra("pid",getIntent().getStringExtra("pid"));
                intent.putExtra("name", getIntent().getStringExtra("name"));
                intent.putExtra("price", getIntent().getStringExtra("price"));
                intent.putExtra("description", getIntent().getStringExtra("description"));
                intent.putExtra("condition", getIntent().getStringExtra("condition"));
                intent.putExtra("status", getIntent().getStringExtra("status"));
                intent.putExtra("category", getIntent().getStringExtra("category"));
                intent.putExtra("image", getIntent().getStringExtra("image"));
                intent.putExtra("posted_by", getIntent().getStringExtra("posted_by"));
                intent.putExtra("date", getIntent().getStringExtra("date"));
                intent.putExtra("time", getIntent().getStringExtra("time"));
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}