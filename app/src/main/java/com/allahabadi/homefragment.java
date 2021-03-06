package com.allahabadi;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link homefragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class homefragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public homefragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment homefragment.
     */
    // TODO: Rename and change types and number of parameters
    public static homefragment newInstance(String param1, String param2) {
        homefragment fragment = new homefragment();
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
        // Inflate the layout for this fragment

        View rootview = inflater.inflate(R.layout.fragment_homefragment, container, false);
        ConstraintLayout button= rootview.findViewById(R.id.imageView);
        LinearLayout olevel= rootview.findViewById(R.id.topvi);
        LinearLayout alevel= rootview.findViewById(R.id.aview);
        LinearLayout blevel= rootview.findViewById(R.id.bview_linear);
        TextView t= (TextView) rootview.findViewById(R.id.main_banner_text);
        FirebaseAuth mAuth= FirebaseAuth.getInstance();

        if ( (mAuth.getCurrentUser())==null){

            t.setText("Click to Login");
           openSignin(button);
        }else{
            t.setText("Latest Update");
        }



        olevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getContext(),"Button-clicked!",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getContext(),olevel.class);
                startActivity(i);
            }
        });

        alevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),Alevel.class);
                startActivity(i);
            }
        });

        blevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),Blevel.class);
                startActivity(i);
            }
        });



        return rootview;
    }

    private void openSignin(ConstraintLayout button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getContext(),"Button-clicked!",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getContext(),signup.class);
                startActivity(i);
            }
        });
    }
}