package com.example.anukrit.quiescent.data.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Frequency {

    public float f1;
    public float f2;
    public float f3;
    public float f4;

    public Frequency(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Frequency(float f1, float f2, float f3, float f4){
        this.f1=f1;
        this.f2=f2;
        this.f3=f3;
        this.f4=f4;
    }
}
