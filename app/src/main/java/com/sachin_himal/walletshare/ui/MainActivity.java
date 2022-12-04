package com.sachin_himal.walletshare.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sachin_himal.walletshare.R;

public class MainActivity extends AppCompatActivity  {



    private BottomNavigationView bottomNavigationView;
    NavController navController;


    private MainActivityViewModel viewModal;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModal = new ViewModelProvider(this).get(MainActivityViewModel.class);
        checkIfSignedIn();

        setContentView(R.layout.activity_main);
        initializeFields();



    }



    private void initializeFields() {

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        navController = Navigation.findNavController(this, R.id.fragmentContainerView);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

    }

    public void changeFragment(@IdRes int fragmentId){
        navController.navigate(fragmentId);
    }


    private void checkIfSignedIn() {
        viewModal.getCurrentUser().observe(this, user ->{
            if (user !=null){
                navController.navigate(R.id.homeFragment);
                bottomNavigationView.setVisibility(View.VISIBLE);
                viewModal.init();

            } else {
                bottomNavigationView.setVisibility(View.INVISIBLE);
                changeFragment(R.id.loginFragment);
            }
        });


    }




}