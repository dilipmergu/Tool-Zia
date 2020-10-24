package com.e.toolsharing;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.e.toolsharing.adapters.HistoryAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.e.toolsharing.activities.Utils;
import com.e.toolsharing.models.HomeDataPojo;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {
    GridView gridview;
    List<HomeDataPojo> a1;
    SharedPreferences sharedPreferences;
    String session;
    HistoryAdapter historyAdapter;
    ProgressDialog progressDialog;
    View view;

    public static HistoryFragment historyFragment() {
        HistoryFragment fragment = new HistoryFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_history, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("History");

        sharedPreferences =getActivity().getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("user_name", "def-val");

        a1= new ArrayList<>();


        gridview=(GridView)view.findViewById(R.id.gridview);
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setTitle("Please Wait data is being Loaded");
        progressDialog.show();
        Query query = FirebaseDatabase.getInstance().getReference("Products")
                .orderByChild("booked_by")
                .equalTo(session);
        query.addListenerForSingleValueEvent(valueEventListener);


        return view;
    }
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            progressDialog.dismiss();
            a1.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HomeDataPojo homeDataPojo = snapshot.getValue(HomeDataPojo.class);
                    a1.add(homeDataPojo);
                }
                //Toast.makeText(getContext(), ""+a1.size(), Toast.LENGTH_SHORT).show();
                historyAdapter = new HistoryAdapter(a1,getActivity());
                gridview.setAdapter(historyAdapter);

            }
            else {
                Toast.makeText(getContext(), "No data Found", Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
            progressDialog.dismiss();

        }
    };

}
