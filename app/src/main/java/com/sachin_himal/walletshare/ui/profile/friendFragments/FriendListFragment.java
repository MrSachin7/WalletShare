package com.sachin_himal.walletshare.ui.profile.friendFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sachin_himal.walletshare.R;

import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FriendListFragment#} factory method to
 * create an instance of this fragment.
 */
public class FriendListFragment extends Fragment {

    RecyclerView recyclerView;
    FriendListAdapter friendListAdapter;
    TextView friendName;
    FriendViewModel friendViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friend_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.friendListRecyclerView);
        friendListAdapter = new FriendListAdapter(getActivity());
        recyclerView.setAdapter(friendListAdapter);

        HashMap<String,String> a = friendViewModel.getALlFriends();
        friendListObserver(a);
    }

    private void friendListObserver(HashMap<String, String> stringStringHashMap) {
        friendListAdapter.setFriendList(stringStringHashMap);
    }


}