package com.allahabadi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Alevel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olevel);
        getSupportActionBar().hide();
        TextView course_heading = (TextView) findViewById(R.id.course_mian_heading);
        TextView course_short_des = (TextView) findViewById(R.id.course_short_des);
        TextView course_des = (TextView) findViewById(R.id.course_desc);
        TextView course_eligibility = (TextView) findViewById(R.id.course_eligibility);


        TextView date_start= (TextView) findViewById(R.id.appli_date_start);
        TextView date_end= (TextView) findViewById(R.id.appli_date_end);
        TextView date_exam = (TextView) findViewById(R.id.exam_date);


        TextView syllabus = findViewById(R.id.syllabus_button);
        TextView notes = findViewById(R.id.notes_but);

        course_heading.setText("Alevel by Nielit");
        course_short_des.setText("A-level is Adavnced diploma course by Nielit which is recognised by Central Government as equivalent to B Tech, BSc ,BCA. It is mainly 3 year course which is preferred to be completed in 2 years");
        course_des.setText("Student needs to clear 10 Theory papers along with 2 pratical exams with 2 Projects. O-level students have to only clear 6 papers and 1 practical and 1 Major project, as they are exempted from papers which they have cleared. For more info refer to full syllabus");
        course_eligibility.setText("Graduation is Basic requirement. \n10+2 students can apply if they have cleared O-level");


        date_start.setText("1 july");
        date_end.setText("10th july");
        date_exam.setText("15th August");

        syllabus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(),loadurlActivity.class);
                i.putExtra("category","syllabus");
                i.putExtra("course","alevel");
                startActivity(i);

            }
        });

        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent temp = new Intent(getBaseContext(),loadurlActivity.class);
                temp.putExtra("category","notes");
                temp.putExtra("course","alevel");
                startActivity(temp);


            }
        });

        TextView examdates = findViewById(R.id.examdatebut);
        examdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent temp = new Intent(getBaseContext(),loadurlActivity.class);
                temp.putExtra("category","dates");
                temp.putExtra("course","alevel");
                startActivity(temp);
            }
        });

        TextView question = findViewById(R.id.question_but);
        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent temp = new Intent(getBaseContext(),loadurlActivity.class);
                temp.putExtra("category","question");
                temp.putExtra("course","alevel");
                startActivity(temp);
            }
        });
        TextView result = findViewById(R.id.result_but);
        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent temp = new Intent(getBaseContext(),loadurlActivity.class);
                temp.putExtra("category","result");
                temp.putExtra("course","alevel");
                startActivity(temp);
            }
        });
        TextView apply = findViewById(R.id.apply_but);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent temp = new Intent(getBaseContext(),loadurlActivity.class);
                temp.putExtra("category","question");
                temp.putExtra("course","alevel");
                startActivity(temp);
            }
        });


    }
}