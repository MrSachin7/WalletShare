package com.sachin_himal.walletshare.ui.split;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sachin_himal.walletshare.R;
import com.sachin_himal.walletshare.entity.GroupUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MemberAdapter  extends RecyclerView.Adapter<MemberAdapter.MemberListForGroup>{


    private Context context;
    private List<HashMap<String,Integer>> userList;
    private ArrayList<String> userName;
    private List<GroupUser> groupUserList;


    public MemberAdapter(Context context) {
        this.context = context;
    }

    public void setGroupUserList(List<GroupUser> groupUserList) {
         this.groupUserList = groupUserList;
        notifyDataSetChanged();
    }

    /**
    public void setUserList(List<HashMap<String, Integer>> userList, ArrayList<String> userName) {
        if (userList == null && userName ==null){
            return;
        }
        this.userList = userList;
        this.userName = userName;
    }


     **/

    @NonNull
    @Override
    public MemberAdapter.MemberListForGroup onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.member_for_group,parent,false);
        return new MemberListForGroup(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberAdapter.MemberListForGroup holder, int position) {


        /**
        for (int i=0;i<userList.size();i++){

            for (String a : userList.get(i).keySet()) {
                System.out.println(userName.get(i));
                holder.setDetails(userName.get(i),userList.get(i).get(a));

            }
        }
         **/
        System.out.println("on bind called");

       holder.setDetails(groupUserList.get(position));



    }

    @Override
    public int getItemCount() {
       // return userList.size();
        return groupUserList.size();
    }




    public static class MemberListForGroup extends RecyclerView.ViewHolder {
        private TextView memberName, memberAmount;
        public MemberListForGroup(@NonNull View itemView) {
            super(itemView);
            memberAmount = itemView.findViewById(R.id.memberAmount);
            memberName = itemView.findViewById(R.id.memberName);
        }



        public void setDetails(GroupUser groupUser) {
            memberName.setText(groupUser.getFirstName());
            if (groupUser.getAmountDue()!=null)memberAmount.setText(groupUser.getAmountDue().toString());
        }
    }
}
