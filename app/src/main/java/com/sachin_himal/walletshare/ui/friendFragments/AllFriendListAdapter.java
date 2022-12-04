package com.sachin_himal.walletshare.ui.friendFragments;

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

import de.hdodenhof.circleimageview.CircleImageView;


public class AllFriendListAdapter extends RecyclerView.Adapter<AllFriendListAdapter.AllFriendListGroup> {

    private List<User> allFriendList = new ArrayList<>();
    private final FriendViewModel viewModel;

    public AllFriendListAdapter(FriendViewModel viewModel) {
        this.viewModel = viewModel;
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
        CircleImageView friendImageView;
        public AllFriendListGroup(@NonNull View itemView) {
            super(itemView);
            friendNamTextView = itemView.findViewById(R.id.friendNameInAllFriendList);
            friendImageView = itemView.findViewById(R.id.friendProfileImage);
        }

        public void setDetails(User user) {
            viewModel.searchProfileImage(user.getUid());
            friendNamTextView.setText(user.retrieveFullName());
            viewModel.searchProfileImage(user.getUid());
            viewModel.getProfileImage().observeForever(uri -> {
                if (uri != null) {
                    friendImageView.setImageURI(uri);
                }
            });
        }
    }
}
