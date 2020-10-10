package com.e.toolsharing.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.e.toolsharing.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class RegistrationActivity  extends AppCompatActivity {
    TextView tv_name,tv_phone,tv_email,tv_user_name,tv_password;
    EditText et_name,et_phone,et_email,et_uname,et_PWD;
    Button btn_register,btn_img_upload;
    private ProgressDialog loadingBar;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private StorageReference ProductImagesRef;
    String downloadImageUrl;
    ImageView image_view;
    String name,phone,email,password,username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("Users");
        loadingBar = new ProgressDialog(RegistrationActivity.this);

        getSupportActionBar().setTitle("Registration");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        tv_name=(TextView)findViewById(R.id.btn_register);
        tv_phone=(TextView)findViewById(R.id.tv_phone);
        tv_email=(TextView)findViewById(R.id.tv_email);
        tv_user_name=(TextView)findViewById(R.id.tv_user_name);
        tv_password=(TextView)findViewById(R.id.tv_password);

        et_name=(EditText)findViewById(R.id.et_name);
        et_phone=(EditText)findViewById(R.id.et_phone);
        et_email=(EditText)findViewById(R.id.et_email);
        et_PWD=(EditText)findViewById(R.id.et_PWD);
        et_uname=(EditText)findViewById(R.id.et_uname);
        image_view=(ImageView)findViewById(R.id.image_view);


        btn_register=(Button)findViewById(R.id.btn_register);
        btn_img_upload=(Button)findViewById(R.id.btn_img_upload);
        btn_img_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OpenGallery();
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateProductData();
               /* Intent intent=new Intent(RegistrationActivity.this,LoginActivity.class);
                startActivity(intent);*/



            }
        });
        Typeface basicdt=Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/Lato-Medium.ttf");
        btn_register.setTypeface(basicdt);
        tv_name.setTypeface(basicdt);
        et_name.setTypeface(basicdt);
        tv_phone.setTypeface(basicdt);
        tv_email.setTypeface(basicdt);
        tv_password.setTypeface(basicdt);
        tv_user_name.setTypeface(basicdt);
        et_phone.setTypeface(basicdt);
        et_email.setTypeface(basicdt);
        et_uname.setTypeface(basicdt);
        et_PWD.setTypeface(basicdt);

    }
    private void OpenGallery()
    {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GalleryPick  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            ImageUri = data.getData();
            image_view.setImageURI(ImageUri);
        }
    }
    private void ValidateProductData()
    {
         name = et_name.getText().toString();
         phone = et_phone.getText().toString();
         email = et_email.getText().toString();
         password = et_PWD.getText().toString();
         username = et_uname.getText().toString();

        if (ImageUri == null)
        {
            Toast.makeText(RegistrationActivity.this, "Product image is mandatory...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(name))
        {
            Toast.makeText(this, "Please write your name...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please write your Email...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "Please write your phone number...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(username))
        {
            Toast.makeText(this, "Please Choose your Username...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        }

        else
        {
            StoreProductInformation();
        }
    }
    private void StoreProductInformation()
    {
        loadingBar.setTitle("Create Account");
        loadingBar.setMessage("Please wait, while we are checking the credentials.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        final StorageReference filePath = ProductImagesRef.child(ImageUri.getLastPathSegment()+ ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);


        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String message = e.toString();
                Toast.makeText(RegistrationActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(RegistrationActivity.this, "Profile Picture uploaded Successfully...", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();
                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if (task.isSuccessful())
                        {
                            downloadImageUrl = task.getResult().toString();

                            //Toast.makeText(RegistrationActivity.this, "Profile image Url Successfully...", Toast.LENGTH_SHORT).show();

                            ValidateDetails();
                        }
                    }
                });
            }
        });
    }
    private void ValidateDetails() {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (!(dataSnapshot.child("Registered_users").child(username).exists()))
                {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("image", downloadImageUrl);
                    userdataMap.put("name", name);
                    userdataMap.put("phone", phone);
                    userdataMap.put("email", email);
                    userdataMap.put("username", username);
                    userdataMap.put("password", password);


                    RootRef.child("Registered_users").child(username).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(RegistrationActivity.this, "Congratulations, your account has been created.", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                      /*Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                                        startActivity(intent);*/
                                        finish();
                                    }
                                    else
                                    {
                                        loadingBar.dismiss();
                                        Toast.makeText(RegistrationActivity.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
                else
                {
                    Toast.makeText(RegistrationActivity.this, "This " + username + " already exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(RegistrationActivity.this, "Please try again using another Email.", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RegistrationActivity.this, RegistrationActivity.class);
                    startActivity(intent);
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
