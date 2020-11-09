package com.e.toolsharing;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.e.toolsharing.activities.BookedToolsActivity;
import com.e.toolsharing.activities.ChangePasswordActivity;
import com.e.toolsharing.activities.GetDamagedToolsActivity;
import com.e.toolsharing.activities.LoginActivity;
import com.e.toolsharing.activities.MyBookedToolsActivity;
import com.e.toolsharing.activities.MyFavouriteActivity;
import com.e.toolsharing.activities.MyProfileActivity;
import com.e.toolsharing.activities.MyToolsActivity;
import com.e.toolsharing.adapters.SearchAdapter;
import com.e.toolsharing.models.HomeDataPojo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class   SearchFragment extends Fragment {
    GridView gridview;
    List<HomeDataPojo> a1;
    ProgressDialog progressDialog;
    SearchAdapter searchAdapter;
    EditText et_search;
    View view;


    public static SearchFragment scarchFragment() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_search, container, false);
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Search");
        progressDialog=new ProgressDialog(getContext());


        gridview=(GridView)view.findViewById(R.id.gridview);
        a1= new ArrayList<>();
        Query query = FirebaseDatabase.getInstance().getReference("Products");
        query.addListenerForSingleValueEvent(valueEventListener);

        et_search = (EditText) view.findViewById(R.id.et_search);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = et_search.getText().toString().toLowerCase(Locale.getDefault());
                searchAdapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }
        });




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
                searchAdapter = new SearchAdapter(a1, getActivity());
                gridview.setAdapter(searchAdapter);

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

            case R.id.my_booked_tools:
                startActivity(new Intent(getContext(), MyBookedToolsActivity.class));
                return true;

            case R.id.my_tools_booked:
                startActivity(new Intent(getContext(), BookedToolsActivity.class));
                return true;

            case R.id.damaged_tool:
                startActivity(new Intent(getContext(), GetDamagedToolsActivity.class));
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