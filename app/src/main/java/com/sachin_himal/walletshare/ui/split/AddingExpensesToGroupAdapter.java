package com.sachin_himal.walletshare.ui.split;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.sachin_himal.walletshare.R;
import com.sachin_himal.walletshare.entity.Group;
import com.sachin_himal.walletshare.entity.GroupUser;
import com.sachin_himal.walletshare.entity.User;

import java.lang.invoke.MutableCallSite;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddingExpensesToGroupAdapter extends RecyclerView.Adapter<AddingExpensesToGroupAdapter.GroupExpensesHolder> {

    private List<GroupUser> allFriendList = new ArrayList<>();
    Double[] doubles ;
    private boolean isOnChanged = false;
    Double expenseFinalTotal = 0.00;

    private MutableLiveData<Double> equalExpense = new MutableLiveData<>();


    public AddingExpensesToGroupAdapter() {
    }


    public Double getExpenseFinalTotal() {
        expenseFinalTotal = 0.00;
        System.out.println("---");
        for (int i = 0; i < doubles.length; i++) {
            System.out.println(doubles[i] + "asasasassas");
            expenseFinalTotal+=doubles[i];
        }


        System.out.println(expenseFinalTotal +" is the total ");

        return expenseFinalTotal;
    }


    public void setAllFriendList(List<GroupUser> allFriendList) {
        doubles = new Double[allFriendList.size()];
        this.allFriendList = allFriendList;
        notifyDataSetChanged();

    }

   public List<GroupUser> getUpdatedList(){
        return allFriendList;
   }

    @Override
    public String toString() {

        System.out.println("---");
        return "AddingExpensesToGroupAdapter{" +
                "doubles=" + Arrays.toString(doubles) +
                '}';
    }

    @NonNull
    @Override
    public AddingExpensesToGroupAdapter.GroupExpensesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adding_expenses_to_each_user_group, parent, false);
        return new GroupExpensesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddingExpensesToGroupAdapter.GroupExpensesHolder holder, int position) {
        holder.setDetails(allFriendList.get(position),position);

    }

    @Override
    public int getItemCount() {
        return allFriendList.size();
    }

    public void splitEqually(double parseDouble) {
        parseDouble = parseDouble/allFriendList.size();
        equalExpense.setValue(parseDouble);


    }


    public class GroupExpensesHolder extends RecyclerView.ViewHolder {
        AppCompatTextView memberName;
        AppCompatEditText amountForUser;


        public GroupExpensesHolder(@NonNull View itemView) {
            super(itemView);
            memberName = itemView.findViewById(R.id.memberNameForAddingExpenseToGroup);
            amountForUser = itemView.findViewById(R.id.expense_amount_field_for_groupMember);

            equalExpense.observeForever(aDouble -> {
                if(!isOnChanged){
                    amountForUser.setText(aDouble.toString());
                }
            });
        }

        public void setDetails(GroupUser user,int position) {


            memberName.setText(user.retrieveFullName());
            if (doubles[position] == null){
                doubles[position]=0.00;
            }
            //amountForUser.setText("0.00");
            amountForUser.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    if (doubles[position] == null){
                        doubles[position]=0.00;
                    }
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    isOnChanged = true;
                }

                @Override
                public void afterTextChanged(Editable s) {


                    if (isOnChanged){
                        isOnChanged = false;

                        try {
                            doubles[position] = Double.valueOf(amountForUser.getText().toString().trim());
                            user.setTempAmount(Double.valueOf(s.toString()));
                    //        expenseFinalTotal+=Double.parseDouble(s.toString());
                          //  System.out.println(expenseFinalTotal);

                        }catch (NumberFormatException e){

                            user.setTempAmount(0.00);
                            doubles[position] = 0.00;
                        }
                    }

                }
            });
        }

    }
}
