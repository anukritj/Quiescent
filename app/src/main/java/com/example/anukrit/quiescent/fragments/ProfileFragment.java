package com.example.anukrit.quiescent.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.anukrit.quiescent.MainActivity;
import com.example.anukrit.quiescent.R;
import com.example.anukrit.quiescent.activities.SplashActivity;
import com.example.anukrit.quiescent.utils.ImageUtils;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.joooonho.SelectableRoundedImageView;

import java.util.Objects;

public class ProfileFragment extends Fragment {

    private static ProfileFragment profileFragment;
    private FirebaseAuth mAuth;
    SharedPreferences shared;
    private FirebaseAuth.AuthStateListener mAuthListener;


    public ProfileFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null)
                {
                    Intent intent = new Intent(getContext(), SplashActivity.class);
                    startActivity(intent);
                }
            }
        };

        MainActivity.fragment = getProfileFragment();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewSetter(view);

    }

    void viewSetter(View view){
        ProgressBar progressBar = view.findViewById(R.id.profile_progress);
        progressBar.setIndeterminate(true);
        SelectableRoundedImageView pic = view.findViewById(R.id.profilePic);
        TextView name = view.findViewById(R.id.profileName);
        //ImageButton myEvents = view.findViewById(R.id.myEvents);
        //ImageButton myRegisterations = view.findViewById(R.id.myRegisterations);

//        String path = "Users/";
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(path);
//        reference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//            //   Profile currentProfile = dataSnapshot.getValue(Profile.class);
//                Log.i("datasnap",dataSnapshot.toString());
//               Toast.makeText(getContext(), dataSnapshot.getKey(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

//        Button cancel = view.findViewById(R.id.cancelUpdate);
        ImageUtils.newInstance().setCircleCropImage(getContext(), Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhotoUrl()).toString(), pic, progressBar);

        name.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

        /*view.findViewById(R.id.manageProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag("Profile");
                if (prev != null && prev.isAdded())
                    ft.remove(prev);
                ft.addToBackStack(null);
                UpdateProfile profile = new UpdateProfile();
                profile.show(ft,"Profile");
            }
        });
*/
        view.findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance().signOut(Objects.requireNonNull(getContext())).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
//                        shared.edit().putBoolean("loggedIn",false);
                        SplashActivity.isLoggedIn=false;
                        Intent intent=new Intent(getContext(),SplashActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    }
                });
            }
        });


    }

    private void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = ((AppCompatActivity)Objects.requireNonNull(getContext())).getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.navigation_fragment_container,fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commitAllowingStateLoss();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    public static ProfileFragment getProfileFragment() {
        if (profileFragment == null)
            profileFragment = new ProfileFragment();
        return profileFragment;
    }
}
