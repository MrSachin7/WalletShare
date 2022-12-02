package com.sachin_himal.walletshare.ui.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.sachin_himal.walletshare.R;
import com.sachin_himal.walletshare.ui.profile.friendFragments.FriendFragmentAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FriendFragment#} factory method to
 * create an instance of this fragment.
 */
public class FriendFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    FriendFragmentAdapter friendFragmentAdapter;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       return inflater.inflate(R.layout.fragment_friend, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout = view.findViewById(R.id.friendTab);
        viewPager2 = view.findViewById(R.id.friendViewPager);
        tabLayout.addTab(tabLayout.newTab().setText("Friend List"));
        tabLayout.addTab(tabLayout.newTab().setText("Send Friend Request"));

        tabLayout.addTab(tabLayout.newTab().setText("Accept Friend Request"));
        FragmentManager fragmentManager = getParentFragmentManager();
        friendFragmentAdapter = new FriendFragmentAdapter(fragmentManager,getLifecycle());
        viewPager2.setAdapter(friendFragmentAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));

            }

        });

    }
}