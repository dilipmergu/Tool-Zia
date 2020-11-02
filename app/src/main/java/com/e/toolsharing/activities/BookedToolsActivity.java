package com.e.toolsharing.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.e.toolsharing.R;
import com.e.toolsharing.adapters.BookedToolsAdapter;
import com.e.toolsharing.models.MyToolsPojo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BookedToolsActivity extends AppCompatActivity {
    ListView list_view;
    List<MyToolsPojo> a1;
    SharedPreferences sharedPreferences;
    String session;
    ProgressDialog progressDialog;
    BookedToolsAdapter bookedToolsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_tools);


        getSupportActionBar().setTitle("Booked by Others");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("user_name","def-val");

        a1= new ArrayList<>();
        list_view=(ListView)findViewById(R.id.list_view);
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Please Wait data is being Loaded");
        progressDialog.show();
        Query query= FirebaseDatabase.getInstance().getReference("Products")
                .orderByChild("posted_by").equalTo(session);
        query.addListenerForSingleValueEvent(valueEventListener);
    }
    ValueEventListener valueEventListener=new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            progressDialog.dismiss();
            a1.clear();
            if(dataSnapshot.exists()){
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    MyToolsPojo myToolsPojo=snapshot.getValue(MyToolsPojo.class);
                    //a1.add(myToolsPojo);
                    if(!(myToolsPojo.getStatus().equals("Available"))){
                        a1.add(myToolsPojo);
                    }

                }
               // Toast.makeText(getContext(),""+a1.size(),Toast.LENGTH_SHORT).show();
                bookedToolsAdapter = new BookedToolsAdapter(a1,BookedToolsActivity.this);
                list_view.setAdapter(bookedToolsAdapter);

            }
            else{
                Toast.makeText(BookedToolsActivity.this,"No data Found",Toast.LENGTH_SHORT).show();

            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
           progressDialog.dismiss();
        }
    };
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}