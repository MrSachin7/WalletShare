package com.sachin_himal.walletshare.view.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.sachin_himal.walletshare.R;
import com.sachin_himal.walletshare.view.Fragments.HomeFragment;
import com.sachin_himal.walletshare.view.Fragments.NewTranscationFragment;
import com.sachin_himal.walletshare.view.Fragments.ProfileFragment;
import com.sachin_himal.walletshare.view.addExpenditure.AddExpenditure;
import com.sachin_himal.walletshare.view.login.LoginActivity;

public class MainActivity extends AppCompatActivity  implements NavigationBarView.OnItemSelectedListener {


    private BottomNavigationView bottomNavigationView;
    private HomeFragment homeFragment ;
    private NewTranscationFragment newTranscationFragment;
    private ProfileFragment profileFragment;


    private MainActivityViewModal viewModal;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModal = new ViewModelProvider(this).get(MainActivityViewModal.class);


        checkIfSignedIn();


        setContentView(R.layout.activity_main);


        //Initializing fragment and setting up
        setUpFragments();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);



        bottomNavigationView.setBackground(null);

        bottomNavigationView.setOnItemSelectedListener(this);


        bottomNavigationView.setSelectedItemId(R.id.menu_dashboard);




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, AddExpenditure.class));
                viewModal.signOut();
            }

        });
    }

    private void checkIfSignedIn() {
        viewModal.getCurrentUser().observe(this, user ->{
            if (user ==null){
                startLoginActivity();
            }
            else{
                viewModal.init();
            }

        });


    }

    private void startLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }


    // Initializing Bottom Navigation

    private void setUpFragments() {
        homeFragment = new HomeFragment();
        newTranscationFragment = new NewTranscationFragment();
        profileFragment = new ProfileFragment();

    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_dashboard:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                return true;
            case R.id.menu_addTransaction:

                getSupportFragmentManager().beginTransaction().replace(R.id.container, newTranscationFragment).commit();
                return true;

            case R.id.menu_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, profileFragment).commit();
                return true;

        }


        return false;
    }

    public void changeNavigationFromFragment(){

               bottomNavigationView.setSelectedItemId(R.id.menu_addTransaction);
    }



}