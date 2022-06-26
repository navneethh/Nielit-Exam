package com.allahabadi;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link profilefragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class profilefragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    FirebaseDatabase database;
    FirebaseAuth mAuth;
    String userid;
    DatabaseReference myref;
    TextView name,email,dob,courses,phone;
    ImageView profileimage;
    LinearLayout phonelayout,doblayot;
    Button uploadbutton;
    Uri selectedImageUri;
    ProgressBar progressBar;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public profilefragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment profilefragment.
     */
    // TODO: Rename and change types and number of parameters
    public static profilefragment newInstance(String param1, String param2) {
        profilefragment fragment = new profilefragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        mAuth= FirebaseAuth.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if(currentUser != null){
            Log.e(TAG, "signed In");
            userid= currentUser.getUid();


        }
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_profilefragment, container, false);


         name= rootview.findViewById(R.id.nameprof);
         email= rootview.findViewById(R.id.emailprof);
         courses= rootview.findViewById(R.id.coursetxt);
         phone= rootview.findViewById(R.id.phonetxt);
         dob= rootview.findViewById(R.id.dobtxt);
         phonelayout = rootview.findViewById(R.id.phonelayout);
        doblayot = rootview.findViewById(R.id.doblayout);
        profileimage= rootview.findViewById(R.id.profileimageview);
        uploadbutton= rootview.findViewById(R.id.uploadbutton);
        progressBar = rootview.findViewById(R.id.progressBar);

        ImageView editi= rootview.findViewById(R.id.imageView4);
//         mAuth= FirebaseAuth.getInstance();



        progressBar.setVisibility(View.INVISIBLE);

        database= FirebaseDatabase.getInstance();


       if(userid!=null) {
           loadprof();

       }



//seting new image
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

        // Inflate the layout for this fragment
        return rootview;
    }

    private void loadprofilepic(String uri) {

        Picasso.with(profileimage.getContext()).load(uri).into(profileimage);

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
                progressBar.setVisibility(View.GONE);
                uploadbutton.setVisibility(View.GONE);
            }
        });
    }

    void loadprof() {

        userid = FirebaseAuth.getInstance().getUid().toString();
        myref = database.getReference("profile").child(userid);


        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                String temp = "";
                temp = (String) dataSnapshot.child("name").getValue();


                if (temp == null) {
                    Toast.makeText(getContext(), "Data is null", Toast.LENGTH_LONG).show();
                } else {


                    name.setText((String) dataSnapshot.child("name").getValue());
                    email.setText(dataSnapshot.child("email").getValue().toString());
                    courses.setText((String) dataSnapshot.child("course").getValue());
                    String pro = (String) dataSnapshot.child("pic").getValue();


                    phone.setText((String) dataSnapshot.child("phone").getValue());
                    dob.setText((String) dataSnapshot.child("dob").getValue());

                    if (pro != null)
                        loadprofilepic(pro);

                    // ..
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
    void selectimage(){
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        launchSomeActivity.launch(i);
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