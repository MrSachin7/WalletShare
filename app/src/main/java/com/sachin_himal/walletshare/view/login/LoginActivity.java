package com.sachin_himal.walletshare.view.login;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.textfield.TextInputLayout;
import com.sachin_himal.walletshare.R;


public class LoginActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager2;

    LoginAdapter loginAdapter;
    FloatingActionButton facebook,google,twitter;

    private LoginViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        initializeAllFields();
        setupAdapter();
        initializeTab();

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);


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