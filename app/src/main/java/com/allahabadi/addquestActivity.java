package com.allahabadi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class addquestActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addquest);
        TextView post = findViewById(R.id.post_but);
        TextView quest= findViewById( R.id.question_edit_txt);

        mAuth= FirebaseAuth.getInstance();


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               String question= quest.getText().toString();
               String user = currentUser.getUid();
                FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference("posts");
                Question q = new Question(user, question,System.currentTimeMillis());
               String key= databaseReference.push().getKey();
                databaseReference.child(key).setValue(q).addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                DatabaseReference db= firebaseDatabase.getReference("profilepost");
                                db.child(user).push().setValue(key);
                            }
                        }
                );

            }
        });
    }

    @Override
    protected void onStart() {

         currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
// No user is signed in.
            Toast.makeText(getBaseContext(),"Need Login to post question",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getBaseContext(),signup.class));
            finish();

        } else {
// User logged in


    }
        super.onStart();
}}