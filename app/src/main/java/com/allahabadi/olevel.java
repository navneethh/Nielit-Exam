package com.allahabadi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class olevel extends AppCompatActivity {
    String Category,coursee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_olevel);
        TextView syllabus = findViewById(R.id.syllabus_button);
        TextView notes = findViewById(R.id.notes_but);

        syllabus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(),loadurlActivity.class);
                i.putExtra("category","syllabus");
                i.putExtra("course","olevel");
                startActivity(i);

            }
        });

        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent temp = new Intent(getBaseContext(),loadurlActivity.class);
                temp.putExtra("category","notes");
                temp.putExtra("course","olevel");
                startActivity(temp);


            }
        });

        TextView examdates = findViewById(R.id.examdatebut);
        examdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent temp = new Intent(getBaseContext(),loadurlActivity.class);
                temp.putExtra("category","dates");
                temp.putExtra("course","olevel");
                startActivity(temp);
            }
        });

        TextView question = findViewById(R.id.question_but);
        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent temp = new Intent(getBaseContext(),loadurlActivity.class);
                temp.putExtra("category","question");
                temp.putExtra("course","olevel");
                startActivity(temp);
            }
        });
        TextView result = findViewById(R.id.result_but);
        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent temp = new Intent(getBaseContext(),loadurlActivity.class);
                temp.putExtra("category","result");
                temp.putExtra("course","olevel");
                startActivity(temp);
            }
        });
        TextView apply = findViewById(R.id.apply_but);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent temp = new Intent(getBaseContext(),loadurlActivity.class);
                temp.putExtra("category","question");
                temp.putExtra("course","olevel");
                startActivity(temp);
            }
        });



    }
    void onclick(String cat, String cou, View v) {
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Category != null) {
                    Intent temp = new Intent(getBaseContext(), loadurlActivity.class);
                    temp.putExtra("category", Category);
                    temp.putExtra("course", coursee);
                    startActivity(temp);
                } else {

                    Toast.makeText(getBaseContext(), "Empty category", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}