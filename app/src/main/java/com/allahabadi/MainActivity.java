package com.allahabadi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.allahabadi.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment( new homefragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId())
            {
                case (R.id.menuhome):
                    replaceFragment( new homefragment());
                    break;
                case (R.id.menuforum):
                    replaceFragment( new DisscussFragment());
                    break;
                case (R.id.menuprofile):
                    replaceFragment( new profilefragment());
                    break;

            }

            return true;
        });


    }

   void replaceFragment(Fragment fragmentActivity){
       FragmentManager fragmentManager= getSupportFragmentManager();
       FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
       fragmentTransaction.replace(R.id.framelayoutt,fragmentActivity);
       fragmentTransaction.commit();

    }
}