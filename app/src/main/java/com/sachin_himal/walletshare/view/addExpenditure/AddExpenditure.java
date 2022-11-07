package com.sachin_himal.walletshare.view.addExpenditure;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.sachin_himal.walletshare.R;

import java.util.Calendar;
import java.util.List;

public class AddExpenditure extends AppCompatActivity {

    LinearLayout colorChangingLinearLayout;
    TabLayout tabLayout;

    TextInputLayout dateField, timeField, categoryField;
    TextInputEditText dateEditText, timeEditText;
    AppCompatButton saveButton;
    AppCompatEditText expenseAmountField, noteField, payeeField;
    AppCompatSpinner paymentTypeSpinner;
    AppCompatAutoCompleteTextView categoryEditable;


    private ExpenditureViewModal viewModal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expenditure);
        initializeFields();
        setUpTabs();
        viewModal = new ViewModelProvider(this).get(ExpenditureViewModal.class);
        saveButton.setOnClickListener(this::savePressed);



    }

    @Override
    protected void onResume() {
        super.onResume();
        initializeCategoryField();

    }

    private void initializeCategoryField() {

        List<String> categories = viewModal.getAllCategories();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_dropdown_item, categories);
        categoryEditable.setAdapter(adapter);

    }

    private void savePressed(View view) {

        String date = dateEditText.getText().toString().trim();
        String time = timeEditText.getText().toString().trim();
        String category = categoryEditable.getText().toString().trim();
        String note = noteField.getText().toString().trim();
        String payee = payeeField.getText().toString().trim();
        boolean isDateValid=validateDate();
        boolean isTimeValid = validateTime();
        boolean isCategoryValid = validateCategory();

        boolean isEveryThingValid = isCategoryValid && isTimeValid && isDateValid;

        if (isEveryThingValid){
            // Todo ask view modal to add the new expense
        }


    }

    private boolean validateDate() {
        String date = dateEditText.getText().toString().trim();
        if (date.equals("")){
            dateField.setError("Date is required");
            return false;

        }
        else{
            dateField.setError(null);
            dateField.setErrorEnabled(false);
            return true;

        }


    }
    private boolean validateTime() {
        String time = timeEditText.getText().toString().trim();
        if (time.equals("")){
            timeField.setError("Date is required");
            return false;

        }

        else{
            timeField.setError(null);
            timeField.setErrorEnabled(false);
            return true;

        }


    }    private boolean validateCategory() {
        String category = categoryEditable.getText().toString().trim();
        if (category.equals("")){
            categoryField.setError("Date is required");
            return false;

        }
        else{
            categoryField.setError(null);
            categoryField.setErrorEnabled(false);
            return true;

        }


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
        dateEditText = findViewById(R.id.date_edit_text);
        timeEditText = findViewById(R.id.time_edit_text);
        saveButton = findViewById(R.id.saveBtn);
        categoryEditable = findViewById(R.id.category_edit_text);
        categoryField = findViewById(R.id.category_field);


        categoryEditable.setShowSoftInputOnFocus(false);
        categoryEditable.setCursorVisible(false);


        // Disable edittext on popping the keyboard, we are using pickers

        dateEditText.setShowSoftInputOnFocus(false);
        dateEditText.setCursorVisible(false);
        dateEditText.setOnClickListener(view -> {
            showDateDialog();
        });
        dateEditText.setFocusable(false);

        timeEditText.setShowSoftInputOnFocus(false);
        timeEditText.setCursorVisible(false);
        timeEditText.setOnClickListener(view -> {
            showTimeDialog();
        });
        timeEditText.setFocusable(false);

    }

    private void showTimeDialog() {
        Calendar calendar = Calendar.getInstance();
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);


        TimePickerDialog timePickerDialog = new TimePickerDialog(this, this::updateTimeField, hour, minute, false);
        timePickerDialog.show();
    }

    private void updateTimeField(TimePicker timePicker, int hour, int minute) {
        String text = hour + ":" + minute;
        timeField.getEditText().setText(text);
    }

    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this::updateDateField, year, month, day);
        datePickerDialog.show();

    }

    private void updateDateField(DatePicker datePicker, int year, int month, int day) {
        String text = day + "-" + month + "-" + year;
        dateField.getEditText().setText(text);
    }
}