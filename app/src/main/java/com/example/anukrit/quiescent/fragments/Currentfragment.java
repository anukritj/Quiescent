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
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.anukrit.quiescent.R;
import com.example.anukrit.quiescent.data.models.Current;
import com.example.anukrit.quiescent.utils.DatabaseUtils;
import com.example.anukrit.quiescent.utils.GeneralUtils;
import com.example.anukrit.quiescent.utils.Mqtthelper;
import com.example.anukrit.quiescent.utils.MqttHelperSubscribe;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.text.DecimalFormat;

public class Currentfragment extends Fragment {
    private static Currentfragment currentFragment;
    private static Current value;
    Mqtthelper mqttHelper;
    MqttHelperSubscribe mqttHelperSubscribe;

    EditText et1;
     SeekBar sk1 ;
     EditText et2;
     SeekBar sk2;
     EditText et3;
     SeekBar sk3;
     EditText et4;
     SeekBar sk4 ;


    public Currentfragment() {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_current, container, false);

         et1=(EditText) rootView.findViewById(R.id.editText1);
        sk1 = (SeekBar) rootView.findViewById(R.id.seekBar1);
        et2=(EditText) rootView.findViewById(R.id.editText2);
         sk2 = (SeekBar) rootView.findViewById(R.id.seekBar2);
         et3=(EditText) rootView.findViewById(R.id.editText3);
         sk3 = (SeekBar) rootView.findViewById(R.id.seekBar3);
         et4=(EditText) rootView.findViewById(R.id.editText4);
         sk4 = (SeekBar) rootView.findViewById(R.id.seekBar4);

        final TextView send1= rootView.findViewById(R.id.send1);
        final TextView send2= rootView.findViewById(R.id.send2);
        final TextView send3= rootView.findViewById(R.id.send3);
        final TextView send4= rootView.findViewById(R.id.send4);


        DatabaseUtils.getDatabaseReference().getDatabaseInstance().child("Device").child("Current").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 value = dataSnapshot.getValue(Current.class);
                  initialize(et1,value.i1);
                  initialize(et2,value.i2);
                  initialize(et3,value.i3);
                  initialize(et4,value.i4);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
            }
        });




//            startMqttSubscribe(getActivity(), "/channell/cur1");
//           startMqttSubscribe(getActivity(), "/channel2/cur2");
//            startMqttSubscribe(getActivity(), "/channel3/cur3");
//            startMqttSubscribe(getActivity(), "/channel4/cur4");
//

        updateSeekbar(et1,sk1);
        updateSeekbar(et2,sk2);
        updateSeekbar(et3,sk3);
        updateSeekbar(et4,sk4);



        updateEditText(et1,sk1);
        updateEditText(et2,sk2);
        updateEditText(et3,sk3);
        updateEditText(et4,sk4);


        send1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(FirebaseAuth.getInstance().getCurrentUser()!=null){
                    Current current=new Current(Float.parseFloat(et1.getText().toString()),
                            Float.parseFloat(et2.getText().toString()),
                            Float.parseFloat(et3.getText().toString()),
                            Float.parseFloat(et4.getText().toString()));
                    DatabaseUtils.getDatabaseReference().getDatabaseInstance().child("Device").child("Current").setValue(current);
                    GeneralUtils.toast(getContext(), "Value sent");
                }

                if(checkConstraints(Integer.parseInt(et1.getText().toString()))){
                    startMqtt(getActivity(), et1.getText().toString(), "/channel1/current");
                    Log.w("Current in Channel1 :", et1.getText().toString());
                }
                else GeneralUtils.toastLong(getActivity(),"Invalid: Enter a value between 4 to 20 mA");


            }
        });

        send2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(FirebaseAuth.getInstance().getCurrentUser()!=null){
                    Current current=new Current(Float.parseFloat(et1.getText().toString()),
                            Float.parseFloat(et2.getText().toString()),
                            Float.parseFloat(et3.getText().toString()),
                            Float.parseFloat(et4.getText().toString()));
                    DatabaseUtils.getDatabaseReference().getDatabaseInstance().child("Device").child("Current").setValue(current);
                    GeneralUtils.toast(getContext(), "Value sent");
                }

                if(checkConstraints(Integer.parseInt(et2.getText().toString()))){
                    startMqtt(getActivity(), et2.getText().toString(), "/channel2/current");
                    Log.w("Current in Channel2 :", et2.getText().toString());
                }
                else GeneralUtils.toastLong(getActivity(),"Invalid: Enter a value between 4 to 20 mA");



            }
        });

        send3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(FirebaseAuth.getInstance().getCurrentUser()!=null){
                    Current current=new Current(Float.parseFloat(et1.getText().toString()),
                            Float.parseFloat(et2.getText().toString()),
                            Float.parseFloat(et3.getText().toString()),
                            Float.parseFloat(et4.getText().toString()));
                    DatabaseUtils.getDatabaseReference().getDatabaseInstance().child("Device").child("Current").setValue(current);
                    GeneralUtils.toast(getContext(), "Value sent");
                }

                if(checkConstraints(Integer.parseInt(et3.getText().toString()))) {

                    startMqtt(getActivity(), et3.getText().toString(), "/channel3/current");
                    Log.w("Current in Channel3 :", et3.getText().toString());
                }
                else GeneralUtils.toastLong(getActivity(),"Invalid: Enter a value between 4 to 20 mA");



            }
        });

        send4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(FirebaseAuth.getInstance().getCurrentUser()!=null){
                    Current current=new Current(Float.parseFloat(et1.getText().toString()),
                            Float.parseFloat(et2.getText().toString()),
                            Float.parseFloat(et3.getText().toString()),
                            Float.parseFloat(et4.getText().toString()));
                    DatabaseUtils.getDatabaseReference().getDatabaseInstance().child("Device").child("Current").setValue(current);
                    GeneralUtils.toast(getContext(), "Value sent");
                }

                if(checkConstraints(Integer.parseInt(et4.getText().toString()))) {
                    startMqtt(getActivity(), et4.getText().toString(), "/channel4/current");
                    Log.w("Current in Channel4 :", et4.getText().toString());
                }
                else GeneralUtils.toastLong(getActivity(),"Invalid: Enter a value between 4 to 20 mA");



            }
        });
        return rootView;
    }

    private void initialize(final EditText et,float i) {
        DecimalFormat df = new DecimalFormat("###,###");
        String i1 = df.format(i);
        et.setText(i1);
    }
    private void updateEditText(final EditText tv, final SeekBar sk){
        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                DecimalFormat df = new DecimalFormat("###,###");

                String value = df.format(i);
                tv.setText(value);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    private void updateSeekbar(final EditText et, final SeekBar sk){


        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try{
                    /*String strEnteredVal = et.getText().toString();

                    if(!strEnteredVal.equals("")) {
                        int num = Integer.parseInt(strEnteredVal);
                        if (num <= 10) {
                            et.setText("" + num);
                        } else {
                            et.setText("");
                            GeneralUtils.toast(getContext(), "Voltage:0-10V");
                        }
                    }*/
                    sk.setProgress(Integer.parseInt(editable.toString()));
                }catch (Exception ex){

                }


            }


        });
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

   /* private void startMqttSubscribe(Context context, String topic) {
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
                        et2.setText(mqttMessage.toString());
                    }
                    else  if(topic.equals("/channel3/cur3")){
                        et3.setText(mqttMessage.toString());
                    }
                    else  if(topic.equals("/channel4/cur4")){
                        et4.setText(mqttMessage.toString());
                    }



                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                    Log.w("Debug", "Completed");

                }

                });

        //}
    }
*/
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

    private boolean checkConstraints(int value){
        if (value>=4 && value<=20) return true;
        return false;
    }
}
