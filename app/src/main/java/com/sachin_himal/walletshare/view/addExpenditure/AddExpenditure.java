package com.sachin_himal.walletshare.view.addExpenditure;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.material.tabs.TabLayout;
import com.sachin_himal.walletshare.R;

import java.util.Calendar;

public class AddExpenditure extends AppCompatActivity {

    LinearLayout colorChangingLinearLayout;
    TabLayout tabLayout;
    AppCompatEditText expenseAmountField, dateField, timeField, noteField, payeeField;
    AppCompatSpinner paymentTypeSpinner, categoryField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expenditure);
        initializeFields();
        setUpTabs();
    }

    private void setUpTabs() {
        TabLayout.Tab expenseTab = tabLayout.newTab();
        expenseTab.setText(R.string.expense);
        expenseTab.view.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.rounded_corners, getTheme()));


        TabLayout.Tab incomeTab = tabLayout.newTab();
        incomeTab.setText(R.string.income);


//        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.addTab(expenseTab, 0, true);
        tabLayout.addTab(incomeTab, 1);

//        tabLayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.tab_selector_expense, getTheme()));


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    colorChangingLinearLayout.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.palette_red, getTheme()));
                    expenseAmountField.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.palette_red, getTheme()));
//                    tabLayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.tab_selector_expense, getTheme()));
                } else if (tab.getPosition() == 1) {
                    colorChangingLinearLayout.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.palette_cyan, getTheme()));
                    expenseAmountField.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.palette_cyan, getTheme()));
//                    tabLayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.tab_selector_income, getTheme()));


                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    private void initializeFields() {
        colorChangingLinearLayout = findViewById(R.id.color_changing_linear_layout);
        tabLayout = findViewById(R.id.incomeTypeTab);
        expenseAmountField = findViewById(R.id.expense_amount_field);
        dateField = findViewById(R.id.date_field);
        timeField = findViewById(R.id.time_field);
        noteField = findViewById(R.id.note_field);
        payeeField = findViewById(R.id.payee_field);
        paymentTypeSpinner = findViewById(R.id.payment_type_spinner);
        categoryField = findViewById(R.id.category_spinner);





        // Disable edittext on popping the keyboard, we are using pickers
        dateField.setShowSoftInputOnFocus(false);
        dateField.setOnClickListener(view -> {
            showDateDialog();
        });

        timeField.setShowSoftInputOnFocus(false);

        timeField.setOnClickListener(view -> {
            showTimeDialog();
        });

    }

    private void showTimeDialog() {
        Calendar calendar = Calendar.getInstance();
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);


        TimePickerDialog timePickerDialog = new TimePickerDialog(this, this::timeChangeListener, hour, minute, false);
        timePickerDialog.show();
    }

    private void timeChangeListener(TimePicker timePicker, int hour, int minute) {
        String text = hour + ":" + minute;
        timeField.setText(text);
    }

    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this::dateChangeListener, year, month, day);
        datePickerDialog.show();

    }

    private void dateChangeListener(DatePicker datePicker, int year, int month, int day) {
        String text = day + "-" + month + "-" + year;
        dateField.setText(text);
        Toast.makeText(this, dateField.getText().toString(), Toast.LENGTH_SHORT).show();
    }
}