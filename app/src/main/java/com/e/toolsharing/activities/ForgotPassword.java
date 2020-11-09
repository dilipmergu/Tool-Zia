package com.e.toolsharing.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.e.toolsharing.BuildConfig;
import com.e.toolsharing.MainActivity;
import com.e.toolsharing.R;
import com.e.toolsharing.adapters.GMailSender;
import com.e.toolsharing.adapters.JSSEProvider;
import com.e.toolsharing.models.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;

public class ForgotPassword extends AppCompatActivity {

    TextView tv_username,tv_emailid;
    EditText et_USERNAME,et_EMAILID;
    Button submit;
    private String parentDbName = "Registered_users";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        getSupportActionBar().setTitle("Forgot Password");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv_username=(TextView)findViewById(R.id.tv_username);
        tv_emailid=(TextView)findViewById(R.id.tv_emailid);
        submit=(Button) findViewById(R.id.btn_submit);
        et_USERNAME=(EditText) findViewById(R.id.et_USERNAME);
        et_EMAILID=(EditText)findViewById(R.id.tv_EMAILID);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotpassword(et_USERNAME.getText().toString(),et_EMAILID.getText().toString());
            }
        });



    }

    private void forgotpassword(String username, String emailid) {

        if (TextUtils.isEmpty(username))
        {
            Toast.makeText(this, "Please write your Username...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(emailid))
        {
            Toast.makeText(this, "Please write your emailid...", Toast.LENGTH_SHORT).show();
        }
        else {
            sendemail(username,emailid);
        }

    }

    private void sendemail(final String username, final String emailid) {

        final String fromEmail = "stoolshare@gmail.com";
        final String fromPassword = "toolsharing@123";

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(parentDbName).child(username).exists()){
                    Users usersData = snapshot.child(parentDbName).child(username).getValue(Users.class);
                    if(usersData.getUsername().equals(username)){
                        if(usersData.getEmail().equals(emailid)){
                            String  toEmails = emailid;
                            List<String> toEmailList = Arrays.asList(toEmails
                                    .split("\\s*,\\s*"));
                            String emailSubject = "password reset toolzia application";
                            String emailBody = "Please use the following password :"+usersData.getPassword();
                            new JSSEProvider(ForgotPassword.this).execute(fromEmail,
                                    fromPassword, toEmailList, emailSubject, emailBody);
                        }
                        else {
                            Toast.makeText(ForgotPassword.this, "emailid is incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                    Intent intent = new Intent(ForgotPassword.this,LoginActivity.class);
                    startActivity(intent);


                }
                else
                {
                    Toast.makeText(ForgotPassword.this, "Account with this " + username + " do not exists.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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