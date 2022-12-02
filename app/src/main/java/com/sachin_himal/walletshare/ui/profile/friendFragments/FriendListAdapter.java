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


    private List<User> allReceivedFriendList = new ArrayList<>();

    public CardAdapter.OnItemClickListener listener;
    //Constructor

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public  void onItemClickListener(CardAdapter.OnItemClickListener listener){
        this.listener = listener;
    }
    public FriendListAdapter() {

    }



    @NonNull
    @Override
    public FriendListGroup onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_list, parent, false);

        return new FriendListGroup(view,listener);

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
        AppCompatButton accept,reject;

        public FriendListGroup(@NonNull View itemView, CardAdapter.OnItemClickListener listener) {
            super(itemView);
            friendNameTextView = itemView.findViewById(R.id.friendNameInList);
            accept = itemView.findViewById(R.id.acceptFriendRequest);
            reject = itemView.findViewById(R.id.rejectFriendRequest);

            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position!= RecyclerView.NO_POSITION){
                        listener.onItemClick(position);
                    }
                }
            });

        }

        public void setDetails(User user) {
            friendNameTextView.setText(user.retrieveFullName());
        }


    }
}
