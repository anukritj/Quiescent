package com.example.anukrit.quiescent;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.anukrit.quiescent.fragments.DashboardFragment;
import com.example.anukrit.quiescent.fragments.HomeFragment;
import com.example.anukrit.quiescent.fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private final DashboardFragment dashboardFragment= DashboardFragment.getDashboardFragment();
    private final HomeFragment homeFragment= HomeFragment.getHomeFragment();
    private final ProfileFragment profileFragment=ProfileFragment.getProfileFragment();
    public static Fragment fragment;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if(fragment!=homeFragment)
                        replaceFragment(homeFragment);
                    return true;
                case R.id.navigation_dashboard:
                    if(fragment!=dashboardFragment)
                        replaceFragment(dashboardFragment);
                    return true;
                /*case R.id.navigation_notifications:
                    return true;*/
                case R.id.navigation_profile:
                    if(fragment!=profileFragment)
                        replaceFragment(profileFragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.navigation_fragment_container,homeFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commitAllowingStateLoss();
        MainActivity.fragment = homeFragment;
    }

    private void replaceFragment(Fragment fragment)
    {
        fragmentManager.beginTransaction()
                .replace(R.id.navigation_fragment_container,fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commitAllowingStateLoss();
        MainActivity.fragment = fragment;
    }

}
