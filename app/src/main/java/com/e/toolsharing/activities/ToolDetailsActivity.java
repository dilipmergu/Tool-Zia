package com.e.toolsharing.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.e.toolsharing.R;
import com.e.toolsharing.models.StatusUpdatePojo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ToolDetailsActivity extends AppCompatActivity {
    TextView tv_name,tv_status,tv_price,tv_reviews;
    ImageView image_view;
    Button btn_book;
    SharedPreferences sharedPreferences;
    String session;
    String name,price,status,category,image,pid,posted_by,booked_by,date,time;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.details_tool);

         sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
         session = sharedPreferences.getString("user_name","def-val");

         getSupportActionBar().setTitle("Tool Booking");
         getSupportActionBar().setHomeButtonEnabled(true);
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);


         name=getIntent().getStringExtra("name");
         price=getIntent().getStringExtra("status");
         status="Not Available";
         category=getIntent().getStringExtra("category");
         image= getIntent().getStringExtra("image");
         pid=getIntent().getStringExtra("pid");
         posted_by=getIntent().getStringExtra("posted_by");
         booked_by=session;
         date=getIntent().getStringExtra("date");
         time=getIntent().getStringExtra("time");

         tv_name=(TextView)findViewById(R.id.tv_name);
         tv_status=(TextView)findViewById(R.id.tv_status);
         tv_price=(TextView)findViewById(R.id.tv_price);
         tv_reviews=(TextView)findViewById(R.id.tv_reviews);
         image_view=(ImageView)findViewById(R.id.image_view);

         tv_name.setText(" :"+getIntent().getStringExtra("name"));
         tv_status.setText(" :"+getIntent().getStringExtra("status"));
         tv_price.setText(" :"+getIntent().getStringExtra("price"));
         tv_reviews.setText(" :"+getIntent().getStringExtra("category"));

         Glide.with(ToolDetailsActivity.this).load(getIntent().getStringExtra("image")).into(image_view);
         btn_book=(Button)findViewById(R.id.btn_book);
         if(getIntent().getStringExtra("posted_by").equals(session)){
             btn_book.setVisibility(View.INVISIBLE);
         }
         else {
             btn_book.setVisibility(View.VISIBLE);
         }
         btn_book.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {


                 DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Products").child(getIntent().getStringExtra("pid"));
                 StatusUpdatePojo statusUpdatePojo=new StatusUpdatePojo(pid,date,time,image,name,category,price,status,posted_by,booked_by);
                 databaseReference.setValue(statusUpdatePojo);
                 Toast.makeText(ToolDetailsActivity.this, "Tool Booked Succussfully", Toast.LENGTH_SHORT).show();
             }
         });
     }

     @Override
    public boolean onOptionsItemSelected(MenuItem item){
         switch (item.getItemId()){
             case android.R.id.home :
                 this.finish();
                 return true;
             default:
                 return super.onOptionsItemSelected(item);
         }

     }

}
