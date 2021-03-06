package com.example.anukrit.quiescent.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anukrit.quiescent.R;
import com.example.anukrit.quiescent.adapters.RoomDetailsModelAdapter;
import com.example.anukrit.quiescent.data.models.RoomDetailsModel;


import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class HomeFragment extends Fragment {
    private List<RoomDetailsModel> details = new ArrayList<>();
    private RecyclerView recyclerView;
    private RoomDetailsModelAdapter mAdapter;
    private static HomeFragment homeFragment;

    public HomeFragment(){

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.room_recycler_view, container, false);




        /*getSupportActionBar().setTitle("Find a Group"); // for set actionbar title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // for add back arrow in action bar*/

        recyclerView =  rootView.findViewById(R.id.recycler_view);

        mAdapter = new RoomDetailsModelAdapter(details,getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        /*ivBack = findViewById(R.id.ivback);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });*/

        prepareGroupData();
        //return rootView;
        return rootView;
    }
    //..
    private void prepareGroupData() {
        details.add(new RoomDetailsModel("Cabin Air Pressure","1",R.drawable.cabin));
        details.add(new RoomDetailsModel("Level Sensing","1",R.drawable.ice));
        details.add(new RoomDetailsModel("Leak Detection","1",R.drawable.leaks));
        details.add(new RoomDetailsModel("Volumetric Flow Controllers","1",R.drawable.sauce));
        details.add(new RoomDetailsModel("Temperature Controller","1",R.drawable.machine5));
        details.add(new RoomDetailsModel("Baggage Counter","1",R.drawable.baggage));
        details.add(new RoomDetailsModel("Containers Scanner","1",R.drawable.cnc));
        details.add(new RoomDetailsModel("Encoders","1",R.drawable.encoder));
        details.add(new RoomDetailsModel("CNC/NC","1",R.drawable.cnc));




        mAdapter.notifyDataSetChanged();
    }
    public static HomeFragment getHomeFragment() {
        if (homeFragment == null)
            homeFragment = new HomeFragment();
        return homeFragment;
    }
}

