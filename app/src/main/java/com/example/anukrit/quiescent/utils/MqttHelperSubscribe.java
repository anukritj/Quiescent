package com.example.anukrit.quiescent.utils;


import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.core.Tag;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;


public class MqttHelperSubscribe {

    private String subscriptionTopic;

    private MqttAndroidClient mqttAndroidClient;
    private final String serverUri = "tcp://m2m.eclipse.org:1883"; // TODO: change this



    public MqttHelperSubscribe(Context context, String topic){
        String clientId = "ExampleAndroidClient";
        this.subscriptionTopic=topic;
        mqttAndroidClient = new MqttAndroidClient(context, serverUri, clientId);

        Log.e("To check the topic: ",topic);
        mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {
                Log.w("mqtt", s);
            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Log.e("Mqtt", mqttMessage.toString());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                Log.w("Mqtt", iMqttDeliveryToken.toString());

            }
        });
        connect();
    }

    public void setCallback(MqttCallbackExtended callback) {
        mqttAndroidClient.setCallback(callback);
    }



    private void connect(){
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);
//        String username = mqttConnectOptions.getUserName();                                                                 // TODO: change this
//        mqttConnectOptions.setUserName(username);
//        char[] password = mqttConnectOptions.getPassword();                                                                 // TODO: change this
//        mqttConnectOptions.setPassword(password);

        try {

            mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {

                    DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(100);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    disconnectedBufferOptions.setDeleteOldestMessages(false);
                    mqttAndroidClient.setBufferOpts(disconnectedBufferOptions);
                    subscribeToTopic(subscriptionTopic);

                  /*  try {
                        unSubscribe(mqttAndroidClient,subscriptionTopic);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
*/

                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.e("Mqtt: OnFailureCalled", "Failed to connect to: " + serverUri + exception.toString());
                }
            });


        } catch (MqttException ex){
            ex.printStackTrace();
        }
    }


    private void subscribeToTopic(String topic) {
        try {
            //String subscriptionTopic = "/cc32xx/ButtonPressEvtSw2";                                                      // TODO: change this
            mqttAndroidClient.subscribe(topic, 0, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.w("Mqtt","Subscribed!");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.w("Mqtt", "Subscribed fail!");
                }
            });



        } catch (MqttException ex) {
            System.err.println("Exceptionst subscribing");
            ex.printStackTrace();
        }
    }


    public void unSubscribe(@NonNull MqttAndroidClient client,
                            @NonNull final String topic) throws MqttException {
        IMqttToken token = client.unsubscribe(topic);
        token.setActionCallback(new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken iMqttToken) {
                Log.d("Unsubscribe", "UnSubscribe Successfully " + topic);
            }
            @Override
            public void onFailure(IMqttToken iMqttToken, Throwable throwable) {
                Log.e("Unsubscribe", "UnSubscribe Failed " + topic);
            }
        });
    }

    public MqttAndroidClient getMqttAndroidClient() {
        return mqttAndroidClient;
    }
}
