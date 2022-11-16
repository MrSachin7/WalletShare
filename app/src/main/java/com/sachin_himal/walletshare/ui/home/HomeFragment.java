package com.sachin_himal.walletshare.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sachin_himal.walletshare.R;
import com.sachin_himal.walletshare.entity.Balance;
import com.sachin_himal.walletshare.entity.Expenditure;
import com.sachin_himal.walletshare.ui.MainActivity;
import com.sachin_himal.walletshare.ui.all_expenses.ExpenseAdapter;

import java.util.List;


public class HomeFragment extends Fragment {


    TextView balance_field, lastRecordShowMore;
    RecyclerView lastRecordListView;

    ExpenseAdapter adapter;
    HomeViewModal viewModal;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initializeAllFields(view);
        viewModal = new ViewModelProvider(this).get(HomeViewModal.class);
        lastRecordListView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ExpenseAdapter();
        lastRecordListView.setAdapter(adapter);
        lastRecordShowMore.setOnClickListener(this::showMorePressed);

        return view;
    }

    private void showMorePressed(View view) {
        ((MainActivity) getActivity()).changeFragment(R.id.allExpensesFragment);

    }

    @Override
    public void onResume() {
        super.onResume();
        viewModal.searchThreeLatestExpenses();
        viewModal.searchCurrentBalance();
        viewModal.getCurrentBalance().observe(getViewLifecycleOwner(), this::updateBalanceField);
        viewModal.getThreeExpenditure().observe(getViewLifecycleOwner(), this::updateLastExpensesField);
    }

    private void updateBalanceField(Balance balance) {
        String format = String.format("%,.2f",balance.getBalance());

        balance_field.setText(format+ " kr");
    }

    private void updateLastExpensesField(List<Expenditure> expenditures) {
        adapter.setExpenditures(expenditures);
    }

    private void initializeAllFields(View view) {
        balance_field = view.findViewById(R.id.balance_field);
        lastRecordShowMore = view.findViewById(R.id.last_records_show_more);
        lastRecordListView = view.findViewById(R.id.last_records_list_view);


    }
}