package com.example.anukrit.quiescent.data.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Voltage {
    public float v1;
    public float v2;
    public float v3;
    public float v4;

    public Voltage(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Voltage(float i1, float i2, float i3, float i4){
        this.v1=i1;
        this.v2=i2;
        this.v3=i3;
        this.v4=i4;
    }
}
