package com.e.toolsharing.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.e.toolsharing.R;
import com.e.toolsharing.models.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyProfileActivity extends AppCompatActivity {
    Button btn_update_profile, btn_logout;
    EditText et_name, et_phone, et_pwd, et_username,et_email;
    DatabaseReference dbArtists;
    Users users;
    ImageView image_view;
    SharedPreferences sharedPreferences;
    String session;
    ProgressDialog loadingBar;
    private String parentDbName = "Registered_users";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        getSupportActionBar().setTitle("My Profile");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        sharedPreferences =getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("user_name", "def-val");

        btn_update_profile = (Button) findViewById(R.id.btn_update_profile);
        loadingBar=new ProgressDialog(MyProfileActivity.this);

        et_name = (EditText) findViewById(R.id.et_name);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        et_username = (EditText) findViewById(R.id.et_username);
        et_email = (EditText) findViewById(R.id.et_email);
        image_view=(ImageView)findViewById(R.id.image_view);


        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(parentDbName).child(session).exists()){
                    Users usersData = snapshot.child(parentDbName).child(session).getValue(Users.class);
                    et_name.setText(usersData.getName());
                    et_email.setText(usersData.getEmail());
                    et_phone.setText(usersData.getPhone());
                    et_pwd.setText(usersData.getPassword());
                    et_username.setText(usersData.getUsername());
                    Glide.with(MyProfileActivity.this).load(usersData.getImage()).into(image_view);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference(parentDbName).child(session);
                Users users=new Users(et_name.getText().toString(),et_phone.getText().toString(),et_pwd.getText().toString(),session,et_email.getText().toString(),"https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQE8MJkjXHhwTEuZMhh0HdqsETJ4Uu_BjCITg&usqp=CAU");
                databaseReference.setValue(users);
                Toast.makeText(MyProfileActivity.this, "Profile Updated Succussfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


        /*progressDialog=new ProgressDialog(MyProfileActivity.this);
        progressDialog.setTitle("Please Wait data is beaging Loaded");
        progressDialog.show();
        Query query = FirebaseDatabase.getInstance().getReference("Registered_users")
                .orderByChild("username")
                .equalTo(session);
        query.addListenerForSingleValueEvent(valueEventListener);*/

    }
    /* ValueEventListener valueEventListener = new ValueEventListener() {
         @Override
         public void onDataChange(DataSnapshot dataSnapshot) {
             progressDialog.dismiss();
             //user.clear();
             if (dataSnapshot.exists()) {
                 for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                     Users user = snapshot.getValue(Users.class);
                    // homeDataPojos.add(artist);
                 }
                 Toast.makeText(MyProfileActivity.this, ""+users.getName(), Toast.LENGTH_SHORT).show();
                 //tv_name.setText(users.getName());

             }
             else {
                 Toast.makeText(MyProfileActivity.this, "No data Found", Toast.LENGTH_SHORT).show();
             }
         }
         @Override
         public void onCancelled(DatabaseError databaseError) {
             progressDialog.dismiss();

         }
     };*/
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