package com.sachin_himal.walletshare.ui.all_expenses;

import static com.sachin_himal.walletshare.ui.all_expenses.ExpenseWithDateModel.LAYOUT_DATE;
import static com.sachin_himal.walletshare.ui.all_expenses.ExpenseWithDateModel.LAYOUT_EXPENSE;

import android.graphics.Color;
import android.util.Log;
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

public class ExpenseAndDateAdapter extends RecyclerView.Adapter {


    List<ExpenseWithDateModel> list;
    List<Expenditure> expenditures;
    List<Expenditure> expendituresToShow;


    public ExpenseAndDateAdapter() {
        this.list = new ArrayList<>();
        this.expenditures = new ArrayList<>();
        expendituresToShow = new ArrayList<>();

    }

    public void setExpenditures(List<Expenditure> expenditures) {
        if (expenditures == null) {
            return;
        }
        this.expenditures = expenditures;
//        expendituresToShow.addAll(expenditures);
        filterList("");
    }

    public void addExpenditure(Expenditure expenditure) {
        String dateString = list.get(list.size() - 1).getDateString();
        if (!expenditure.getDateString().equals(dateString)) {
            list.add(new ExpenseWithDateModel(LAYOUT_DATE, expenditure.getDateString()));
        }
        list.add(new ExpenseWithDateModel(LAYOUT_EXPENSE, expenditure));
        notifyItemChanged(list.size() - 1);
    }


    private void updateList() {

        list.clear();
        notifyDataSetChanged();

        if (expendituresToShow.isEmpty()) {

            return;
        }
        Log.d("Update list","Not empty");

        String dateString = expendituresToShow.get(0).getDateString();
        list.add(new ExpenseWithDateModel(LAYOUT_DATE, dateString));

        for (int i = 0; i < expendituresToShow.size(); i++) {
            Expenditure expenditure = expendituresToShow.get(i);
            if (!expenditure.getDateString().equals(dateString)) {
                dateString = expenditure.getDateString();
                list.add(new ExpenseWithDateModel(LAYOUT_DATE, dateString));
            }
            list.add(new ExpenseWithDateModel(LAYOUT_EXPENSE, expenditure));
        }

        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        switch (list.get(position).getViewType()) {
            case 1:
                return LAYOUT_EXPENSE;
            case 2:
                return LAYOUT_DATE;

            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case LAYOUT_EXPENSE:
                View expenseLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_list, parent, false);
                return new ExpenseViewHolder(expenseLayout);

            case LAYOUT_DATE:
                View dateLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.date_item, parent, false);
                return new DateViewHolder(dateLayout);

            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (list.get(position).getViewType()) {
            case LAYOUT_EXPENSE:
                Expenditure expenditure = list.get(position).getExpenditure();
                ((ExpenseViewHolder) holder).setView(expenditure);

                return;

            case LAYOUT_DATE:
                String dateString = list.get(position).getDateString();
                ((DateViewHolder) holder).setView(dateString);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void filterList( String filterCategory) {
        expendituresToShow.clear();
        expendituresToShow.addAll(expenditures);
        if ( filterCategory !=null && !filterCategory.equals("") && !filterCategory.equals("All")) {
            expendituresToShow.removeIf(expenditure -> !expenditure.getCategory().equals(filterCategory));
        }

        updateList();

    }


    static class ExpenseViewHolder extends RecyclerView.ViewHolder {

        private final TextView category, amount, time, note;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.expense_category);
            amount = itemView.findViewById(R.id.expense_amount);
            time = itemView.findViewById(R.id.expense_time);
            note = itemView.findViewById(R.id.expense_note);
        }

        private void setView(Expenditure expenditure) {
            amount.setText(String.format("%s", expenditure.getAmount()) + " kr");
            if (expenditure.getAmount() > 0) {
                amount.setTextColor(Color.parseColor("#1ECB89"));
            }
            category.setText(expenditure.getCategory());

            time.setText(expenditure.getTimeString());
            note.setText(expenditure.getNote());
        }
    }

    static class DateViewHolder extends RecyclerView.ViewHolder {

        private final TextView date_field;

        public DateViewHolder(@NonNull View itemView) {
            super(itemView);
            date_field = itemView.findViewById(R.id.date_display_field);

        }

        private void setView(String date) {
            date_field.setText(date);
        }
    }
}
