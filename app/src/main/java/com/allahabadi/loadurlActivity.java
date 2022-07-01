package com.allahabadi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class loadurlActivity extends AppCompatActivity {
    String pdfuri;
    WebView web;
    ProgressBar p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus);
        getSupportActionBar().hide();
        web= findViewById(R.id.webView);
        p = findViewById(R.id.progressBar3);
        Intent i = getIntent();
        String course = i.getStringExtra("course");
        String cat= i.getStringExtra("category");
        DatabaseReference syllabusref= FirebaseDatabase.getInstance().getReference("syllabus");

        if (cat!=null){
            getsyllabusurl(cat,course,syllabusref);}
        else {
            if(course!= null){
                getsyllabusurl(course,syllabusref);
            }
        }
        p.setVisibility(View.VISIBLE);
    }
    private void getsyllabusurl(String cate,String course, DatabaseReference syllabusref) {

        syllabusref.child(cate).child(course).addValueEventListener(new ValueEventListener( ) {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                pdfuri=(String) snapshot.getValue();
                loadpdf(pdfuri);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void getsyllabusurl(String course, DatabaseReference syllabusref) {

        syllabusref.child(course).addValueEventListener(new ValueEventListener( ) {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                pdfuri=(String) snapshot.getValue();
                loadpdf(pdfuri);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void loadpdf(String pdfuri) {
        web.loadUrl(pdfuri);
        web.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                p.setVisibility(View.INVISIBLE);
            }
        });

    }

}