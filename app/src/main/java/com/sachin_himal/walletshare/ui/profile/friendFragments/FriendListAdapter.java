package com.sachin_himal.walletshare.ui.profile.friendFragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.sachin_himal.walletshare.R;
import com.sachin_himal.walletshare.entity.User;
import com.sachin_himal.walletshare.ui.split.CardAdapter;

import java.util.ArrayList;
import java.util.List;


// This Adapter is For friend Request adapter


public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.FriendListGroup> {


    private final FriendViewModel viewModel;
    private List<User> allReceivedFriendList = new ArrayList<>();

    public FriendListAdapter.OnItemClickListener listener;



    //Constructor

    public interface OnItemClickListener{
        void onItemClick(User user);
    }


    public FriendListAdapter(FriendViewModel viewModel) {
        this.viewModel = viewModel;

    }



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
        allReceivedFriendList.addAll(users);
        notifyDataSetChanged();


    }


    public class FriendListGroup extends RecyclerView.ViewHolder {
        TextView friendNameTextView;
        AppCompatButton accept,reject;

        public FriendListGroup(@NonNull View itemView) {
            super(itemView);
            friendNameTextView = itemView.findViewById(R.id.friendNameInList);
            accept = itemView.findViewById(R.id.acceptFriendRequest);
            reject = itemView.findViewById(R.id.rejectFriendRequest);

            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewModel.acceptFriendRequest(allReceivedFriendList.get(getAdapterPosition()).getUid());
                }
            });

            reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewModel.rejectFriendRequests(allReceivedFriendList.get(getAdapterPosition()).getUid());
                }
            });






        }

        public void setDetails(User user) {
            friendNameTextView.setText(user.retrieveFullName());
        }


    }
}
