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
import com.example.anukrit.quiescent.data.models.Current;
import com.example.anukrit.quiescent.data.models.Voltage;
import com.example.anukrit.quiescent.utils.DatabaseUtils;
import com.example.anukrit.quiescent.utils.MqttHelperSubscribe;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.text.DecimalFormat;

public class VoltageAnalyticsFragment extends Fragment {
    MqttHelperSubscribe mqttHelperSubscribe;
    private static Voltage value;

    TextView channel1;
    TextView channel2;
    TextView channel3;
    TextView channel4;


    public VoltageAnalyticsFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_voltage_analytics, container, false);

        channel1= rootView.findViewById(R.id.channel1);
        channel2= rootView.findViewById(R.id.channel2);
        channel3= rootView.findViewById(R.id.channel3);
        channel4= rootView.findViewById(R.id.channel4);

        DatabaseUtils.getDatabaseReference().getDatabaseInstance().child("Device").child("Voltage").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                value = dataSnapshot.getValue(Voltage.class);
                initialize(channel1,value.v1);
                initialize(channel2,value.v2);
                initialize(channel3,value.v3);
                initialize(channel4,value.v4);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
            }
        });

        startMqttSubscribe(getActivity(), "/channell/vol1");
        startMqttSubscribe(getActivity(), "/channel2/vol2");
        startMqttSubscribe(getActivity(), "/channel3/vol3");
        startMqttSubscribe(getActivity(), "/channel4/vol4");
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
                if(topic.equals("/channel1/vol1")){
                    channel1.setText(mqttMessage.toString());
                }
                else if(topic.equals("/channel2/vol2")){
                    channel2.setText(mqttMessage.toString());
                }
                else  if(topic.equals("/channel3/vol3")){
                    channel3.setText(mqttMessage.toString());
                }
                else  if(topic.equals("/channel4/vol4")){
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
                mqttHelperSubscribe.unSubscribe(mqttHelperSubscribe.getMqttAndroidClient(), "/channel1/vol1");
                mqttHelperSubscribe.unSubscribe(mqttHelperSubscribe.getMqttAndroidClient(), "/channel2/vol2");
                mqttHelperSubscribe.unSubscribe(mqttHelperSubscribe.getMqttAndroidClient(), "/channel3/vol3");
                mqttHelperSubscribe.unSubscribe(mqttHelperSubscribe.getMqttAndroidClient(), "/channel4/vol4");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
}
