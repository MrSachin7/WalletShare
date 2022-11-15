package com.sachin_himal.walletshare.ui.all_expenses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sachin_himal.walletshare.R;
import com.sachin_himal.walletshare.entity.Expenditure;

import java.util.ArrayList;
import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {

    private List<Expenditure> allExpenditure = new ArrayList<>();




    public void setAllExpenditure(List<Expenditure> expenditure){
        allExpenditure.clear();
        allExpenditure.addAll(expenditure);
        notifyDataSetChanged();
    }

    public void addExpenditure(Expenditure expenditure){
        allExpenditure.add(expenditure);
        notifyItemInserted(allExpenditure.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.all_expense_list ,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return allExpenditure.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView category, amount, time, note;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.expense_category);
            amount = itemView.findViewById(R.id.expense_amount);
            time = itemView.findViewById(R.id.expense_time);
            note = itemView.findViewById(R.id.expense_note);
        }
    }
}
