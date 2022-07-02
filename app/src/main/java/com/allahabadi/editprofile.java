package com.allahabadi;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class editprofile extends AppCompatActivity {
    String userid,cour;

    FirebaseAuth mAuth;
    FirebaseDatabase database;


    EditText name,phone,gender;
    TextView email,dob;
    Spinner courses;
    ImageView profileimage;
    LinearLayout phonelayout,doblayot;
    Button uploadbutton;   TextView updatebutton;
    Uri selectedImageUri;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        Intent i=getIntent();
        String isfromsignup=i.getStringExtra("signup");
        if(isfromsignup!=null){
        if(isfromsignup.equals("t")){
            getSupportActionBar().setTitle("Complete your Profile");
        }
        }
        else getSupportActionBar().hide();

        ProgressBar p = (ProgressBar)findViewById(R.id.progressBare);

        name= findViewById(R.id.nameprofe);
        email= findViewById(R.id.emailprofe);
        courses= findViewById(R.id.coursetexte);
        phone= findViewById(R.id.phonetxte);
        dob= findViewById(R.id.dobtxte);
        gender = findViewById(R.id.gendertxte);
        phonelayout = findViewById(R.id.phonelayout);
        LinearLayout edit= findViewById(R.id.linearLayout12e);

        ImageView editi= findViewById(R.id.imageV);
        profileimage= findViewById(R.id.profileimageviewe);
        uploadbutton= findViewById(R.id.uploadbuttone);
        progressBar = findViewById(R.id.progressBare);
        updatebutton= findViewById(R.id.updatebutton);


        edit.setVisibility(View.GONE);
        database= FirebaseDatabase.getInstance();

       FirebaseDatabase database= FirebaseDatabase.getInstance();
        mAuth= FirebaseAuth.getInstance();


            loadprof1();


            updatebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateprofile();
                }
            });
        editi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectimage();
            }
        });
        uploadbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                uploadbutton.setVisibility(View.INVISIBLE);
                uploadpic();
            }
        });


        courses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

                 cour= adapterView.getItemAtPosition(pos).toString();
                Log.e(TAG, "value of course"+cour);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                String Course= "Olevel";

            }
        });

    }

    private void updateprofile() {
        updatebutton.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        DatabaseReference myref = database.getReference("profile").child(userid);

        String nam= name.getText().toString();
        String phon = phone.getText().toString();
        if(nam==null){
            Toast.makeText(getBaseContext(),"Name is Empty",Toast.LENGTH_SHORT).show();
        }else{ myref.child("name").setValue(nam).addOnCompleteListener(OKC);
        }

        if (phone.length()!=0 )
            myref.child("phone").setValue(phon).addOnCompleteListener(OKC);
        String gend = gender.getText().toString();
         if(gend!=null) myref.child("gender").setValue(gend).addOnCompleteListener(OKC);
        myref.child("course").setValue(cour);


    }

    OnCompleteListener OKC = new OnCompleteListener() {
        @Override
        public void onComplete(@NonNull Task task) {
            progressBar.setVisibility(View.INVISIBLE);
        }
    };

    private void selectimage() {

            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(Intent.ACTION_GET_CONTENT);

            launchSomeActivity.launch(i);
        }

    private void uploadpic() {
        FirebaseStorage storage= FirebaseStorage.getInstance();
        final Long temp= System.currentTimeMillis();
        StorageReference storageRef = storage.getReference("profile").child(userid);
        if(selectedImageUri==null)
            Log.e("profile fragment","image uri is empty so can't upload");
        else
            storageRef.child("users").putFile(selectedImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    storageRef.child("users").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            Log.e("profile fragment","image uri is returned");
                            progressBar.setVisibility(View.GONE);
                            uploadbutton.setVisibility(View.GONE);
                            addiamgeuritoprofile(uri);

                        }
                    });
                }
            });
    }


    private void addiamgeuritoprofile(Uri uri) {
//        String url = String.valueOf(uri);
        String url = uri.toString();
        DatabaseReference dr = database.getReference("profile").child(userid).child("pic");
        dr.setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }

    private void loadprof1() {
        progressBar.setVisibility(View.INVISIBLE);
        userid = mAuth.getUid();
       if(userid!=null){
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

                    String pro = (String) dataSnapshot.child("pic").getValue();
                    gender.setText((String)dataSnapshot.child("gender").getValue());


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
        myref.addListenerForSingleValueEvent(postListener);

    }}

    private void loadprofilepic(String pro) {
        Picasso.with(profileimage.getContext()).load(pro).into(profileimage);
    }


    @Override
    protected void onStart() {


        FirebaseUser user= mAuth.getCurrentUser();
        if (user==null){
            Toast.makeText(getBaseContext(),"First Login your profile",Toast.LENGTH_LONG).show();
            startActivity( new Intent(getBaseContext(),signup.class));
            finish();

        }
        else {
            Log.e(TAG, "signed In"+user.getUid());
            userid= user.getUid().toString();
        }
        super.onStart();
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