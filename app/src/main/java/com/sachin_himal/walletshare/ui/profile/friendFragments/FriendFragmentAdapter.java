package com.sachin_himal.walletshare.ui.profile.friendFragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FriendFragmentAdapter  extends FragmentStateAdapter {

    public FriendFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position==1){
            return new SendFriendRequestFragment();
        }if(position ==2){
            return  new AcceptRequestFragment();
        }else return new FriendListFragment();

    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
