package com.e.toolsharing.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.e.toolsharing.R;
import com.e.toolsharing.models.StatusUpdatePojo;

public class MyToolDetailsActivity extends AppCompatActivity {
    TextView tv_name,tv_status,tv_price,tv_category,tv_condition,tv_description;
    ImageView image_view;
    Button btn_edit_tool,btn_damage;
    SharedPreferences sharedPreferences;
    String session;
    private String parentDbName = "Products";
    private DatabaseReference ProductsRef;
    String name,price,status,category,image,pid,posted_by,booked_by,date,time;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_tool_details);

        sharedPreferences =getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("user_name", "def-val");
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products").child(parentDbName);

        getSupportActionBar().setTitle("Tool Details");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_name=(TextView)findViewById(R.id.tv_name);
        tv_status=(TextView)findViewById(R.id.tv_status);
        tv_price=(TextView)findViewById(R.id.tv_price);
        tv_category=(TextView)findViewById(R.id.tv_category);
        tv_condition=(TextView)findViewById(R.id.tv_condition);
        tv_description=(TextView)findViewById(R.id.tv_description);
        image_view=(ImageView)findViewById(R.id.image_view);

        tv_name.setText(" :"+getIntent().getStringExtra("name"));
        tv_status.setText(" :"+getIntent().getStringExtra("status"));
        tv_price.setText(" :"+getIntent().getStringExtra("price"));
        tv_category.setText(" :"+getIntent().getStringExtra("category"));
        tv_condition.setText(" :"+getIntent().getStringExtra("condition"));
        tv_description.setText(" :"+getIntent().getStringExtra("description"));
        Glide.with(MyToolDetailsActivity.this).load(getIntent().getStringExtra("image")).into(image_view);
        btn_edit_tool=(Button)findViewById(R.id.btn_edit_tool);
        btn_edit_tool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MyToolDetailsActivity.this, EditToolActivity.class);
                intent.putExtra("name", getIntent().getStringExtra("name"));
                intent.putExtra("price", getIntent().getStringExtra("price"));
                intent.putExtra("description", getIntent().getStringExtra("description"));
                intent.putExtra("condition", getIntent().getStringExtra("condition"));
                intent.putExtra("status", getIntent().getStringExtra("status"));
                intent.putExtra("image", getIntent().getStringExtra("image"));
                intent.putExtra("pid", getIntent().getStringExtra("pid"));
                intent.putExtra("category", getIntent().getStringExtra("category"));
                intent.putExtra("posted_by", getIntent().getStringExtra("posted_by"));
                intent.putExtra("booked_by", "");
                intent.putExtra("date", getIntent().getStringExtra("date"));
                intent.putExtra("time", getIntent().getStringExtra("time"));
                intent.putExtra("from_date", getIntent().getStringExtra("from_date"));
                intent.putExtra("to_date", getIntent().getStringExtra("to_date"));
                intent.putExtra("total_price", getIntent().getStringExtra("total_price"));
                startActivity(intent);
            }
        });

//        btn_damage=(Button)findViewById(R.id.btn_damage);
//        btn_damage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent=new Intent(MyToolDetailsActivity.this,ToolsDamageActivity.class);
//                intent.putExtra("name",getIntent().getStringExtra("name"));
//                intent.putExtra("image",getIntent().getStringExtra("image"));
//                intent.putExtra("price",getIntent().getStringExtra("price"));
//                intent.putExtra("booked_by", getIntent().getStringExtra("booked_by"));
//                intent.putExtra("pid",getIntent().getStringExtra("pid"));
//                startActivity(intent);
//            }
//        });

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
