package com.example.anukrit.quiescent.data.models.ErrorDetection;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class VoltageError0 {

    public String actual;
    public static String desired;
    public String error;

    public VoltageError0(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public VoltageError0(String actual, String desired, String error){
        this.actual=actual;
        this.desired=desired;
        this.error=error;
    }

    public void setActual(String actual) {
        this.actual = actual;
    }

    public void setDesired(String desired) {
        this.desired = desired;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getActual() {
        return actual;
    }

    public static String getDesired() {
        return desired;
    }

    public String getError() {
        return error;
    }
}
