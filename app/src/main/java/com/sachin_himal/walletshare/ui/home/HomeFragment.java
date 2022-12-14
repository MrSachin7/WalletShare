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

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.sachin_himal.walletshare.R;
import com.sachin_himal.walletshare.entity.Balance;
import com.sachin_himal.walletshare.entity.Expenditure;
import com.sachin_himal.walletshare.ui.MainActivity;
import com.sachin_himal.walletshare.ui.all_expenses.ExpenseAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HomeFragment extends Fragment {


    TextView balance_field, lastRecordShowMore, filterChartShowMore;
    RecyclerView lastRecordListView;
    PieChart pieChart;

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
        filterChartShowMore.setOnClickListener(this::showMoreChartPressed);
        viewModal.getCurrentBalance().observe(getViewLifecycleOwner(), this::updateBalanceField);
        viewModal.getThreeExpenditure().observe(getViewLifecycleOwner(), this::updateLastExpensesField);
        viewModal.getLastMonthExpenseObserver().observe(getViewLifecycleOwner(), this::pieChartObserver);
        return view;
    }

    private void showMoreChartPressed(View view) {
        ((MainActivity) getActivity()).changeFragment(R.id.filterChartFragment);
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

    private void showMorePressed(View view) {
        ((MainActivity) getActivity()).changeFragment(R.id.allExpensesFragment);
    }


    private void updateBalanceField(Balance balance) {

        if (balance !=null){
            // Formatter for money
            String format = String.format("%,.2f",balance.getBalance());
            balance_field.setText(format+ " kr");
        }

    }

    private void updateLastExpensesField(List<Expenditure> expenditures) {
        if (expenditures !=null){
            adapter.setExpenditures(expenditures);
        }
    }

    private void initializeAllFields(View view) {
        balance_field = view.findViewById(R.id.balance_field);
        lastRecordShowMore = view.findViewById(R.id.last_records_show_more);
        lastRecordListView = view.findViewById(R.id.last_records_list_view);
        pieChart = view.findViewById(R.id.pieChart);
        filterChartShowMore = view.findViewById(R.id.filter_chart_show_more);

    }
}