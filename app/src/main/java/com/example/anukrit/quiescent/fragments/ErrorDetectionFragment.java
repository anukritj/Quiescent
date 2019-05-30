package com.example.anukrit.quiescent.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.anukrit.quiescent.R;
import com.example.anukrit.quiescent.data.models.ErrorDetection.CurrentError2;
import com.example.anukrit.quiescent.data.models.ErrorDetection.CurrentError3;
import com.example.anukrit.quiescent.data.models.ErrorDetection.VoltageError0;
import com.example.anukrit.quiescent.data.models.ErrorDetection.VoltageError1;
import com.example.anukrit.quiescent.utils.DatabaseUtils;
import com.example.anukrit.quiescent.utils.MqttHelperSubscribe;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class ErrorDetectionFragment extends Fragment {

    public static ErrorDetectionFragment errorDetectionFragment;
    private static CurrentError2 currentError2;
    private static CurrentError3 currentError3;
    private static VoltageError0 voltageError0;
    private static VoltageError1 voltageError1;

    MqttHelperSubscribe mqttHelperSubscribe;


     TextView actual_v0;
     TextView actual_v1;
     TextView actual_i2;
     TextView actual_i3;

     TextView error_v0;
     TextView error_v1;
     TextView error_i2;
     TextView error_i3;

     String actual;
     String error;


    public ErrorDetectionFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_error_detection, container, false);
//
         actual_v0= rootView.findViewById(R.id.actual_v0);
         actual_v1= rootView.findViewById(R.id.actual_v1);
         actual_i2= rootView.findViewById(R.id.actual_i2);
         actual_i3= rootView.findViewById(R.id.actual_i3);

         error_v0= rootView.findViewById(R.id.error_v0);
         error_v1= rootView.findViewById(R.id.error_v1);
         error_i2= rootView.findViewById(R.id.error_i2);
         error_i3= rootView.findViewById(R.id.error_i3);
//
//        final EditText desired_v0=(EditText) rootView.findViewById(R.id.desired_v0);
//        final EditText desired_v1=(EditText) rootView.findViewById(R.id.desired_v1);
//        final EditText desired_i2=(EditText) rootView.findViewById(R.id.desired_i2);
//        final EditText desired_i3=(EditText) rootView.findViewById(R.id.desired_i3);

        actual_v0.setText(VoltageAnalyticsFragment.getActualV0());
        error_v0.setText(VoltageAnalyticsFragment.getErrorV0());


        DatabaseUtils.getDatabaseReference().getDatabaseInstance().child("Device").child("Error Detection").child("V0").child("Actual").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                actual=Long.toString(dataSnapshot.getValue(Long.class));
//                //actual_v0.setText(actual);


                //Log.e("ActualV0: ",actual);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
            }
        });

        DatabaseUtils.getDatabaseReference().getDatabaseInstance().child("Device").child("Error Detection").child("V0").child("Error").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                error=Long.toString(dataSnapshot.getValue(Long.class));
//                error_v0.setText(error);
//                error_v0.setText(VoltageAnalyticsFragment.getErrorV0());
//                error=VoltageAnalyticsFragment.getErrorV0();
//                Log.e("Error V0: ",error);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
            }
        });

        startMqttSubscribe(getActivity(), "/channel1/actual");
        startMqttSubscribe(getActivity(), "/channel1/error");
//        startMqttSubscribe(getActivity(), "/channel3/cur3");
//        startMqttSubscribe(getActivity(), "/channel4/cur4");


        return rootView;
    }

    private void startMqttSubscribe( Context context, String topic) {
            mqttHelperSubscribe = new MqttHelperSubscribe(context,topic);
//        while(mqttHelperSubscribe.getMqttAndroidClient().isConnected()){
            mqttHelperSubscribe.setCallback(new MqttCallbackExtended() {
                @Override
                public void connectComplete(boolean b, String s) {

                }

                @Override
                public void connectionLost(Throwable throwable) {

                }

                @Override
                public void messageArrived(String topic, MqttMessage mqttMessage) {
                    Log.w("Debug", mqttMessage.toString());
                    Log.w("Subscribe Topic: ", topic);
                    if(topic.equals("/channel1/actual")){
                        if(Integer.parseInt(mqttMessage.toString())<=10 && Integer.parseInt(mqttMessage.toString())>=0)
                        {
                            actual_v0.setText(mqttMessage.toString());
                        }
                        else actual_v0.setText("Negative");

                    }
                    else  if(topic.equals("/channel1/error")){
                        if(Integer.parseInt(mqttMessage.toString())<=10 && Integer.parseInt(mqttMessage.toString())>=0)
                        {
                            error_v0.setText(mqttMessage.toString());
                        }
                        else error_v0.setText("Negative");                    }




                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                    Log.w("Debug", "Completed");

                }

            });

            //}
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

}
