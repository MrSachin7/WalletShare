package com.sachin_himal.walletshare.ui.profile.friendFragments;

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
import com.sachin_himal.walletshare.ui.MainActivity;
import com.shashank.sony.fancytoastlib.FancyToast;

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

        friendViewModel.getAllReceivedFriendRequest().observe(getViewLifecycleOwner(),this::friendRequestObserver);
        friendViewModel.getSuccessMessage().observe(getViewLifecycleOwner(),this::successMessageObserver);

        recyclerView = view.findViewById(R.id.recyclerViewsCardsForAcceptingRequest);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        friendListAdapter = new FriendListAdapter(friendViewModel);
        recyclerView.setAdapter(friendListAdapter);
        return view;
    }

    private void successMessageObserver(String successMessage) {
        if(successMessage != null){
            FancyToast.makeText(getActivity(),successMessage,FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
        }
    }


    private void addFriend(User user) {
        friendViewModel.acceptFriendRequest(user.getUid());
        friendViewModel.getSuccessMessage().observe(getViewLifecycleOwner(), this::successObserver);

    }

    private void friendRequestObserver(List<User> users) {

        if (users !=null){
            this.users = users;
            friendListAdapter.setAllReceivedFriendList(users);
        }
    }

    private void successObserver(String s) {
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