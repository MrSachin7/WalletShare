package com.sachin_himal.walletshare.ui.profile.friendFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sachin_himal.walletshare.R;
import com.sachin_himal.walletshare.entity.User;

import java.util.HashMap;
import java.util.List;


public class AcceptRequestFragment extends Fragment {


    RecyclerView recyclerView;
    FriendListAdapter friendListAdapter;
    FriendViewModel friendViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_accept_request, container, false);


        friendViewModel = new ViewModelProvider(this).get(FriendViewModel.class);

        friendViewModel.searchForFriendRequest();
        //TODO : Does not work :

//        friendViewModel.getAllReceivedRequests().observe(getViewLifecycleOwner(),this::friendListObserver);

        friendViewModel.getAllReceievedFriendRequest().observe(getViewLifecycleOwner(),this::friendRequestObserver);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerViewsCardsForAcceptingRequest);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        friendListAdapter = new FriendListAdapter();
        recyclerView.setAdapter(friendListAdapter);





    }

    private void friendRequestObserver(List<User> users) {

        if (users !=null && !users.isEmpty()){
            friendListAdapter.setAllReceivedFriendList(users);
        }
    }

//    private void friendListObserver(HashMap<String, String> stringStringHashMap) {
//        if (stringStringHashMap != null) {
//            friendListAdapter.setFriendList(stringStringHashMap);
//            friendListAdapter.setFriendRequest(true);
//        }
//    }

}