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

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.FriendListGroup> {


    private List<User> allReceivedFriendList = new ArrayList<>();


    public FriendListAdapter() {

    }

//    public void setFriendList(HashMap<String, String> friendList) {
//        if (friendList == null || friendList.size() <0){
//            return;
//        }
//        this.friendList = friendList;
//    }



    @NonNull
    @Override
    public FriendListGroup onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_list, parent, false);

        return new FriendListGroup(view);

    }

    @Override
    public void onBindViewHolder(@NonNull FriendListGroup holder, int position) {
        holder.setDetails(allReceivedFriendList.get(position));
    }

    @Override
    public int getItemCount() {
        return allReceivedFriendList.size();
    }

    public void setAllReceivedFriendList(List<User> users) {
        allReceivedFriendList.clear();
        System.out.println("ALl users \n\n\n");
        users.forEach(System.out::println);

        System.out.println("ALl users \n\n\n");

        allReceivedFriendList.addAll(users);
        notifyDataSetChanged();


    }


    public class FriendListGroup extends RecyclerView.ViewHolder {
        TextView friendNameTextView;

        public FriendListGroup(@NonNull View itemView) {
            super(itemView);
            friendNameTextView = itemView.findViewById(R.id.friendNameInList);

        }

        public void setDetails(User user) {
            friendNameTextView.setText(user.getFirstName() + " " + user.getLastName());
        }
    }
}
