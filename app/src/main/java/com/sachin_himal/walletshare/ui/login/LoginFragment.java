package com.sachin_himal.walletshare.ui.login;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.sachin_himal.walletshare.R;


public class LoginFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager2 viewPager2;

    LoginAdapter loginAdapter;
    FloatingActionButton facebook,google,twitter;


    private UserViewModel viewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initializeAllFields(view);
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        setupAdapter();
        initializeTab();

        return view;

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
        loginAdapter = new LoginAdapter(getChildFragmentManager(), getLifecycle());
        loginAdapter.addFragment(new LoginTabFragment());
        loginAdapter.addFragment(new SignUpTabFragment());
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager2.setAdapter(loginAdapter);
    }

    private void initializeAllFields(View view) {
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager2 = view.findViewById(R.id.view_pager2);
        facebook = view.findViewById(R.id.facebook_btn);
        twitter = view.findViewById(R.id.twitter_btn);
        google = view.findViewById(R.id.google_btn);


    }
}