package com.e.toolsharing.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.e.toolsharing.R;
import com.e.toolsharing.adapters.MyToolsAdapter;
import com.e.toolsharing.models.HomeDataPojo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyToolsActivity extends AppCompatActivity {
    GridView gridview;
    List<HomeDataPojo> a1;
    SharedPreferences sharedPreferences;
    String session;
    MyToolsAdapter myToolsAdapter;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tools);

        getSupportActionBar().setTitle("My Tools");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences =getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("user_name", "def-val");

        a1= new ArrayList<>();


        gridview=(GridView)findViewById(R.id.gridview);
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Please Wait data is being Loaded");
        progressDialog.show();
        Query query = FirebaseDatabase.getInstance().getReference("Products")
                .orderByChild("posted_by")
                .equalTo(session);
        query.addListenerForSingleValueEvent(valueEventListener);
    }
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            progressDialog.dismiss();
            a1.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HomeDataPojo homeDataPojo = snapshot.getValue(HomeDataPojo.class);
                    a1.add(homeDataPojo);
                }
                myToolsAdapter = new MyToolsAdapter(a1,MyToolsActivity.this);
                gridview.setAdapter(myToolsAdapter);

            }
            else {
                Toast.makeText(MyToolsActivity.this, "No data Found", Toast.LENGTH_SHORT).show();
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
