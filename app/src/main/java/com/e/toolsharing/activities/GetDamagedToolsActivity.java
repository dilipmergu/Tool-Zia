package com.e.toolsharing.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.e.toolsharing.R;
import com.e.toolsharing.adapters.GetDamageToolssAdapter;
import com.e.toolsharing.models.PenaltyPojo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GetDamagedToolsActivity extends AppCompatActivity {
    ListView list_view;
    List<PenaltyPojo> a1;
    SharedPreferences sharedPreferences;
    String session;
    ProgressDialog progressDialog;
    GetDamageToolssAdapter getDamageToolssAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_damaged_tools);
        sharedPreferences =getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("user_name", "def-val");

        getSupportActionBar().setTitle("Damaged Tools");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        a1= new ArrayList<>();
        list_view=(ListView)findViewById(R.id.list_view);
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Please Wait data is being Loaded");
        progressDialog.show();
        Query query = FirebaseDatabase.getInstance().getReference("Penalty").child(session)
                .orderByChild("booked_by")
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
                    PenaltyPojo penaltyPojo = snapshot.getValue(PenaltyPojo.class);
                    a1.add(penaltyPojo);
                }
                getDamageToolssAdapter = new GetDamageToolssAdapter(a1, GetDamagedToolsActivity.this);
                list_view.setAdapter(getDamageToolssAdapter);

            }
            else {
                Toast.makeText(GetDamagedToolsActivity.this, "No data Found", Toast.LENGTH_SHORT).show();
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