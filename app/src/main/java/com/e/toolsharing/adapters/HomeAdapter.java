package com.e.toolsharing.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.e.toolsharing.R;
import com.e.toolsharing.activities.ToolDetailsActivity;
import com.e.toolsharing.models.HomeDataPojo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

public class HomeAdapter extends BaseAdapter {
    List<HomeDataPojo> ar;
    Context cnt;
    String session;

    public HomeAdapter(Context cnt, List<HomeDataPojo> ar, String session) {
        this.ar = ar;
        this.cnt = cnt;
        this.session=session;
    }

    @Override
    public int getCount() {
        return ar.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int pos, View view, ViewGroup viewGroup) {
        LayoutInflater obj1 = (LayoutInflater) cnt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View obj2 = obj1.inflate(R.layout.adapter_home, null);

        ImageView image_view=(ImageView)obj2.findViewById(R.id.image_view);
        Glide.with(cnt).load(ar.get(pos).getImage()).into(image_view);

        TextView tv_cname = (TextView) obj2.findViewById(R.id.tv_cname);
        tv_cname.setText(ar.get(pos).getName());

        RatingBar tv_rating=(RatingBar)obj2.findViewById(R.id.tv_rating);

        CardView cvParent=(CardView)obj2.findViewById(R.id.cvParent);
        cvParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(cnt, ToolDetailsActivity.class);
                intent.putExtra("name", ar.get(pos).getName().toString());
                intent.putExtra("price", ar.get(pos).getPrice().toString());
                intent.putExtra("description", ar.get(pos).getDesc().toString());
                intent.putExtra("condition", ar.get(pos).getCondition().toString());
                intent.putExtra("status", ar.get(pos).getStatus().toString());
                intent.putExtra("category", ar.get(pos).getCategory().toString());
                intent.putExtra("image", ar.get(pos).getImage().toString());
                intent.putExtra("pid", ar.get(pos).getPid().toString());
                intent.putExtra("posted_by", ar.get(pos).getPosted_by().toString());
                intent.putExtra("booked_by", "");
                intent.putExtra("date", ar.get(pos).getDate().toString());
                intent.putExtra("time", ar.get(pos).getTime().toString());
                cnt.startActivity(intent);
            }
        });

        ImageView img_fav=(ImageView)obj2.findViewById(R.id.img_fav);
        img_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favList(ar.get(pos).getPid().toString(),ar.get(pos).getDate().toString(),ar.get(pos).getTime().toString()
                        ,ar.get(pos).getImage().toString(),ar.get(pos).getName().toString()
                        ,ar.get(pos).getCategory().toString(),ar.get(pos).getPrice().toString(),ar.get(pos).getDesc().toString(),
                        ar.get(pos).getCondition().toString(),ar.get(pos).getStatus().toString(),ar.get(pos).getPosted_by());
            }
        });

        return obj2;
    }
    ProgressDialog loadingBar;

    private DatabaseReference ProductsRef;
    public  void favList(final String id,final String date,final String time,final String image
            ,final String name,final String category,final String price,final String desc,final String condition,final String status,final String posted_by){
        loadingBar=new ProgressDialog(cnt);
        loadingBar.setTitle("Please Wait data is being Loaded");
        loadingBar.show();
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (!(dataSnapshot.child("Fav Products").child(session).child(id).exists()))
                {
                    HashMap<String, Object> productMap = new HashMap<>();
                    productMap.put("pid", id);
                    productMap.put("date", date);
                    productMap.put("time", time);
                    productMap.put("image",image);
                    productMap.put("name",name);
                    productMap.put("category", category);
                    productMap.put("price", price);
                    productMap.put("desc",desc);
                    productMap.put("condition", condition);
                    productMap.put("status", status);
                    productMap.put("fav_list", session);
                    productMap.put("posted_by", posted_by);
                    productMap.put("booked_by","");


                    RootRef.child("Fav Products").child(session).child(id).updateChildren(productMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(cnt, "Add to favourite successfully.", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                    }
                                    else
                                    {
                                        loadingBar.dismiss();
                                        Toast.makeText(cnt, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                                    }

                                }

                            });
                }
                else
                {
                    Toast.makeText(cnt, "This " + id + " already exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
