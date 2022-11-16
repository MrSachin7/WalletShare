package com.sachin_himal.walletshare.ui.all_expenses;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sachin_himal.walletshare.R;
import com.sachin_himal.walletshare.entity.Expenditure;
import com.sachin_himal.walletshare.ui.expenditure.ExpenditureViewModal;

import java.util.List;

public class AllExpensesFragment extends Fragment {


    ExpenseAndDateAdapter adapter;
    ExpenditureViewModal viewModal;
    RecyclerView recyclerView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_expense_fragment, container, false);
        initializeAllFields(view);


        viewModal = new ViewModelProvider(this).get(ExpenditureViewModal.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new ExpenseAndDateAdapter();
        recyclerView.setAdapter(adapter);
        viewModal.searchAllExpenditures();
        viewModal.getAllExpenditures().observe(getViewLifecycleOwner(), this::expenditureObserver);

        return view;
    }

    private void expenditureObserver(List<Expenditure> expenditures) {
        adapter.setExpenditures(expenditures);
    }

    private void initializeAllFields(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
    }
}
