package com.sachin_himal.walletshare.ui.filter_chart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.tabs.TabLayout;
import com.sachin_himal.walletshare.R;
import com.sachin_himal.walletshare.entity.Expenditure;
import com.sachin_himal.walletshare.ui.expenditure.ExpenditureViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilterChartFragment extends Fragment {

    PieChart pieChart;

    TabLayout tabLayout;


    ExpenditureViewModel viewModal;

    private int selectedTab = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart_filter, container, false);

        viewModal = new ViewModelProvider(this).get(ExpenditureViewModel.class);

        initializeFields(view);
        initializeTabLayout();
        viewModal.getAllExpenditures().observe(getViewLifecycleOwner(), this::pieChartObserver);
        return view;

    }

    private void pieChartObserver(List<Expenditure> expenditures) {
        if (expenditures != null){
            updatePieChart(expenditures);
        }

    }



    private void updatePieChart(List<Expenditure> expenditures) {
        HashMap<String, Double> mapCategoryToExpense = new HashMap<>();

        for (Expenditure expenditure : expenditures) {
            String category = expenditure.getCategory();
            double amount = expenditure.getAmount();
            if (amount <0) amount = amount * (-1); // The expenses have their amount saved as negative in database.

            if (mapCategoryToExpense.get(category) != null) {
                double tempAmount = mapCategoryToExpense.get(category);
                amount = amount + tempAmount;
            }
            mapCategoryToExpense.put(category, amount);
        }


        List<PieEntry> records = new ArrayList<>();
        for (Map.Entry<String, Double> entry : mapCategoryToExpense.entrySet()) {
            records.add(new PieEntry(entry.getValue().floatValue(), entry.getKey()));
        }


        PieDataSet dataSet = new PieDataSet(records, "Categories");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextSize(22f);

        PieData pieData = new PieData(dataSet);
        pieChart.setData(pieData);

        pieChart.animate();
        pieChart.invalidate();

    }


    private void initializeTabLayout() {
        TabLayout.Tab all = tabLayout.newTab();
        all.setText("All");

        TabLayout.Tab sevenDay = tabLayout.newTab();
        sevenDay.setText("7D");

        TabLayout.Tab thirtyDay = tabLayout.newTab();
        thirtyDay.setText("30D");

        TabLayout.Tab threeMonths = tabLayout.newTab();
        threeMonths.setText("3M");

        TabLayout.Tab sixMonths = tabLayout.newTab();
        sixMonths.setText("6M");

        TabLayout.Tab oneYear = tabLayout.newTab();
        oneYear.setText("1Y");

        tabLayout.addTab(all,0,true);
        tabLayout.addTab(sevenDay, 1);
        tabLayout.addTab(thirtyDay, 2);
        tabLayout.addTab(threeMonths, 3);
        tabLayout.addTab(sixMonths, 4);
        tabLayout.addTab(oneYear, 5);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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

    private void startFilter() {
        List<Expenditure> expenditures = null;

        switch (selectedTab){
            case 0:
                expenditures = viewModal.getAllExpenditures().getValue();
                break;
            case 1:
                expenditures = viewModal.getExpenditureLastWeek();
                break;
            case 2:
                expenditures = viewModal.getExpenditureLastMonth();
                break;
            case 3:
                expenditures = viewModal.getExpenditureLastThreeMonths();
                break;
            case 4:
                expenditures = viewModal.getExpenditureLastSixMonths();
                break;

            case 5:
                expenditures = viewModal.getExpenditureLastOneYear();
                break;

        }
        updatePieChart(expenditures);

    }

    private void initializeFields(View view) {
        pieChart = view.findViewById(R.id.pieChart);
        tabLayout = view.findViewById(R.id.filter_tab_layout);
    }
}
