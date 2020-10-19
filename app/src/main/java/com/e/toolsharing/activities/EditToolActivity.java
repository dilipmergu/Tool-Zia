package com.e.toolsharing.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.e.toolsharing.R;
import com.e.toolsharing.models.MyToolsPojo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

public class EditToolActivity extends AppCompatActivity {
    EditText et_name,et_price,et_desc;
    ImageView image_view;
    Spinner spin_status,spin_condition;
    Button btn_update;
    private String parentDbName = "Products";
    SharedPreferences sharedPreferences;
    String session;
    String[] status,tool_condition;
    String bookedby="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tool);
        sharedPreferences =getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("user_name", "def-val");

        status=getResources().getStringArray(R.array.status);
        tool_condition=getResources().getStringArray(R.array.category);


        et_name=(EditText)findViewById(R.id.et_name);
        spin_status=(Spinner)findViewById(R.id.spin_status);
        et_desc=(EditText)findViewById(R.id.et_desc);
        et_price=(EditText)findViewById(R.id.et_price);
        spin_condition=(Spinner)findViewById(R.id.spin_condition);
        image_view=(ImageView)findViewById(R.id.image_view);
        btn_update=(Button)findViewById(R.id.btn_update);

        et_name.setText(getIntent().getStringExtra("name"));
        et_price.setText(getIntent().getStringExtra("price"));
        et_desc.setText(getIntent().getStringExtra("description"));

        int tool_status = new ArrayList<String>(Arrays.asList(status)).indexOf(getIntent().getStringExtra("status"));
        spin_status.setSelection(tool_status);

        int tool_cond = new ArrayList<String>(Arrays.asList(tool_condition)).indexOf(getIntent().getStringExtra("condition"));
        spin_condition.setSelection(tool_cond);
        Glide.with(EditToolActivity.this).load(getIntent().getStringExtra("image")).into(image_view);


        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference(parentDbName).child(getIntent().getStringExtra("pid"));
                MyToolsPojo edittools=new MyToolsPojo(getIntent().getStringExtra("time"),getIntent().getStringExtra("image"),et_name.getText().toString(),getIntent().getStringExtra("category"),et_price.getText().toString()
                        ,et_desc.getText().toString(),getIntent().getStringExtra("condition"),spin_status.getSelectedItem().toString(),session,getIntent().getStringExtra("booked_by")
                        ,getIntent().getStringExtra("date"),getIntent().getStringExtra("pid"));
                databaseReference.setValue(edittools);
                Toast.makeText(EditToolActivity.this, "Tool Updated Succussfully", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

    }
}