package com.sachin_himal.walletshare.ui.friendFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sachin_himal.walletshare.R;
import com.sachin_himal.walletshare.entity.User;

import java.util.List;


public class FriendListFragment extends Fragment {

    RecyclerView recyclerView;
    AllFriendListAdapter friendListAdapter;
    FriendViewModel friendViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_friend_list, container, false);
        friendViewModel = new ViewModelProvider(this).get(FriendViewModel.class);



        friendViewModel.initializeFriendKey();
        friendViewModel.searchForALlFriends();

        friendViewModel.getALlFriendList().observe(getViewLifecycleOwner(),this::allFriendListObserver);

        recyclerView = view.findViewById(R.id.friendListRecyclerView);
        friendListAdapter = new AllFriendListAdapter(friendViewModel);
        recyclerView.setAdapter(friendListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    private void allFriendListObserver(List<User> users) {
        if(users != null){
            friendListAdapter.setAllFriendList(users);

        }

    }


}