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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class ToolsDamageActivity extends AppCompatActivity {

    ImageView  image_view;
    TextView tv_tool_name,tv_price,tv_booked_by;
    EditText et_penalty,et_penalty_reason;
    SharedPreferences sharedPreferences;
    String session;
    Button btn_damage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools_damage);

        sharedPreferences =getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("user_name", "def-val");


        getSupportActionBar().setTitle("Damage Tool");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        image_view=(ImageView)findViewById(R.id.image_view);
        Glide.with(ToolsDamageActivity.this).load(getIntent().getStringExtra("image")).into(image_view);


        tv_tool_name=(TextView)findViewById(R.id.tv_tool_name);
        tv_price=(TextView)findViewById(R.id.tv_price);
        tv_booked_by=(TextView)findViewById(R.id.tv_booked_by);

        tv_tool_name.setText(getIntent().getStringExtra("name"));
        tv_price.setText(getIntent().getStringExtra("price"));
        tv_booked_by.setText(getIntent().getStringExtra("booked_by"));

        et_penalty=(EditText)findViewById(R.id.et_penalty);
        et_penalty_reason=(EditText)findViewById(R.id.et_penalty_reason);

        btn_damage=(Button)findViewById(R.id.btn_damage);
        btn_damage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tooldamage();
                //Toast.makeText(ToolsDamageActivity.this, ""+getIntent().getStringExtra("booked_by"), Toast.LENGTH_SHORT).show();

            }
        });
    }
    ProgressDialog loadingBar;

    private DatabaseReference ProductsRef;
    public  void tooldamage(){
        loadingBar=new ProgressDialog(ToolsDamageActivity.this);
        loadingBar.setTitle("Please Wait data is being Loaded");
        loadingBar.show();
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (!(dataSnapshot.child("Penalty").child(getIntent().getStringExtra("booked_by")).child(getIntent().getStringExtra("pid")).exists()))
                {

                    HashMap<String, Object> productMap = new HashMap<>();
                    productMap.put("pid", getIntent().getStringExtra("pid"));
                    productMap.put("image",getIntent().getStringExtra("image"));
                    productMap.put("name",getIntent().getStringExtra("name"));
                    productMap.put("tool_owner",session);
                    productMap.put("booked_by",getIntent().getStringExtra("booked_by"));
                    productMap.put("damage_penalty",et_penalty.getText().toString());
                    productMap.put("penalty_reason",et_penalty_reason.getText().toString());

                    RootRef.child("Penalty").child(getIntent().getStringExtra("booked_by")).child( getIntent().getStringExtra("pid")).updateChildren(productMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(ToolsDamageActivity.this, "Penalty Added Succussfully.", Toast.LENGTH_SHORT).show();
                                        finish();
                                        // loadingBar.dismiss();
                                    }
                                    else
                                    {
                                        loadingBar.dismiss();
                                        Toast.makeText(ToolsDamageActivity.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                                    }

                                }

                            });
                }
                else
                {
                    Toast.makeText(ToolsDamageActivity.this, "You have already added Penalty for this product.", Toast.LENGTH_SHORT).show();
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