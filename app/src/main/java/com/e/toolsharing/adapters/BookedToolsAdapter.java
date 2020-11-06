package com.e.toolsharing.adapters;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.e.toolsharing.R;
import com.e.toolsharing.activities.ToolDetailsActivity;
import com.e.toolsharing.models.MyToolsPojo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class BookedToolsAdapter extends BaseAdapter {
    List<MyToolsPojo> ar;
    private  String parentDbName = "Products";
    Context cnt;

    public  BookedToolsAdapter(List<MyToolsPojo> ar, Context cnt){
        this.ar = ar;
        this.cnt = cnt;
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
    public View getView(final int pos, View View, ViewGroup viewGroup) {
        LayoutInflater obj1 = (LayoutInflater)cnt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View obj2 = obj1.inflate(R.layout.activity_booked_tools_adapter,null);

        ImageView image_View=(ImageView)obj2.findViewById(R.id.image_view);
        Glide.with(cnt).load(ar.get(pos).getImage()).into(image_View);

        TextView tv_cname=(TextView)obj2.findViewById(R.id.tv_cname);
        tv_cname.setText(ar.get(pos).getName());

        TextView tv_booked_by =(TextView)obj2.findViewById(R.id.tv_booked_by);
        tv_booked_by.setText("Booked By :"+ar.get(pos).getBooked_by());

        TextView tv_price =(TextView)obj2.findViewById(R.id.tv_price);
        tv_price.setText("price :"+ar.get(pos).getPrice());

        Button btn_accept = (Button)obj2.findViewById(R.id.btn_accept);
        Button btn_reject=(Button)obj2.findViewById(R.id.btn_reject);
        if (ar.get(pos).getDate().equals("Approved")){
            btn_accept.setVisibility(android.view.View.INVISIBLE);
            btn_reject.setVisibility(android.view.View.INVISIBLE);
        }
        else if (ar.get(pos).getDate().equals("Rejected")){
            btn_reject.setVisibility(android.view.View.INVISIBLE);
            btn_accept.setVisibility(android.view.View.INVISIBLE);
        }
        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference(parentDbName).child(ar.get(pos).getPid());
                MyToolsPojo edittools=new MyToolsPojo(ar.get(pos).getTime(),ar.get(pos).getImage(),ar.get(pos).getName(),ar.get(pos).getCategory(),ar.get(pos).getPrice()
                        ,ar.get(pos).getDesc(),ar.get(pos).getCondition(),ar.get(pos).getStatus(),ar.get(pos).getPosted_by(),ar.get(pos).getBooked_by()
                        ,ar.get(pos).getPosted_by(),"Approved",ar.get(pos).getPid(),ar.get(pos).getFrom_date(),ar.get(pos).getTo_date(),ar.get(pos).getPrice());
                databaseReference.setValue(edittools);

                Toast.makeText(cnt,"Approved Successfully",Toast.LENGTH_SHORT).show();
            }
        });


        btn_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {


                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference(parentDbName).child(ar.get(pos).getPid());
                MyToolsPojo edittools= new MyToolsPojo(ar.get(pos).getTime(),ar.get(pos).getImage(),ar.get(pos).getName(),ar.get(pos).getCategory(),ar.get(pos).getPrice()
                        ,ar.get(pos).getDesc(),ar.get(pos).getCondition(),ar.get(pos).getStatus(),ar.get(pos).getPosted_by(),ar.get(pos).getBooked_by()
                        ,ar.get(pos).getPosted_by(),"Rejected",ar.get(pos).getPid(),"","","");
                databaseReference.setValue(edittools);

                Toast.makeText(cnt,"This tool is Rejected",Toast.LENGTH_SHORT).show();

            }
        });

       /* CardView cvParent=(CardView)obj2.findViewById(R.id.cvParent);
        cvParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent intent=new Intent(cnt, ToolDetailsActivity.class);
                intent.putExtra("name",ar.get(pos).getName());
                intent.putExtra("status",ar.get(pos).getStatus());
                intent.putExtra("price",ar.get(pos).getPrice());
                intent.putExtra("reviews",ar.get(pos).getReviews());
                intent.putExtra("image",ar.get(pos).getImage());
                cnt.startActivity(intent);
            }
        });*/
        return obj2;
    }
}