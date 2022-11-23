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

    private Context context;
    private List<Group> groupArrayList;

    //Constructor


    public CardAdapter(Context context, ArrayList<Group> groupArrayList) {
        this.context = context;
        this.groupArrayList = groupArrayList;
    }

    public  void setGroupArrayList(List<Group> groups){
        if (groups == null) {
            return;
        }
        this.groupArrayList = groups;
    }


    //Card Adapter
    @NonNull
    @Override
    public GroupHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.split_cardview_recycleview,parent,false);
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

    class  GroupHolder extends RecyclerView.ViewHolder{

        private TextView groupName, amount;
        public GroupHolder(@NonNull View itemView) {
            super(itemView);

            groupName = itemView.findViewById(R.id.groupNameForCard);
            amount = itemView.findViewById(R.id.groupAmount);

        }

        void setDetails(Group group){

            groupName.setText(group.getGroupName());
            amount.setText(group.getAmount()+"");
        }


    }
}
