package com.sachin_himal.walletshare.ui.all_expenses;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sachin_himal.walletshare.R;
import com.sachin_himal.walletshare.entity.Expenditure;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {

    private List<Expenditure> allExpenditure = new ArrayList<>();


    public void setExpenditures(List<Expenditure> expenditure){
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
        View view = inflater.inflate(R.layout.expense_list,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Expenditure expenditure = allExpenditure.get(position);
        holder.amount.setText(String.format("%s", expenditure.getAmount())+ " kr");
        if (expenditure.getExpenditureType().equalsIgnoreCase("Income")) {
            holder.amount.setTextColor(Color.parseColor("#1ECB89"));
        }

        holder.category.setText(expenditure.getCategory());

        LocalDateTime timeOfExpenditure = expenditure.retrieveAsLocalDateTime();
        String month = timeOfExpenditure.getMonth().toString();
        month = month.charAt(0) + month.substring(1).toLowerCase();


        int year = timeOfExpenditure.getYear();
        int dayOfMonth = timeOfExpenditure.getDayOfMonth();
        holder.time.setText(dayOfMonth+ " "+ month+ " "+ year);
        holder.note.setText(expenditure.getNote());


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
