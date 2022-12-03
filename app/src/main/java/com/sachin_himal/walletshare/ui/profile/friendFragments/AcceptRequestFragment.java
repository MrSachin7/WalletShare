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
import com.sachin_himal.walletshare.ui.MainActivity;
import com.sachin_himal.walletshare.ui.split.CardAdapter;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.HashMap;
import java.util.List;


public class AcceptRequestFragment extends Fragment {


    RecyclerView recyclerView;
    FriendListAdapter friendListAdapter;
    FriendViewModel friendViewModel;List<User> users;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_accept_request, container, false);


        friendViewModel = new ViewModelProvider(this).get(FriendViewModel.class);

        friendViewModel.searchForFriendRequest();

        friendViewModel.getAllReceievedFriendRequest().observe(getViewLifecycleOwner(),this::friendRequestObserver);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerViewsCardsForAcceptingRequest);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        friendListAdapter = new FriendListAdapter(friendViewModel);
        recyclerView.setAdapter(friendListAdapter);




    }

    private void friendClicked(User user) {

    }


    private void addFriend(User user) {

        friendViewModel.acceptFriendRequest(user.getUid());


        friendViewModel.getSuccessMessage().observe(getViewLifecycleOwner(), this::succeessObserver);

    }

    private void friendRequestObserver(List<User> users) {

        if (users !=null && !users.isEmpty()){
            this.users = users;
            friendListAdapter.setAllReceivedFriendList(users);
        }
    }

    private void succeessObserver(String s) {
        if (s==null || s.isEmpty()) return;
        FancyToast.makeText(getContext(), s,FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
        ((MainActivity)getActivity()).changeFragment(R.id.friendFragment);
        friendViewModel.searchForFriendRequest();

    }
//    private void friendListObserver(HashMap<String, String> stringStringHashMap) {
//        if (stringStringHashMap != null) {
//            friendListAdapter.setFriendList(stringStringHashMap);
//            friendListAdapter.setFriendRequest(true);
//        }
//    }

}