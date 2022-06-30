package com.allahabadi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Blevel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olevel);
        TextView course_heading = (TextView) findViewById(R.id.course_mian_heading);
        TextView course_short_des = (TextView) findViewById(R.id.course_short_des);
        TextView course_des = (TextView) findViewById(R.id.course_desc);
        TextView course_eligibility = (TextView) findViewById(R.id.course_eligibility);


        TextView date_start= (TextView) findViewById(R.id.appli_date_start);
        TextView date_end= (TextView) findViewById(R.id.appli_date_end);
        TextView date_exam = (TextView) findViewById(R.id.exam_date);

        course_heading.setText("B-level by Nielit");
        course_short_des.setText("B-level is MCA equivalent course by Nielit which is recognised by Central Government for its vacancies. It is mainly 3 year course which is preferred to be completed in 2 years");
        course_des.setText("Student needs to clear 25 Theory papers along with 4 pratical exams with 2 Major Projects. A-level students have to only clear 15 papers and 2 practical and 1 Major project, as they are exempted from papers which they have cleared. For more info refer to full syllabus");
        course_eligibility.setText("Graduation is Basic requirement.");


        date_start.setText("1 july");
        date_end.setText("10th july");
        date_exam.setText("15th August");
    }
}