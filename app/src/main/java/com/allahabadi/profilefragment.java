package com.allahabadi;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
    TextView name,email,dob,courses,gender,phone;
    ImageView profileimage;
    LinearLayout phonelayout;
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
         gender = rootview.findViewById(R.id.gendertxt);
         phonelayout = rootview.findViewById(R.id.phonelayout);
         LinearLayout edit= rootview.findViewById(R.id.editbut);
        profileimage= rootview.findViewById(R.id.profileimageview);
        uploadbutton= rootview.findViewById(R.id.uploadbutton);
        progressBar = rootview.findViewById(R.id.progressBar);

//         mAuth= FirebaseAuth.getInstance();



        progressBar.setVisibility(View.INVISIBLE);

        database= FirebaseDatabase.getInstance();
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),editprofile.class);
                startActivity(i);
            }
        });


       if(userid!=null) {
           loadprof();

       }



       TextView logout= rootview.findViewById(R.id.Lohgin);
       logout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               new AlertDialog.Builder(getContext())
                       .setTitle("Log out")
                       .setMessage("Are you sure you want to Log out?")

                       // Specifying a listener allows you to take an action before dismissing the dialog.
                       // The dialog is automatically dismissed when a dialog button is clicked.
                       .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int which) {
                               // Continue with delete operationmAuth.signOut();
                               mAuth.signOut();
                               startActivity(new Intent(getContext(),MainActivity.class));


                           }
                       })

                       // A null listener allows the button to dismiss the dialog and take no further action.
                       .setNegativeButton(android.R.string.no, null)
                       .setIcon(android.R.drawable.ic_dialog_alert)
                       .show();



           }
       });
//seting new image



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
                    gender.setText((String)dataSnapshot.child("gender").getValue());

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