package com.e.toolsharing.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.e.toolsharing.R;
import com.e.toolsharing.adapters.MyBookedToolsAdapter;
import com.e.toolsharing.adapters.ToolReviewAdapter;
import com.e.toolsharing.models.MyToolsPojo;
import com.e.toolsharing.models.ReviewsPojo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GetToolReviewsActivity extends AppCompatActivity {
    ListView list_view;
    List<ReviewsPojo> a1;
    SharedPreferences sharedPreferences;
    String session;
    ProgressDialog progressDialog;
    ToolReviewAdapter toolReviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_tool_reviews);

        getSupportActionBar().setTitle("Tool Reviews");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        sharedPreferences =getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("user_name", "def-val");

        a1= new ArrayList<>();
        list_view=(ListView)findViewById(R.id.list_view);
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Please Wait data is being Loaded");
        progressDialog.show();
        /*Query query = FirebaseDatabase.getInstance().getReference("Tool Reviews")
                .orderByChild("pid").equalTo(getIntent().getStringExtra("pid"));*/

        Query query = FirebaseDatabase.getInstance().getReference("Tool Reviews").child(getIntent().getStringExtra("pid"))
                .orderByChild("pid")
                .equalTo(getIntent().getStringExtra("pid"));

        query.addListenerForSingleValueEvent(valueEventListener);
    }
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            progressDialog.dismiss();
            a1.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ReviewsPojo reviewsPojo = snapshot.getValue(ReviewsPojo.class);
                    a1.add(reviewsPojo);
                    /*if ((myToolsPojo.getBooked_by().equals(session))) {
                        a1.add(myToolsPojo);
                    }*/
                }
                //Toast.makeText(getContext(), ""+a1.size(), Toast.LENGTH_SHORT).show();
                toolReviewAdapter = new ToolReviewAdapter(a1, GetToolReviewsActivity.this);
                list_view.setAdapter(toolReviewAdapter);

            }
            else {
                Toast.makeText(GetToolReviewsActivity.this, "No data Found", Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
            progressDialog.dismiss();

        }
    };
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