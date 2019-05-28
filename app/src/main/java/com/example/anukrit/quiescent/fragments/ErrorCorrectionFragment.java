package com.example.anukrit.quiescent.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.anukrit.quiescent.R;
import com.example.anukrit.quiescent.data.models.Current;
import com.example.anukrit.quiescent.data.models.ErrorDetection.CurrentError2;
import com.example.anukrit.quiescent.data.models.ErrorDetection.CurrentError3;
import com.example.anukrit.quiescent.data.models.ErrorDetection.VoltageError0;
import com.example.anukrit.quiescent.data.models.ErrorDetection.VoltageError1;
import com.example.anukrit.quiescent.utils.DatabaseUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class ErrorCorrectionFragment extends Fragment {

    public static ErrorCorrectionFragment errorCorrectionFragment;
    private static CurrentError2 currentError2;
    private static CurrentError3 currentError3;
    private static VoltageError0 voltageError0;
    private static VoltageError1 voltageError1;





    public ErrorCorrectionFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_error_detection, container, false);
//
//        final TextView actual_v0= rootView.findViewById(R.id.actual_v0);
//        final TextView actual_v1= rootView.findViewById(R.id.actual_v1);
//        final TextView actual_i2= rootView.findViewById(R.id.actual_i2);
//        final TextView actual_i3= rootView.findViewById(R.id.actual_i3);
//
//        final TextView error_v0= rootView.findViewById(R.id.error_v0);
//        final TextView error_v1= rootView.findViewById(R.id.error_v1);
//        final TextView error_i2= rootView.findViewById(R.id.error_i2);
//        final TextView error_i3= rootView.findViewById(R.id.error_i3);
//
//        final EditText desired_v0=(EditText) rootView.findViewById(R.id.desired_v0);
//        final EditText desired_v1=(EditText) rootView.findViewById(R.id.desired_v1);
//        final EditText desired_i2=(EditText) rootView.findViewById(R.id.desired_i2);
//        final EditText desired_i3=(EditText) rootView.findViewById(R.id.desired_i3);

        /*DatabaseUtils.getDatabaseReference().getDatabaseInstance().child("Device").child("Error Detection").child("I2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentError2 = dataSnapshot.getValue(CurrentError2.class);
                actual_i2.setText(currentError2.actual);
                desired_i2.setText(currentError2.desired);
                error_i2.setText(currentError2.error);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
            }
        });

        DatabaseUtils.getDatabaseReference().getDatabaseInstance().child("Device").child("Error Detection").child("I3").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentError3 = dataSnapshot.getValue(CurrentError3.class);
                actual_i3.setText(currentError3.actual);
                desired_i3.setText(currentError3.desired);
                error_i3.setText(currentError3.error);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
            }
        });

        DatabaseUtils.getDatabaseReference().getDatabaseInstance().child("Device").child("Error Detection").child("V0").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                voltageError0 = dataSnapshot.getValue(VoltageError0.class);
                actual_v0.setText(currentError2.actual);
                desired_v0.setText(currentError2.desired);
                error_v0.setText(currentError2.error);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
            }
        });

        DatabaseUtils.getDatabaseReference().getDatabaseInstance().child("Device").child("Error Detection").child("V1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                voltageError1 = dataSnapshot.getValue(VoltageError1.class);
                actual_v1.setText(currentError2.actual);
                desired_v1.setText(currentError2.desired);
                error_v1.setText(currentError2.error);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
            }
        });*/
        return rootView;
    }
}
