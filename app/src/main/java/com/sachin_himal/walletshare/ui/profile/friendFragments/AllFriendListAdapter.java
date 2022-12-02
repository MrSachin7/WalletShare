package com.sachin_himal.walletshare.ui.profile.friendFragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sachin_himal.walletshare.R;
import com.sachin_himal.walletshare.entity.User;

import java.util.ArrayList;
import java.util.List;


public class AllFriendListAdapter extends RecyclerView.Adapter<AllFriendListAdapter.AllFriendListGroup> {

    private List<User> allFriendList = new ArrayList<>();

    public AllFriendListAdapter() {

    }

    public void setAllFriendList(List<User> allFriendList) {

        this.allFriendList = allFriendList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AllFriendListGroup onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_friend_list_cardview, parent, false);

        return new AllFriendListGroup(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllFriendListAdapter.AllFriendListGroup holder, int position) {
        holder.setDetails(allFriendList.get(position));


    }

    @Override
    public int getItemCount() {
        return allFriendList.size();
    }




    public class AllFriendListGroup extends RecyclerView.ViewHolder {
        TextView friendNamTextView;
        public AllFriendListGroup(@NonNull View itemView) {
            super(itemView);
            friendNamTextView = itemView.findViewById(R.id.friendNameInAllFriendList);
        }

        public void setDetails(User user) {

            friendNamTextView.setText(user.retrieveFullName());

        }
    }
}
