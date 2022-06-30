package com.allahabadi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class qdetail extends AppCompatActivity {
    DatabaseReference quesdata;
    ArrayList<Question> answerlist;
    commentAdapter adapter;
    String userid;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qdetail);
        EditText answerentry = (EditText) findViewById(R.id.editTextTextMultiLine);
        Button postreply = findViewById(R.id.Send);
         answerlist= new ArrayList<>();
         RecyclerView recyclerView = findViewById(R.id.recycleranswer);
        Intent i = getIntent();
        String key="";
       key = i.getStringExtra("key");
        String post= i.getStringExtra("post");
        String name= i.getStringExtra("name");
        Long time= i.getLongExtra("time",000000l);
        String imageurl= i.getStringExtra("image");
        answerlist.add(new Question(key,post,time,imageurl,name));
        quesdata = FirebaseDatabase.getInstance().getReference("qanswer").child(key);
        loaddata();
        adapter = new commentAdapter(answerlist);
        RecyclerView.LayoutManager layoutManager =new LinearLayoutManager(qdetail.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        answerentry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>25){
                    postreply.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        postreply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String answer= answerentry.getText().toString();
                String uid= user.getUid().toString();
                Long time = System.currentTimeMillis();
                answerentry.setText("");
                postreply.setEnabled(false);
                quesdata.push().setValue(new Question(uid,answer,time)).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
            }
        });


    }

    private void loaddata() {

        quesdata.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                   String post = (String) snapshot.child("post").getValue();
                    String uid = (String) snapshot.child("uid").getValue();
                    Long time = (Long) snapshot.child("time").getValue();
                    Log.e("form activity qdetail",uid);
                    loaduiddetail(uid,post,time);
                }


            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loaduiddetail(String uid,String post, Long time) {

        FirebaseDatabase.getInstance().getReference().child("profile").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String s = (String) snapshot.child("name").getValue();
                Log.e("qdetail",snapshot.getKey());
                String imageuri= (String) snapshot.child("pic").getValue();


                answerlist.add(new Question("key",post,time,imageuri,s));
                Log.e(s,post);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
         user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
Log.e("activity Qdetail","not logged in");
        }
        else{
                Log.e("activity Qdetail","Logged in  "+ user.getUid());
                userid=user.getUid().toString();

    }
    }
}