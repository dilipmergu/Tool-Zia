package com.e.toolsharing;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.e.toolsharing.activities.ChangePasswordActivity;
import com.e.toolsharing.activities.LoginActivity;
import com.e.toolsharing.activities.MyFavouriteActivity;
import com.e.toolsharing.activities.MyProfileActivity;
import com.e.toolsharing.activities.MyToolsActivity;
import com.e.toolsharing.activities.ToolDetailsActivity;
import com.e.toolsharing.activities.Utils;
import com.e.toolsharing.models.HomeDataPojo;
import com.e.toolsharing.models.HomePojo;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment {

    GridView gridview;
    List<HomePojo> a1;
    FirebaseListAdapter adapter;
    ImageView image_view;
    TextView product_name,tv_uname;
    SharedPreferences sharedPreferences;
    String session;
    private ProgressDialog loadingBar;
    HomeDataPojo homeDataPojo;
    View view;
    public static HomeFragment homeFragment() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_home, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Home");

        sharedPreferences =getActivity().getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("user_name", "def-val");
        loadingBar = new ProgressDialog(getActivity());




        gridview = (GridView) view.findViewById(R.id.gridview);
        Query query = FirebaseDatabase.getInstance().getReference().child("Products");
        FirebaseListOptions<HomeDataPojo> homeDataPojoFirebaseListOptions = new FirebaseListOptions.Builder<HomeDataPojo>()
                .setLayout(R.layout.adapter_home)
                .setLifecycleOwner(getActivity())
                .setQuery(query, HomeDataPojo.class)
                .build();
        adapter = new FirebaseListAdapter(homeDataPojoFirebaseListOptions) {
            @Override
            protected void populateView(View v, Object model, int position) {

                image_view = (ImageView) v.findViewById(R.id.image_view);
                product_name = (TextView) v.findViewById(R.id.tv_cname);
                tv_uname = (TextView) v.findViewById(R.id.tv_uname);

                homeDataPojo = (HomeDataPojo) model;
                product_name.setText(homeDataPojo.getName().toString());
                tv_uname.setText(homeDataPojo.getPosted_by().toString());
                Glide.with(getActivity()).load(homeDataPojo.getImage().toString()).into(image_view);
                ImageView img_fav=(ImageView) v.findViewById(R.id.img_fav);

                img_fav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        favList();


                    }
                });
                image_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), ToolDetailsActivity.class);
                        intent.putExtra("name", homeDataPojo.getName().toString());
                        intent.putExtra("price", homeDataPojo.getPrice().toString());
                        intent.putExtra("description", homeDataPojo.getDesc().toString());
                        intent.putExtra("condition", homeDataPojo.getCondition().toString());
                        intent.putExtra("status", homeDataPojo.getStatus().toString());
                        intent.putExtra("category", homeDataPojo.getCategory().toString());
                        intent.putExtra("image", homeDataPojo.getImage().toString());
                        intent.putExtra("pid", homeDataPojo.getPid().toString());
                        intent.putExtra("posted_by", homeDataPojo.getPosted_by().toString());
                        intent.putExtra("booked_by", "");
                        intent.putExtra("date", homeDataPojo.getDate().toString());
                        intent.putExtra("time", homeDataPojo.getTime().toString());
                        startActivity(intent);

                    }
                });
            }
        };
        gridview.setAdapter(adapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.my_profile:
                startActivity(new Intent(getContext(), MyProfileActivity.class));
                return true;
            case R.id.my_tools_posted:
                startActivity(new Intent(getContext(), MyToolsActivity.class));
                return true;

            case R.id.my_fav_tools:
                startActivity(new Intent(getContext(), MyFavouriteActivity.class));
                return true;

            case R.id.change_pwd:
                startActivity(new Intent(getContext(), ChangePasswordActivity.class));
                return true;

            case R.id.logout:
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public  void favList(){
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (!(dataSnapshot.child("Fav Products").child(homeDataPojo.getPid().toString()).exists()))
                {
                    HashMap<String, Object> productMap = new HashMap<>();
                    productMap.put("pid", homeDataPojo.getPid().toString());
                    productMap.put("date", homeDataPojo.getDate().toString());
                    productMap.put("time", homeDataPojo.getTime().toString());
                    productMap.put("image", homeDataPojo.getImage().toString());
                    productMap.put("name", homeDataPojo.getName().toString());
                    productMap.put("category", homeDataPojo.getCategory().toString());
                    productMap.put("price", homeDataPojo.getPrice().toString());
                    productMap.put("desc", homeDataPojo.getDesc().toString());
                    productMap.put("condition", homeDataPojo.getCondition().toString());
                    productMap.put("status", homeDataPojo.getStatus().toString());
                    productMap.put("posted_by", homeDataPojo.getPosted_by().toString());
                    productMap.put("fav_list", session);
                    productMap.put("booked_by","");


                    RootRef.child("Fav Products").child(homeDataPojo.getPid().toString()).updateChildren(productMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(getActivity(), "Add to favourite successfully.", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                    }
                                    else
                                    {
                                        loadingBar.dismiss();
                                        Toast.makeText(getContext(), "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                                    }

                                }

                            });
                }
                else
                {
                    Toast.makeText(getContext(), "This " + homeDataPojo.getPid().toString() + " already exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
