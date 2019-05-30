package com.example.anukrit.quiescent.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.anukrit.quiescent.R;
import com.example.anukrit.quiescent.data.models.Current;
import com.example.anukrit.quiescent.data.models.ErrorDetection.CurrentError2;
import com.example.anukrit.quiescent.data.models.ErrorDetection.CurrentError3;
import com.example.anukrit.quiescent.data.models.ErrorDetection.VoltageError0;
import com.example.anukrit.quiescent.data.models.ErrorDetection.VoltageError1;
import com.example.anukrit.quiescent.data.models.Voltage;
import com.example.anukrit.quiescent.utils.DatabaseUtils;
import com.example.anukrit.quiescent.utils.GeneralUtils;
import com.example.anukrit.quiescent.utils.MqttHelperSubscribe;
import com.example.anukrit.quiescent.utils.Mqtthelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class ErrorCorrectionFragment extends Fragment {

    public static ErrorDetectionFragment errorDetectionFragment;
    private static CurrentError2 currentError2;
    private static CurrentError3 currentError3;
    private static VoltageError0 voltageError0;
    private static VoltageError1 voltageError1;

    public static String desiredVoltage;

    Mqtthelper mqttHelper;
    MqttHelperSubscribe mqttHelperSubscribe;

    EditText desired_v0;
    EditText desired_v1;
    EditText desired_i2;
    EditText desired_i3;



    public ErrorCorrectionFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_error_correction, container, false);
//
        desired_v0= rootView.findViewById(R.id.desired_v0);
        desired_v1= rootView.findViewById(R.id.desired_v1);
        desired_i2= rootView.findViewById(R.id.desired_i2);
        desired_i3= rootView.findViewById(R.id.desired_i3);
        final TextView send= rootView.findViewById(R.id.send);


        desired_v0.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try{
                    String strEnteredVal = desired_v0.getText().toString();
                    desiredVoltage=strEnteredVal;
                    if(FirebaseAuth.getInstance().getCurrentUser()!=null){

                        DatabaseUtils.getDatabaseReference().getDatabaseInstance().child("Device").child("Error Detection").child("V0").child("Desired").setValue(strEnteredVal);
                    }
                }catch (Exception ex){

                }


            }


        });



     /*   DatabaseUtils.getDatabaseReference().getDatabaseInstance().child("Device").child("Error Detection").child("I2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentError2 = dataSnapshot.getValue(CurrentError2.class);
                desired_i2.setText(currentError2.desired);

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
                desired_i3.setText(currentError3.desired);

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
                desired_v0.setText(voltageError0.desired);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
            }
        });*/

       /* DatabaseUtils.getDatabaseReference().getDatabaseInstance().child("Device").child("Error Detection").child("V1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                voltageError1 = dataSnapshot.getValue(VoltageError1.class);
                desired_v1.setText(voltageError1.desired);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
            }
        });*/

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if(FirebaseAuth.getInstance().getCurrentUser()!=null){
                    Current current=new Current(Float.parseFloat(et1.getText().toString()),
                            Float.parseFloat(et2.getText().toString()),
                            Float.parseFloat(et3.getText().toString()),
                            Float.parseFloat(et4.getText().toString()));
                    DatabaseUtils.getDatabaseReference().getDatabaseInstance().child("Device").child("Current").setValue(current);
                    GeneralUtils.toast(getContext(), "Value sent");
                }*/

                if(checkConstraints(Integer.parseInt(desired_v0.getText().toString()))){
                    startMqtt(getActivity(), desired_v0.getText().toString(), "/channel1/desired_voltage");
                    GeneralUtils.toastLong(getActivity(),"Values Sent");
                    Log.w("Desired V0 :", desired_v0.getText().toString());
                }
                else GeneralUtils.toastLong(getActivity(),"Invalid: Enter a value between 4 to 20 mA");

                desired_v0.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        try{
                            String strEnteredVal = desired_v0.getText().toString();

//                            if(FirebaseAuth.getInstance().getCurrentUser()!=null){
//
//                                DatabaseUtils.getDatabaseReference().getDatabaseInstance().child("Device").child("Error Detection").child("V0").child("Desired").setValue(strEnteredVal);
//                            }
                        }catch (Exception ex){

                        }


                    }


                });


            }
        });


        return rootView;
    }

    private void startMqtt(Context context, String message, String topic) {
        mqttHelper = new Mqtthelper(context, message,topic);
        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {

            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) {
                Log.w("Debug", mqttMessage.toString());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                Log.w("Debug", "Completed");
                /*try {
                    mqttHelper.disconnect(mqttHelper.getMqttAndroidClient());
                } catch (MqttException e) {
                    e.printStackTrace();
                }*/
            }
        });
    }
    private boolean checkConstraints(int value){
        if (value>=0 && value<=10) return true;
        return false;
    }

    @Override
    public void onDestroy() {
        try {

            if(mqttHelperSubscribe.getMqttAndroidClient()!=null) {
                mqttHelperSubscribe.unSubscribe(mqttHelperSubscribe.getMqttAndroidClient(), "/channel1/actual");
                mqttHelperSubscribe.unSubscribe(mqttHelperSubscribe.getMqttAndroidClient(), "/channel1/error");

                mqttHelperSubscribe.unSubscribe(mqttHelperSubscribe.getMqttAndroidClient(), "/channel1/cur1");
                mqttHelperSubscribe.unSubscribe(mqttHelperSubscribe.getMqttAndroidClient(), "/channel2/cur2");
                mqttHelperSubscribe.unSubscribe(mqttHelperSubscribe.getMqttAndroidClient(), "/channel3/cur3");
                mqttHelperSubscribe.unSubscribe(mqttHelperSubscribe.getMqttAndroidClient(), "/channel4/cur4");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    public static String getDesiredVoltage() {
        return desiredVoltage;
    }
}
