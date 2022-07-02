package com.allahabadi;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView Signupbutton= findViewById(R.id.signuptxt);
        TextView loginbutton= findViewById(R.id.Loginbut);
       final EditText emailT= findViewById(R.id.TextPersonName);
        EditText passwordT= findViewById(R.id.editTextTextPersonName);



        mAuth= FirebaseAuth.getInstance();

        Signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent (getBaseContext(),signup.class);
                startActivity(i);

                finish();
            }
        });

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String email= emailT.getText().toString();
               String pass= passwordT.getText().toString();
 
               if (email.length()!=0 && pass.length()!=0)
                   signin(email,pass);
               
            }
        });
    }

    private void signin(String email, String password) {


        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(login.this, "Wrong email or password.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });





    }
}