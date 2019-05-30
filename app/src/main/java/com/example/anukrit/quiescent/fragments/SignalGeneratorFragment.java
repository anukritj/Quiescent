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

public class SignalGeneratorFragment extends Fragment {

    private static SignalGeneratorFragment signalGeneratorFragment;
    public SignalGeneratorFragment(){

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_signal_generator, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        VoltageFragment voltageFragment = new VoltageFragment();
        Currentfragment currentFragment = new Currentfragment();
        FrequencyFragment frequencyFragment = new FrequencyFragment();
        ErrorCorrectionFragment errorCorrectionFragment=new ErrorCorrectionFragment();


        TabLayout tabLayout = (TabLayout)view.findViewById(R.id.tabLayout);
        TabLayoutAdapter tabLayoutAdapter = new TabLayoutAdapter(Objects.requireNonNull(getChildFragmentManager()));
        ViewPager viewPager = view.findViewById(R.id.viewPager);
        Log.e("SignalGeneratorFragment", "Entered SignalGeneratorFragment");
        tabLayoutAdapter.addFragment(voltageFragment, "Voltage");
        tabLayoutAdapter.addFragment(currentFragment, "Current");
        tabLayoutAdapter.addFragment(frequencyFragment, "Frequency");
        tabLayoutAdapter.addFragment(errorCorrectionFragment, "Correction");



        viewPager.setAdapter(tabLayoutAdapter);
        tabLayout.setupWithViewPager(viewPager);
        //CustomTabLayout.Tab tab = tabLayout.getTabAt(index);
        //assert tab != null;
        //tab.select();
    }

    public static SignalGeneratorFragment getSignalGeneratorFragment() {
        if (signalGeneratorFragment == null)
            signalGeneratorFragment = new SignalGeneratorFragment();
        return signalGeneratorFragment;
    }


}
