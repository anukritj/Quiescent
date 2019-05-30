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
import android.widget.EditText;
import android.widget.TextView;

import com.example.anukrit.quiescent.R;
import com.example.anukrit.quiescent.data.models.Current;
import com.example.anukrit.quiescent.utils.DatabaseUtils;
import com.example.anukrit.quiescent.utils.MqttHelperSubscribe;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;


import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.text.DecimalFormat;

public class CurrentAnalyticsFragment extends Fragment {

    MqttHelperSubscribe mqttHelperSubscribe;
    private static Current value;

     TextView channel1;
     TextView channel2;
     TextView channel3;
     TextView channel4;


    public CurrentAnalyticsFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_current_analytics, container, false);

         channel1= rootView.findViewById(R.id.channel1);
         channel2= rootView.findViewById(R.id.channel2);
         channel3= rootView.findViewById(R.id.channel3);
         channel4= rootView.findViewById(R.id.channel4);

        DatabaseUtils.getDatabaseReference().getDatabaseInstance().child("Device").child("Current").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                value = dataSnapshot.getValue(Current.class);
                initialize(channel1,value.i1);
                initialize(channel2,value.i2);
                initialize(channel3,value.i3);
                initialize(channel4,value.i4);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
            }
        });

           startMqttSubscribe(getActivity(), "/channell/cur1");
           startMqttSubscribe(getActivity(), "/channel2/cur2");
            startMqttSubscribe(getActivity(), "/channel3/cur3");
            startMqttSubscribe(getActivity(), "/channel4/cur4");
        return rootView;
    }

    private void initialize(final TextView et, float i) {
        DecimalFormat df = new DecimalFormat("###,###");
        String i1 = df.format(i);
        et.setText(i1);
    }

     private void startMqttSubscribe(Context context, String topic) {
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
                    if(topic.equals("/channel2/cur2")){
                        channel2.setText(mqttMessage.toString());
                    }
                    else  if(topic.equals("/channel3/cur3")){
                        channel3.setText(mqttMessage.toString());
                    }
                    else  if(topic.equals("/channel4/cur4")){
                        channel4.setText(mqttMessage.toString());
                    }



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
                mqttHelperSubscribe.unSubscribe(mqttHelperSubscribe.getMqttAndroidClient(), "/channell/cur1");
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
