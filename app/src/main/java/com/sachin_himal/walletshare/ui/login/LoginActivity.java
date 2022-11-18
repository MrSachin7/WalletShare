package com.sachin_himal.walletshare.ui.login;


import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseUser;
import com.sachin_himal.walletshare.ui.MainActivity;
import com.sachin_himal.walletshare.R;
import com.shashank.sony.fancytoastlib.FancyToast;


public class LoginActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager2;

    LoginAdapter loginAdapter;
    FloatingActionButton facebook,google,twitter;


    private UserViewModel viewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        initializeAllFields();
        setupAdapter();
        initializeTab();

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        viewModel.getCurrentUser().observe(this, this::loginStatusChanged);
    }





    private void loginStatusChanged(FirebaseUser firebaseUser) {

        if(firebaseUser !=null){
            FancyToast.makeText(this, firebaseUser.getEmail()+ "logged in", FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();
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