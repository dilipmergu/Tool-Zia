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
import android.widget.RatingBar;
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
import com.e.toolsharing.models.HomeAdapter;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment {

    GridView gridview;
    List<HomeDataPojo> homeDataPojos;
    SharedPreferences sharedPreferences;
    String session;
    private ProgressDialog loadingBar;
    RatingBar tv_rating;
    HomeAdapter homeAdapter;
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
        setHasOptionsMenu(true);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Home");


        sharedPreferences = getActivity().getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("user_name", "def-val");
        loadingBar = new ProgressDialog(getActivity());

        gridview = (GridView) view.findViewById(R.id.gridview);
        loadingBar = new ProgressDialog(getActivity());
        loadingBar.setTitle("Please Wait data is being Loaded");
        loadingBar.show();

        homeDataPojos = new ArrayList<>();
        Query query = FirebaseDatabase.getInstance().getReference().child("Products");
        query.addListenerForSingleValueEvent(valueEventListener);

        return view;
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            loadingBar.dismiss();
            homeDataPojos.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HomeDataPojo homeDataPojo = snapshot.getValue(HomeDataPojo.class);
                    if (!(homeDataPojo.getPosted_by().equals(session))) {
                        homeDataPojos.add(homeDataPojo);
                    }
                }
                if (homeDataPojos.size() > 0) {
                    homeAdapter = new HomeAdapter(getActivity(), homeDataPojos, session);
                    gridview.setAdapter(homeAdapter);

                }

            } else {
                Toast.makeText(getContext(), "No data Found", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            loadingBar.dismiss();

        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
                break;
        }
        return false;
    }
}
