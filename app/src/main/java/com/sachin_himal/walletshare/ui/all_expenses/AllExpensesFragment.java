package com.sachin_himal.walletshare.ui.all_expenses;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.sachin_himal.walletshare.R;
import com.sachin_himal.walletshare.entity.Expenditure;
import com.sachin_himal.walletshare.ui.expenditure.ExpenditureViewModel;

import java.util.List;

public class AllExpensesFragment extends Fragment {

    AppCompatAutoCompleteTextView categoryEditable;
    TabLayout filterTabLayout;
    ExpenseAndDateAdapter adapter;
    ExpenditureViewModel viewModal;
    RecyclerView recyclerView;


    private int selectedTab = 0;
    private String filterCategory = "";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_expense_fragment, container, false);
        initializeAllFields(view);
        setUpFilterTabs();

        viewModal = new ViewModelProvider(this).get(ExpenditureViewModel.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new ExpenseAndDateAdapter();

        recyclerView.setAdapter(adapter);
        viewModal.getAllExpenditures().observe(getViewLifecycleOwner(), this::expenditureObserver);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initializeCategoryField();
    }


    private void setUpFilterTabs() {

        TabLayout.Tab all = filterTabLayout.newTab();
        all.setText("All");

        TabLayout.Tab sevenDay = filterTabLayout.newTab();
        sevenDay.setText("7D");

        TabLayout.Tab thirtyDay = filterTabLayout.newTab();
        thirtyDay.setText("30D");

        TabLayout.Tab threeMonths = filterTabLayout.newTab();
        threeMonths.setText("3M");

        TabLayout.Tab sixMonths = filterTabLayout.newTab();
        sixMonths.setText("6M");

        TabLayout.Tab oneYear = filterTabLayout.newTab();
        oneYear.setText("1Y");

        filterTabLayout.addTab(all,0,true);
        filterTabLayout.addTab(sevenDay, 1);
        filterTabLayout.addTab(thirtyDay, 2);
        filterTabLayout.addTab(threeMonths, 3);
        filterTabLayout.addTab(sixMonths, 4);
        filterTabLayout.addTab(oneYear, 5);

        filterTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectedTab = tab.getPosition();
                startFilter();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    private void expenditureObserver(List<Expenditure> expenditures) {
        adapter.setExpenditures(expenditures);
    }

    private void initializeAllFields(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        categoryEditable = view.findViewById(R.id.category_edit_text);
        categoryEditable.setShowSoftInputOnFocus(false);
        categoryEditable.setCursorVisible(false);
        categoryEditable.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterCategory = charSequence.toString();
                startFilter();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        filterTabLayout = view.findViewById(R.id.filter_tab_layout);
    }

    private void startFilter() {

        List<Expenditure> expenditureToShow =null;
        switch (selectedTab) {

            case 0:
                expenditureToShow = viewModal.getAllExpenditures().getValue();
                break;

            case 1:
                expenditureToShow = viewModal.getExpenditureLastWeek();
                break;

            case 2:
                expenditureToShow = viewModal.getExpenditureLastMonth();
                break;

            case 3:
                expenditureToShow = viewModal.getExpenditureLastThreeMonths();
                break;

            case 4:
                expenditureToShow = viewModal.getExpenditureLastSixMonths();

                break;

            case 5:
                expenditureToShow = viewModal.getExpenditureLastOneYear();
                break;
        }

        if (adapter != null){
            adapter.setExpenditures(expenditureToShow);
            adapter.filterList(filterCategory);
        }

    }


    private void initializeCategoryField() {

        List<String> categories = viewModal.getAllCategories();
        categories.add(0, "All");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_dropdown_item, categories);
        categoryEditable.setAdapter(adapter);

    }
}
