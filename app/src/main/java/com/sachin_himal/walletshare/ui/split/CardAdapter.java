package com.sachin_himal.walletshare.ui.split;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sachin_himal.walletshare.R;
import com.sachin_himal.walletshare.entity.Group;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.GroupHolder> {


    private List<Group> groupArrayList = new ArrayList<>();


    public OnItemClickListener  listener;
    //Constructor

    public interface OnItemClickListener{
        void onItemClick(Group group);
    }

    public void setItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public CardAdapter() {

    }

    public void setGroupArrayList(List<Group> groups){
        if (groups == null) {
            return;
        }

      this.groupArrayList = groups;
    }


    //Card Adapter
    @NonNull
    @Override
    public GroupHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.split_cardview_recycleview,parent,false);
        return new GroupHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupHolder holder, int position) {

        Group group = groupArrayList.get(position);
        holder.setDetails(group);
    }

    @Override
    public int getItemCount() {
        return groupArrayList.size();
    }








    //View Holder : GroupHolder

    public class  GroupHolder extends RecyclerView.ViewHolder{

        private TextView groupName, amount;

        public GroupHolder(@NonNull View itemView) {
            super(itemView);

            groupName = itemView.findViewById(R.id.groupNameForCard);
            amount = itemView.findViewById(R.id.groupAmount);


            itemView.setOnClickListener(v -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onItemClick(groupArrayList.get(getAdapterPosition()));
                }
            });



        }

        void setDetails(Group group){

            groupName.setText(group.getGroupName());
            amount.setText(group.getAmount()+" kr");
        }


    }


}
