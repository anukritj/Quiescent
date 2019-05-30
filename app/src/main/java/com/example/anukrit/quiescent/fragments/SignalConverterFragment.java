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
import com.example.anukrit.quiescent.utils.DatabaseUtils;
import com.example.anukrit.quiescent.utils.MqttHelperSubscribe;
import com.google.firebase.auth.FirebaseAuth;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class SignalConverterFragment extends Fragment {

    TextView input_voltage;
    TextView output_current;
    MqttHelperSubscribe mqttHelperSubscribe;
    public SignalConverterFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_signal_converter, container, false);

        input_voltage=rootView.findViewById(R.id.input_voltage);
        output_current=rootView.findViewById(R.id.output_current);

        startMqttSubscribe(getActivity(), "/channel1/ip_voltage");
        startMqttSubscribe(getActivity(), "/channel1/op_current");

        return rootView;
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
                if(topic.equals("/channel1/ip_voltage")){
                    input_voltage.setText(mqttMessage.toString());
                }
                else if(topic.equals("/channel1/op_current")){
                    output_current.setText(mqttMessage.toString());
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
    public void onDestroy(){

        try {

            if(mqttHelperSubscribe.getMqttAndroidClient()!=null) {
                mqttHelperSubscribe.unSubscribe(mqttHelperSubscribe.getMqttAndroidClient(), "/channel1/vol1");
                mqttHelperSubscribe.unSubscribe(mqttHelperSubscribe.getMqttAndroidClient(), "/channel2/vol2");
                mqttHelperSubscribe.unSubscribe(mqttHelperSubscribe.getMqttAndroidClient(), "/channel3/vol3");
                mqttHelperSubscribe.unSubscribe(mqttHelperSubscribe.getMqttAndroidClient(), "/channel4/vol4");
                mqttHelperSubscribe.unSubscribe(mqttHelperSubscribe.getMqttAndroidClient(), "/channel1/actual");
                mqttHelperSubscribe.unSubscribe(mqttHelperSubscribe.getMqttAndroidClient(), "/channel1/error");
                mqttHelperSubscribe.unSubscribe(mqttHelperSubscribe.getMqttAndroidClient(), "/channel1/ip_voltage");
                mqttHelperSubscribe.unSubscribe(mqttHelperSubscribe.getMqttAndroidClient(), "/channel1/op_current");


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
}
