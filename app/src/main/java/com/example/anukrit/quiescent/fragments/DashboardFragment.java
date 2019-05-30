package com.example.anukrit.quiescent.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anukrit.quiescent.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashboardFragment extends Fragment {


    private static DashboardFragment dashboardFragment;

    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this,rootView);
        return rootView;
    }

    @OnClick(R.id.signal_generator)
    void generateSignal(){
//        startActivity(SignalGeneratorActivity.makeIntent());
        SignalGeneratorFragment signalGeneratorFragment= new SignalGeneratorFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.navigation_fragment_container, signalGeneratorFragment);
        ft.commit();
    }

    /*@OnClick(R.id.error_correction)
    void errorCorrection(){
//        startActivity(GroupDetailsActivity.makeIntent());
        ErrorCorrectionFragment errorCorrectionFragment=new ErrorCorrectionFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.navigation_fragment_container, errorCorrectionFragment);
        ft.commit();

    }
*/
    @OnClick(R.id.analytics)
    void checkAnalytics(){
//        startActivity(AddGroupActivity.makeIntent());
        AnalyticsFragment analyticsFragment= new AnalyticsFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.navigation_fragment_container, analyticsFragment);
        ft.commit();
    }

    /*@OnClick(R.id.callibration)
    void addErrorDetection(){
//        startActivity(AddGroupActivity.makeIntent());
        ErrorDetectionFragment errorDetectionFragment = new ErrorDetectionFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.navigation_fragment_container, errorDetectionFragment);
        ft.commit();
    }*/

    @Override
    public void onResume() {
        super.onResume();
//        GeneralUtils.debugToast(getContext(),"onResume: CommunityAddGroupsFragment");
//        String schoolName = PrefManager.getInstance().getSchool();
//        if (schoolName!=null) {
//            selectedSchool.setVisibility(View.VISIBLE);
//            tvSchool.setText(schoolName);
//        }else
//            selectedSchool.setVisibility(View.GONE);
    }
    public static DashboardFragment getDashboardFragment() {
        if (dashboardFragment == null)
            dashboardFragment = new DashboardFragment();
        return dashboardFragment;
    }


}
