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

import java.io.UnsupportedEncodingException;

public class Mqtthelper {

    private String message;
    private String topic;
    private String subscriptionTopic;

    private MqttAndroidClient mqttAndroidClient;
    private final String serverUri = "tcp://m2m.eclipse.org:1883"; // TODO: change this

    public Mqtthelper(Context context, String message, String topic){
        String clientId = "ExampleAndroidClient";
        this.message=message;
        this.topic=topic;
        mqttAndroidClient = new MqttAndroidClient(context, serverUri, clientId);
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
                Log.w("Mqtt", mqttMessage.toString());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                Log.w("Mqtt", iMqttDeliveryToken.toString());

            }
        });
        connectForPublish();
    }



    public void setCallback(MqttCallbackExtended callback) {
        mqttAndroidClient.setCallback(callback);
    }

    private void connectForPublish(){
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
                    //subscribeToTopic();

                    try {
                        publishMessage(mqttAndroidClient,message,0,topic);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.w("Mqtt: OnFailureCalled", "Failed to connect to: " + serverUri + exception.toString());
                }
            });


        } catch (MqttException ex){
            ex.printStackTrace();
        }
    }



    private void publishMessage(@NonNull MqttAndroidClient client,
                               @NonNull String msg, int qos, @NonNull String topic)
            throws MqttException, UnsupportedEncodingException {
        byte[] encodedPayload = new byte[0];
        encodedPayload = msg.getBytes("UTF-8");
        MqttMessage message = new MqttMessage(encodedPayload);
        message.setId(5866);
        message.setRetained(true);
        message.setQos(qos);
        client.publish(topic, message);
        disconnect(mqttAndroidClient);
    }

    public void disconnect(@NonNull MqttAndroidClient client)
            throws MqttException {
        IMqttToken mqttToken = client.disconnect();
        mqttToken.setActionCallback(new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken iMqttToken) {
                Log.d("MQTT","Successfully disconnected");
            }
            @Override
            public void onFailure(IMqttToken iMqttToken, Throwable throwable) {
                Log.d("MQTT", "Failed to disconnected " + throwable.toString());
            }
        });
    }



    public MqttAndroidClient getMqttAndroidClient() {
        return mqttAndroidClient;
    }
}