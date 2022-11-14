package com.sachin_himal.walletshare.view.login;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseUser;
import com.sachin_himal.walletshare.view.home.MainActivity;
import com.sachin_himal.walletshare.R;


public class LoginActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager2;

    LoginAdapter loginAdapter;
    FloatingActionButton facebook,google,twitter;
    private static final int REQ_ONE_TAP = 2;  // Can be any integer unique to the Activity.
    private boolean showOneTapUI = true;


    private UserViewModal viewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        initializeAllFields();
        setupAdapter();
        initializeTab();

        viewModel = new ViewModelProvider(this).get(UserViewModal.class);
        viewModel.getCurrentUser().observe(this, this::loginStatusChanged);



    }





    private void loginStatusChanged(FirebaseUser firebaseUser) {

        if(firebaseUser !=null){
            Toast.makeText(this, firebaseUser.getEmail()+ "logged in", Toast.LENGTH_SHORT).show();
            openMainView();
        }

    }

    private void openMainView() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }



    private void initializeTab() {

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            if (position == 0) tab.setText(R.string.login);
            else if (position == 1) tab.setText(R.string.sign_up);
        }
        ).attach();
    }

    private void setupAdapter() {
        loginAdapter = new LoginAdapter(getSupportFragmentManager(), getLifecycle());
        loginAdapter.addFragment(new LoginTabFragment());
        loginAdapter.addFragment(new SignUpTabFragment());
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager2.setAdapter(loginAdapter);
    }

    private void initializeAllFields() {
        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.view_pager2);
        facebook = findViewById(R.id.facebook_btn);
        twitter = findViewById(R.id.twitter_btn);
        google = findViewById(R.id.google_btn);


    }
}