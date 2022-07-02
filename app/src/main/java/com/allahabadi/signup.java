package com.allahabadi;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {
    FirebaseAuth mAuth;
    String email,password,name,cour;
    TextView emaill,pass,namet;
    FirebaseDatabase database; DatabaseReference myRef;
    String usid; ProgressBar p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

         emaill= findViewById(R.id.emailt);
         pass= findViewById(R.id.passt);
       p= findViewById(R.id.progressBar4);

        TextView loginbutton= findViewById(R.id.logintxt);
         namet= findViewById(R.id.namet);
        TextView sign= findViewById(R.id.signupt);


         database = FirebaseDatabase.getInstance();
        myRef = database.getReference("profile");




        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent (getBaseContext(),login.class);
                startActivity(i);
                finish();
            }
        });



// ...
// Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                              @Override
                                              public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

                                                 cour= adapterView.getItemAtPosition(pos).toString();
                                                  Log.e(TAG, "value of course"+cour);

                                              }

                                              @Override
                                              public void onNothingSelected(AdapterView<?> adapterView) {
                                                  String Course= "Olevel";

                                              }
                                          }
        );




        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=emaill.getText().toString();
                password = pass.getText().toString();
                name=namet.getText().toString();
                if(email.length()<3){
                    Toast.makeText(signup.this, "Enter proper email", Toast.LENGTH_SHORT).show();
                }else if (name.length()<4)
                    Toast.makeText(signup.this, "Name lenght is too small", Toast.LENGTH_SHORT).show();
                else if (password.length()<4)
                    Toast.makeText(signup.this, "Enter proper Password", Toast.LENGTH_SHORT).show();
                else {
                    signuo();
                    p.setVisibility(View.VISIBLE);
                }
            }});
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null){
            Log.e(TAG, "signed In");
            usid= mAuth.getUid();
            Log.e(TAG, usid);


        }
    }

 void signuo(){
     email=emaill.getText().toString();
     password = pass.getText().toString();
     name=namet.getText().toString();
     mAuth.createUserWithEmailAndPassword(email, password)
             .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task) {
                     if (task.isSuccessful()) {
                         // Sign in success, update UI with the signed-in user's information
                         Log.d(TAG, "signInWithEmail:success");
                         Toast.makeText(signup.this, "Signup complete",Toast.LENGTH_SHORT).show();
                         User userr= new User(name,email,cour);

//                         name = task.getResult().getUser().toString();
                         myRef.child(mAuth.getUid().toString()).setValue(userr).addOnCompleteListener(new OnCompleteListener<Void>() {
                             @Override
                             public void onComplete(@NonNull Task<Void> task) {
                                 Intent o= new Intent(getBaseContext(),editprofile.class);
                                 o.putExtra("signup","t");
                                 startActivity(o);
                                 p.setVisibility(View.INVISIBLE);
                                 finish();
                             }
                         });




                     } else {
                         // If sign in fails, display a message to the user.
                         Log.w(TAG, "signInWithEmail:failure", task.getException());
                         Toast.makeText(signup.this, "Authentication failed.",
                                 Toast.LENGTH_SHORT).show();

                     }
                 }
             });
 }



    class SpinnerActvity extends Activity  implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)
        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
        }
    }



}