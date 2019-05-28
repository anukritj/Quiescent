package com.example.anukrit.quiescent.data.models.ErrorDetection;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class VoltageError0 {

    public String actual;
    public String desired;
    public String error;

    public VoltageError0(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public VoltageError0(String actual, String desired, String error){
        this.actual=actual;
        this.desired=desired;
        this.error=error;
    }

}
