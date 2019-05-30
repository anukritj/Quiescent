package com.example.anukrit.quiescent.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anukrit.quiescent.R;
import com.example.anukrit.quiescent.adapters.TabLayoutAdapter;

import java.util.Objects;

public class AnalyticsFragment extends Fragment {

    private static AnalyticsFragment analyticsFragment;
    public AnalyticsFragment(){

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_signal_generator, container, false);

//        final TextView send=rootView.findViewById(R.id.send);
//        send.setVisibility(View.INVISIBLE);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        VoltageAnalyticsFragment voltageAnalyticsFragment = new VoltageAnalyticsFragment();
        CurrentAnalyticsFragment currentAnalyticsFragment = new CurrentAnalyticsFragment();
        FrequencyFragment frequencyFragment = new FrequencyFragment();
        ErrorDetectionFragment errorDetectionFragment=new ErrorDetectionFragment();
        SignalConverterFragment signalConverterFragment=new SignalConverterFragment();


        TabLayout tabLayout = (TabLayout)view.findViewById(R.id.tabLayout);
        TabLayoutAdapter tabLayoutAdapter = new TabLayoutAdapter(Objects.requireNonNull(getChildFragmentManager()));
        ViewPager viewPager = view.findViewById(R.id.viewPager);
        Log.e("AnalyticsFragment", "Analytics Fragment");
        tabLayoutAdapter.addFragment(voltageAnalyticsFragment, "Voltage");
        tabLayoutAdapter.addFragment(currentAnalyticsFragment, "Current");
//        tabLayoutAdapter.addFragment(frequencyFragment, "Frequency");
        tabLayoutAdapter.addFragment(errorDetectionFragment, "Detection");
        tabLayoutAdapter.addFragment(signalConverterFragment, "Converter");




        viewPager.setAdapter(tabLayoutAdapter);
        tabLayout.setupWithViewPager(viewPager);
        //CustomTabLayout.Tab tab = tabLayout.getTabAt(index);
        //assert tab != null;
        //tab.select();
    }

    public static AnalyticsFragment getAnalyticsFragment() {
        if (analyticsFragment == null)
            analyticsFragment = new AnalyticsFragment();
        return analyticsFragment;
    }


}
