package com.e.toolsharing.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.e.toolsharing.R;
import com.e.toolsharing.models.StatusUpdatePojo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ToolDetailsActivity extends AppCompatActivity {
    TextView tv_name,tv_status,tv_price,tv_reviews,tv_desc,tv_condition,tv_category,tv_availability,tv_phno;
    ImageView image_view;
    Button btn_book;
    SharedPreferences sharedPreferences;
    String session;
    String name,price,status,category,image,pid,posted_by,booked_by,date,time,condition,description;
    TextView tv_to_date,tv_from_date;
    EditText et_price;
    int mYear,mMonth,mDay;
    String DAY,MONTH,YEAR;
    Button btn_get_price,btn_get_reviews;
    LinearLayout ll;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_tool);

        sharedPreferences =getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("user_name", "def-val");


        getSupportActionBar().setTitle("Tool Booking");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        name=getIntent().getStringExtra("name");
        price=getIntent().getStringExtra("price");
        status="Not Available";
        category=getIntent().getStringExtra("category");
        image=getIntent().getStringExtra("image");
        pid=getIntent().getStringExtra("pid");
        posted_by=getIntent().getStringExtra("posted_by");
        booked_by=session;
        date=getIntent().getStringExtra("date");
        time=getIntent().getStringExtra("time");
        description=getIntent().getStringExtra("description");
        condition=getIntent().getStringExtra("condition");

        btn_get_reviews=(Button)findViewById(R.id.btn_get_reviews);
        btn_get_reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ToolDetailsActivity.this,GetToolReviewsActivity.class);
                i.putExtra("pid",pid);
                startActivity(i);
            }
        });
        tv_name=(TextView)findViewById(R.id.tv_name);
        tv_status=(TextView)findViewById(R.id.tv_status);
        tv_price=(TextView)findViewById(R.id.tv_price);
        tv_phno = (TextView)findViewById(R.id.tv_phno);
        // tv_reviews=(TextView)findViewById(R.id.tv_reviews);
        tv_desc=(TextView)findViewById(R.id.tv_desc);
        tv_condition=(TextView)findViewById(R.id.tv_condition);
        image_view=(ImageView)findViewById(R.id.image_view);
        tv_category = (TextView) findViewById(R.id.tv_category);
        tv_availability=(TextView)findViewById(R.id.tv_availability);

        tv_to_date=(TextView)findViewById(R.id.tv_to_date);
        tv_from_date=(TextView)findViewById(R.id.tv_from_date);

        et_price=(EditText)findViewById(R.id.et_price);

        tv_from_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datepicker();
            }
        });
        tv_to_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datepicker1();
            }
        });

        tv_name.setText(" :"+getIntent().getStringExtra("name"));
        tv_status.setText(" :"+getIntent().getStringExtra("status"));
        tv_price.setText(" :"+getIntent().getStringExtra("price"));
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference users = ref.child("Registered_users").child(posted_by).child("phone");
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String phone = dataSnapshot.getValue(String.class);
                tv_phno.setText(" :"+phone);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        tv_desc.setText(" :"+getIntent().getStringExtra("description"));
        tv_condition.setText(" :"+getIntent().getStringExtra("condition"));
        tv_category.setText(" :"+getIntent().getStringExtra("category"));
        tv_availability.setText(":Tool is Available From :"+getIntent().getStringExtra("to_date"));
        // tv_reviews.setText(" : ****");

        Glide.with(ToolDetailsActivity.this).load(getIntent().getStringExtra("image")).into(image_view);

        ll=(LinearLayout)findViewById(R.id.ll);
        btn_book=(Button)findViewById(R.id.btn_book);
        if (getIntent().getStringExtra("posted_by").equals(session)||getIntent().getStringExtra("status").equals("Not Available")) {
            ll.setVisibility(View.INVISIBLE);
        }
        else  {
            ll.setVisibility(View.VISIBLE);
        }
        btn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (tv_to_date.getText().toString().equals("From Date") || tv_to_date.getText().toString().equals("To Date")) {
                    Toast.makeText(ToolDetailsActivity.this, "Please choose From and To Date", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Products").child(getIntent().getStringExtra("pid"));
                    StatusUpdatePojo statusUpdatePojo = new StatusUpdatePojo(pid, date, time, image, name, category, price, description, condition, status, posted_by, booked_by, tv_from_date.getText().toString(), tv_to_date.getText().toString(), et_price.getText().toString());
                    databaseReference.setValue(statusUpdatePojo);
                    Toast.makeText(ToolDetailsActivity.this, "Tool Requested Succussfully", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    public void toolprice(){
        SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
        String dateBeforeString = tv_from_date.getText().toString();
        String dateAfterString = tv_to_date.getText().toString();
        String price=getIntent().getStringExtra("price");


        try {

            double doubleprice = Double.parseDouble(price);
            int value = (int)Math.round(doubleprice);


            Date dateBefore=myFormat.parse(dateBeforeString);
            Date dateAfter = myFormat.parse(dateAfterString);
            long difference= dateAfter.getTime() - dateBefore.getTime();
            float daysBetween = (difference/(1000*60*60*24));// 5/11 to 7/11 2*1000*60*60*24/1000*60*60*24
            if (daysBetween<0.0){

                Toast.makeText(ToolDetailsActivity.this, "to day should be from today"+daysBetween, Toast.LENGTH_SHORT).show();
            }else if (daysBetween== 1.0){
                daysBetween = (float) 1.0;
            }
            final double calculateamount = daysBetween *value;

            btn_get_price=(Button)findViewById(R.id.btn_get_price);
            btn_get_price.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Toast.makeText(ToolDetailsActivity.this, ""+calculateamount,Toast.LENGTH_SHORT).show();
                    et_price.setText(""+calculateamount);
                }
            });

        } catch (ParseException e) {
            Toast.makeText(this,""+e,Toast.LENGTH_SHORT).show();
        }
    }

    private void datepicker1() {

        final Calendar c= Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay= c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog=new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        DAY = dayOfMonth + " ";
                        MONTH = monthOfYear + 1 + "";
                        YEAR = year + "";

                        tv_to_date.setText(dayOfMonth+"-"+(monthOfYear +1)+"-"+year);
                        toolprice();
                    }
                },mYear,mMonth,mDay);
        datePickerDialog.show();
    }


    private void datepicker() {

        final Calendar c= Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth= c.get(Calendar.MONTH);
        mDay=c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog= new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        DAY = dayOfMonth + " ";
                        MONTH = monthOfYear + 1 +"";
                        YEAR = year + "";

                        tv_from_date.setText(dayOfMonth + "-"+(monthOfYear + 1)+"-"+year);

                    }
                },mYear,mMonth,mDay);
        datePickerDialog.show();
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
