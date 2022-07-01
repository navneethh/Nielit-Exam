package com.allahabadi;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class editprofile extends AppCompatActivity {
    String userid;

    FirebaseAuth mAuth;
    FirebaseDatabase database;


    EditText name;
    TextView email,dob,courses,phone;
    ImageView profileimage;
    LinearLayout phonelayout,doblayot;
    Button uploadbutton;
    Uri selectedImageUri;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        getSupportActionBar().hide();
        ProgressBar p = (ProgressBar)findViewById(R.id.progressBare);

        name= findViewById(R.id.nameprofe);
        email= findViewById(R.id.emailprofe);
        courses= findViewById(R.id.coursetxte);
        phone= findViewById(R.id.phonetxte);
        dob= findViewById(R.id.dobtxte);
        phonelayout = findViewById(R.id.phonelayout);
        LinearLayout edit= findViewById(R.id.linearLayout12e);

        ImageView editi= findViewById(R.id.imageV);
        profileimage= findViewById(R.id.profileimageviewe);
        uploadbutton= findViewById(R.id.uploadbuttone);
        progressBar = findViewById(R.id.progressBare);

        edit.setVisibility(View.GONE);
        database= FirebaseDatabase.getInstance();

       FirebaseDatabase database= FirebaseDatabase.getInstance();



            loadprof1();
        editi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectimage();
            }
        });


    }

    private void selectimage() {

            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(Intent.ACTION_GET_CONTENT);

            launchSomeActivity.launch(i);
        }


    private void loadprof1() {
        progressBar.setVisibility(View.INVISIBLE);
        userid = FirebaseAuth.getInstance().getUid().toString();
       DatabaseReference myref = database.getReference("profile").child(userid);


        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                String temp = "";
                temp = (String) dataSnapshot.child("name").getValue();



                if (temp == null) {
                    Toast.makeText(getBaseContext(), "Data is null", Toast.LENGTH_LONG).show();
                } else {


                    name.setText((String) dataSnapshot.child("name").getValue());
                    email.setText(dataSnapshot.child("email").getValue().toString());
                    courses.setText((String) dataSnapshot.child("course").getValue());
                    String pro = (String) dataSnapshot.child("pic").getValue();


                    phone.setText((String) dataSnapshot.child("phone").getValue());
                    dob.setText((String) dataSnapshot.child("dob").getValue());

                    if (pro != null)
                        loadprofilepic(pro);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        myref.addValueEventListener(postListener);

    }

    private void loadprofilepic(String pro) {
        Picasso.with(profileimage.getContext()).load(pro).into(profileimage);
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if (user==null){
            Toast.makeText(getBaseContext(),"First Login your profile",Toast.LENGTH_LONG).show();
            startActivity( new Intent(getBaseContext(),signup.class));
        }
        else {
            Log.e(TAG, "signed In");
            userid= user.getUid().toString();
        }
    }

    ActivityResultLauncher<Intent> launchSomeActivity
            = registerForActivityResult(
            new ActivityResultContracts
                    .StartActivityForResult(),
            result -> {
                if (result.getResultCode()
                        == Activity.RESULT_OK) {
                    Intent data = result.getData();

                    // do your operation from here....
                    if (data != null
                            && data.getData() != null) {
                        selectedImageUri = data.getData();
                        Bitmap selectedImageBitmap;

                        profileimage.setImageURI(selectedImageUri);
                        uploadbutton.setVisibility(View.VISIBLE);
                    }
                }
            });
}