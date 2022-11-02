package com.sachin_himal.walletshare.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.sachin_himal.walletshare.R;

public class LoginActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager;
    FloatingActionButton facebook, google, twitter;
    LoginAdapter loginAdapter;
    float alpha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        initializeAllFields();
        initializeTab();
        setupAdapter();
        showAnimation();



    }

    private void showAnimation() {
        facebook.setTranslationY(300);
        google.setTranslationY(300);
        twitter.setTranslationY(300);
        tabLayout.setTranslationY(300);

        facebook.setAlpha(alpha);
        google.setAlpha(alpha);
        twitter.setAlpha(alpha);
        tabLayout.setAlpha(alpha);

        facebook.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        google.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        twitter.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        tabLayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
    }

    private void setupAdapter() {
        loginAdapter = new LoginAdapter(getSupportFragmentManager(), getLifecycle()) ;
        loginAdapter.addFragment(new LoginTabFragment());
        loginAdapter.addFragment(new SignUpTabFragment());
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager.setAdapter(loginAdapter);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if (position==0) tab.setText(R.string.login);
            else if (position==1) tab.setText(R.string.sign_up);

        }).attach();

    }

    private void initializeTab() {

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

    }

    private void initializeAllFields() {
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        facebook = findViewById(R.id.fab_facebook);
        google = findViewById(R.id.fab_google);
        twitter = findViewById(R.id.fab_twitter);
    }
}