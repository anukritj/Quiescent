package com.example.anukrit.quiescent.data.models.ErrorDetection;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class CurrentError2 {

    public String actual;
    public String desired;
    public String error;

    public CurrentError2(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public CurrentError2(String actual, String desired, String error){
        this.actual=actual;
        this.desired=desired;
        this.error=error;
    }

}
