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
import com.example.anukrit.quiescent.data.models.Frequency;
import com.example.anukrit.quiescent.utils.DatabaseUtils;
import com.example.anukrit.quiescent.utils.GeneralUtils;
import com.example.anukrit.quiescent.utils.Mqtthelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.text.DecimalFormat;

import butterknife.OnClick;

public class FrequencyFragment extends Fragment {

    private static FrequencyFragment frequencyFragment;
    private static Frequency value;
    Mqtthelper mqttHelper;


    public  FrequencyFragment () {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_frequency, container, false);

        final EditText et1=(EditText) rootView.findViewById(R.id.editText1);
        final SeekBar sk1 = (SeekBar) rootView.findViewById(R.id.seekBar1);
        final EditText et2=(EditText) rootView.findViewById(R.id.editText2);
        final SeekBar sk2 = (SeekBar) rootView.findViewById(R.id.seekBar2);
        final EditText et3=(EditText) rootView.findViewById(R.id.editText3);
        final SeekBar sk3 = (SeekBar) rootView.findViewById(R.id.seekBar3);
        final EditText et4=(EditText) rootView.findViewById(R.id.editText4);
        final SeekBar sk4 = (SeekBar) rootView.findViewById(R.id.seekBar4);

        final TextView send= rootView.findViewById(R.id.send);

        //DatabaseUtils.getDatabaseReference().getDatabaseInstance().child("Device").child("Frequency").;


        DatabaseUtils.getDatabaseReference().getDatabaseInstance().child("Device").child("Frequency").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                value = dataSnapshot.getValue(Frequency.class);
                initialize(et1,value.f1);
                initialize(et2,value.f2);
                initialize(et3,value.f3);
                initialize(et4,value.f4);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
            }
        });



        updateSeekbar(et1,sk1);
        updateSeekbar(et2,sk2);
        updateSeekbar(et3,sk3);
        updateSeekbar(et4,sk4);

//        updateSeekbar((EditText) rootView.findViewById(R.id.editText2),(SeekBar)rootView.findViewById(R.id.seekBar2));
//        updateSeekbar((EditText) rootView.findViewById(R.id.editText3),(SeekBar)rootView.findViewById(R.id.seekBar3));
//        updateSeekbar((EditText) rootView.findViewById(R.id.editText4),(SeekBar)rootView.findViewById(R.id.seekBar4));

        updateEditText(et1,sk1);
        updateEditText(et2,sk2);
        updateEditText(et3,sk3);
        updateEditText(et4,sk4);

//        updateEditText((EditText) rootView.findViewById(R.id.editText2),(SeekBar)rootView.findViewById(R.id.seekBar2));
//        updateEditText((EditText) rootView.findViewById(R.id.editText3),(SeekBar)rootView.findViewById(R.id.seekBar3));
//        updateEditText((EditText) rootView.findViewById(R.id.editText4),(SeekBar)rootView.findViewById(R.id.seekBar4));

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    Frequency frequency = new Frequency(Float.parseFloat(et1.getText().toString()),
                            Float.parseFloat(et2.getText().toString()),
                            Float.parseFloat(et3.getText().toString()),
                            Float.parseFloat(et4.getText().toString()));
                    DatabaseUtils.getDatabaseReference().getDatabaseInstance().child("Device").child("Frequency").setValue(frequency);
                    GeneralUtils.toast(getContext(), "Value sent");
                }

//                startMqtt(getActivity(),et1.getText().toString(),"/channel1/current"  );
//                startMqtt(getActivity(),et2.getText().toString(),"/channel2/current"  );
//                startMqtt(getActivity(),et3.getText().toString(),"/channel3/current"  );
//                startMqtt(getActivity(),et4.getText().toString(),"/channel4/current"  );
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
    @OnClick(R.id.send)
    void send(){
        GeneralUtils.toast(getContext(),"Sent");
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

}

