package com.example.anukrit.quiescent.activities;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.anukrit.quiescent.MainActivity;
import com.example.anukrit.quiescent.R;
import com.example.anukrit.quiescent.utils.DatabaseUtils;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.shobhitpuri.custombuttons.GoogleSignInButton;

import java.util.Collections;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 200;

    private FirebaseAuth mAuth;
    private GoogleSignInButton googleSignInButton;
    private SharedPreferences shared ;
    private boolean isLoggedIn = false;
    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mAuth= FirebaseAuth.getInstance();
        shared= getSharedPreferences("userData",MODE_PRIVATE);

        isLoggedIn = shared.getBoolean("loggedIn",false);
//        Toast.makeText(this, "log "+Boolean.toString(isLoggedIn), Toast.LENGTH_SHORT).show();

        googleSignInButton=findViewById(R.id.button_google_login);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build();

        final List<AuthUI.IdpConfig> providers = Collections.singletonList(new AuthUI.IdpConfig.GoogleBuilder().build());



//        if(!isLoggedIn){
            new Handler().postDelayed(new Runnable() {


                @Override
                public void run() {
                    // This method will be executed once the timer is over
//                   Intent i = new Intent(SplashActivity.this, MainActivity.class);
//                    startActivity(i);
//                    finish();
                     if(isLoggedIn){
                        FirebaseUser currentUser  = mAuth.getCurrentUser();
                        if(currentUser != null)
                            updateUI(currentUser);
                        else
                            shared.edit().putBoolean("loggedIn",false).apply();
                    }
                     else
                    googleSignInButton.setVisibility(View.VISIBLE);

                }
            }, 3000);


        googleSignInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    googleSignInButton.setEnabled(false);

                    // Create and launch sign-in intent
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAvailableProviders(providers)
                                    .build(),
                            RC_SIGN_IN);
                }
            });
            isLoggedIn=true;
//        }
    }

    @Override
    protected void onStart() {
        super.onStart();
       /* if(isLoggedIn){
            FirebaseUser currentUser  = mAuth.getCurrentUser();
            if(currentUser != null)
                updateUI(currentUser);
            else
                shared.edit().putBoolean("loggedIn",false).apply();
        }*/
    }

    private void updateUI(FirebaseUser firebaseUser)
    {

        dismissProgressDialog();

        if (firebaseUser!=null)
        {


            if (Build.VERSION.SDK_INT >= 23)
            {
                Intent intent=new Intent(this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
            else
            {
                Intent intent=new Intent(this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);

        progressDialog=new ProgressDialog(SplashActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setTitle("Logging in");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        if (requestCode == RC_SIGN_IN) {

//            IdpResponse response = IdpResponse.fromResultIntent(data);


            if (resultCode == RESULT_OK) {
                // Successfully signed in
                dismissProgressDialog();
                updateUI(mAuth.getCurrentUser());
            }
            else
            {
                googleSignInButton.setEnabled(true);
                dismissProgressDialog();
                Toast.makeText(this,"Login failed",Toast.LENGTH_LONG).show();
            }

        }
    }

    private void dismissProgressDialog()
    {
        if (progressDialog!=null && progressDialog.isShowing())
            progressDialog.dismiss();
    }



    @Override
    protected void onStop() {
        super.onStop();
    }
}


