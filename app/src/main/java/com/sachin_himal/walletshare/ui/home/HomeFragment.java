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


    TextView balance_field, lastRecordShowMore;
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
        viewModal.searchThreeLatestExpenses();
        viewModal.searchCurrentBalance();
        viewModal.getCurrentBalance().observe(getViewLifecycleOwner(), this::updateBalanceField);
        viewModal.getThreeExpenditure().observe(getViewLifecycleOwner(), this::updateLastExpensesField);

        initializePieChart();
        return view;
    }

    private void initializePieChart() {
        List<Expenditure> expendituresLastMonth = viewModal.getExpendituresLastMonth();
        HashMap<String, Double> mapCategoryToExpense = new HashMap<>();

        for (Expenditure expenditure : expendituresLastMonth) {
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

//        records.add( new PieEntry(200, "Transport"));
//        records.add( new PieEntry(300, "Food"));
//        records.add( new PieEntry(200, "Fight"));
//        records.add( new PieEntry(400, "Test"));

        PieDataSet dataSet = new PieDataSet(records, "Categories");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextSize(22f);

        PieData pieData = new PieData(dataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(true);
        pieChart.setCenterText("Monthly expenditure");
        pieChart.animate();


    }

    private void showMorePressed(View view) {
        ((MainActivity) getActivity()).changeFragment(R.id.allExpensesFragment);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void updateBalanceField(Balance balance) {

        if (balance !=null){
            String format = String.format("%,.2f",balance.getBalance());
            balance_field.setText(format+ " kr");
        }

    }

    private void updateLastExpensesField(List<Expenditure> expenditures) {
        adapter.setExpenditures(expenditures);
    }

    private void initializeAllFields(View view) {
        balance_field = view.findViewById(R.id.balance_field);
        lastRecordShowMore = view.findViewById(R.id.last_records_show_more);
        lastRecordListView = view.findViewById(R.id.last_records_list_view);
        pieChart = view.findViewById(R.id.pieChart);


    }
}