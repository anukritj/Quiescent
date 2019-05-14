package com.example.anukrit.quiescent.utils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseUtils {
    private static DatabaseUtils databaseUtils;
    private static FirebaseDatabase firebaseDatabase;



    private FirebaseDatabase getFirebaseDatabase()
    {
        if (firebaseDatabase == null)
        {
            firebaseDatabase = FirebaseDatabase.getInstance();
            firebaseDatabase.setPersistenceEnabled(true);
        }
        return firebaseDatabase;
    }

    public DatabaseReference getDatabaseInstance()
    {
        return getFirebaseDatabase().getReference();
    }

    public static DatabaseUtils getDatabaseReference() {
        if (databaseUtils == null)
            databaseUtils = new DatabaseUtils();
        return databaseUtils;
    }
}
