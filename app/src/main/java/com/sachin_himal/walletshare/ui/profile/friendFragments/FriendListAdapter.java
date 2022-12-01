package com.sachin_himal.walletshare.ui.profile.friendFragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sachin_himal.walletshare.R;

import java.util.ArrayList;

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.FriendListGroup> {



    private Context context;
    private ArrayList<String> userName;



    public FriendListAdapter(Context context) {
        this.context = context;

    }

    public void setUserName(ArrayList<String> userName) {
        this.userName = userName;
    }

    @NonNull
    @Override
    public FriendListGroup onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.friend_list,parent,false);

        return new FriendListGroup(view);

    }

    @Override
    public void onBindViewHolder(@NonNull FriendListGroup holder, int position) {
        holder.setDetails(userName.get(position));
    }

    @Override
    public int getItemCount() {
        return userName.size();
    }




    public class FriendListGroup extends RecyclerView.ViewHolder {
        TextView friendNameTextView;
        public FriendListGroup(@NonNull View itemView) {
            super(itemView);
            friendNameTextView = itemView.findViewById(R.id.friendNameInList);

        }

        public void setDetails(String userName) {
            friendNameTextView.setText(userName);
        }
    }
}
