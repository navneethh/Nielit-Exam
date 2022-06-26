package com.allahabadi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class qdetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qdetail);
        Intent i = getIntent();
        String uid = i.getStringExtra("uid");
        TextView textView= findViewById(R.id.textView13);
        textView.setText(uid);
    }
}