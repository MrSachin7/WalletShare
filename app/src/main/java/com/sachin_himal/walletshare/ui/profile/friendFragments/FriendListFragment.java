package com.sachin_himal.walletshare.ui.profile.friendFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sachin_himal.walletshare.R;
import com.sachin_himal.walletshare.entity.User;
import com.sachin_himal.walletshare.ui.split.CardAdapter;

import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FriendListFragment#} factory method to
 * create an instance of this fragment.
 */
public class FriendListFragment extends Fragment {

    RecyclerView recyclerView;
    AllFriendListAdapter friendListAdapter;
    TextView friendName;
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


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        friendViewModel = new ViewModelProvider(this).get(FriendViewModel.class);

        recyclerView = view.findViewById(R.id.friendListRecyclerView);
        friendListAdapter = new AllFriendListAdapter();
        recyclerView.setAdapter(friendListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));




    }

    private void allFriendListObserver(List<User> users) {
        friendListAdapter.setAllFriendList(users);
        System.out.println(users.toString());
    }


}