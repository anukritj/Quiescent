package com.example.anukrit.quiescent.data.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Current {

    public float i1;
    public float i2;
    public float i3;
    public float i4;

    public Current(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Current(float i1, float i2, float i3, float i4){
        this.i1=i1;
        this.i2=i2;
        this.i3=i3;
        this.i4=i4;
    }
}
