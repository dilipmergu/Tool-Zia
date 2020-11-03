package com.e.toolsharing.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.e.toolsharing.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ToolReviewActivity extends AppCompatActivity {
    TextView tv_name,tv_status,tv_price,tv_reviews,tv_desc,tv_condition,tv_category;
    ImageView image_view;
    Button btn_book;
    SharedPreferences sharedPreferences;
    String session,rating_num;
    RatingBar ratingBar;
    EditText et_tool_review;
    Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_review);

        sharedPreferences =getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("user_name", "def-val");


        getSupportActionBar().setTitle("Tool Review");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_name=(TextView)findViewById(R.id.tv_name);
        tv_status=(TextView)findViewById(R.id.tv_status);
        tv_price=(TextView)findViewById(R.id.tv_price);
        tv_reviews=(TextView)findViewById(R.id.tv_reviews);
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
        Glide.with(ToolReviewActivity.this).load(getIntent().getStringExtra("image")).into(image_view);

        ratingBar=(RatingBar)findViewById(R.id.ratingBar);
        et_tool_review=(EditText)findViewById(R.id.et_tool_review);
        btn_submit=(Button)findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toolreview();
                //Toast.makeText(ToolReviewActivity.this, ""+getIntent().getStringExtra("pid"), Toast.LENGTH_SHORT).show();
            }
        });

    }
    ProgressDialog loadingBar;

    private DatabaseReference ProductsRef;
    public  void toolreview(){
        loadingBar=new ProgressDialog(ToolReviewActivity.this);
        loadingBar.setTitle("Please Wait data is being Loaded");
        loadingBar.show();
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (!(dataSnapshot.child("Tool Reviews").child(getIntent().getStringExtra("pid")).child(session).exists()))
                {

                    HashMap<String, Object> productMap = new HashMap<>();
                    productMap.put("pid", getIntent().getStringExtra("pid"));
                    productMap.put("image",getIntent().getStringExtra("image"));
                    productMap.put("name",getIntent().getStringExtra("name"));
                    productMap.put("rating", String.valueOf(ratingBar.getRating()));
                    productMap.put("review", et_tool_review.getText().toString());

                    RootRef.child("Tool Reviews").child( getIntent().getStringExtra("pid")).child(session).updateChildren(productMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(ToolReviewActivity.this, "Thank you for the feedback.", Toast.LENGTH_SHORT).show();
                                        finish();
                                        // loadingBar.dismiss();
                                    }
                                    else
                                    {
                                        loadingBar.dismiss();
                                        Toast.makeText(ToolReviewActivity.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                                    }

                                }

                            });
                }
                else
                {
                    Toast.makeText(ToolReviewActivity.this, "You have already given feedback to this product.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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