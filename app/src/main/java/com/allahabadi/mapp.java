package com.allahabadi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.os.Bundle;

import com.google.firebase.database.FirebaseDatabase;

public class mapp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}