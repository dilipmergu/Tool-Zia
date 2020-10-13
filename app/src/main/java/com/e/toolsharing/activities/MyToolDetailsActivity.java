package com.e.toolsharing.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.e.toolsharing.R;

import okhttp3.internal.Util;

public class MyToolDetailsActivity extends AppCompatActivity {

    TextView tv_name,tv_status,tv_price,tv_category;
    ImageView image_view;
    Button btn_book;
    SharedPreferences sharedPreferences;
    String session;
    String name,price,status,category,image,pid,posted_by,booked_by,date,time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_tool_details);

        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("user_name","def-val");

        getSupportActionBar().setTitle("Tool Details");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_name=(TextView)findViewById(R.id.tv_name);
        tv_status=(TextView)findViewById(R.id.tv_status);
        tv_price=(TextView)findViewById(R.id.tv_price);
        tv_category=(TextView)findViewById(R.id.tv_category);
        image_view=(ImageView) findViewById(R.id.image_view);

        tv_name.setText(" :"+getIntent().getStringExtra("name"));
        tv_status.setText(" :"+getIntent().getStringExtra("status"));
        tv_price.setText(" :"+getIntent().getStringExtra("price"));
        tv_category.setText(" :"+getIntent().getStringExtra("category"));


        Glide.with(MyToolDetailsActivity.this).load(getIntent().getStringExtra("image")).into(image_view);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId())
        {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }





}
