package com.allahabadi;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DisscussFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DisscussFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    quesAdapter adapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    DatabaseReference datar;

    ArrayList<Question> userlist;
    public DisscussFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DisscussFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DisscussFragment newInstance(String param1, String param2) {
        DisscussFragment fragment = new DisscussFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_disscuss, container, false);
        // Inflate the layout for this fragment

        RecyclerView recyclerView= rootview.findViewById(R.id.recyclerView);
        FloatingActionButton fab= rootview.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),addquestActivity.class);
                startActivity(i);
            }
        });
        userlist = new ArrayList<Question>();



        datar= FirebaseDatabase.getInstance().getReference();

         adapter = new quesAdapter(userlist);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        setuserlist( );
        return rootview;
    }

    private void setuserlist() {
        userlist.clear();
       DatabaseReference dr= datar.child("posts");
       dr.addChildEventListener(new ChildEventListener() {

           @Override
           public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

               String s="",imageuri="";
               String post = (String) snapshot.child("post").getValue();

               String uid = (String) snapshot.child("uid").getValue();
               Long time = (Long) snapshot.child("time").getValue();

               String key= snapshot.getKey();
//               reading uid details
               loaduiddetail(uid,post,time,key);






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

    private void loaduiddetail(String uid,String post, Long time,String key) {

       datar.child("profile").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String s = (String) snapshot.child("name").getValue();
                Log.e("Discuss frag adding name",snapshot.getKey());
                String imageuri= (String) snapshot.child("pic").getValue();


                userlist.add(0,new Question(key,post,time,imageuri,s));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}