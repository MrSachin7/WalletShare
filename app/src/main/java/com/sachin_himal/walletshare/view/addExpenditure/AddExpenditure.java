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
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.sachin_himal.walletshare.R;
import com.sachin_himal.walletshare.entity.Expenditure;

import java.util.Calendar;
import java.util.List;

public class AddExpenditure extends AppCompatActivity {

    LinearLayout colorChangingLinearLayout;
    TabLayout tabLayout;

    TextInputLayout dateField, timeField, categoryField, paymentField;
    TextInputEditText dateEditText, timeEditText;
    AppCompatButton saveButton;
    AppCompatEditText expenseAmountField, noteField, payeeField;
    AppCompatAutoCompleteTextView categoryEditable, paymentEditable;


    private ExpenditureViewModal viewModal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expenditure);
        initializeFields();
        setUpTabs();
        viewModal = new ViewModelProvider(this).get(ExpenditureViewModal.class);
        saveButton.setOnClickListener(this::savePressed);

//        viewModal.getExpenditure().observe(this, this::expenditureListener);


    }


    private void expenditureListener(Expenditure expenditure) {
        Toast.makeText(this, "Added an expense of" + expenditure.getAmount(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initializeCategoryField();
        initializePaymentField();

    }

    private void initializePaymentField() {

        List<String> paymentTypes = viewModal.getAllPaymentCategories();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_dropdown_item, paymentTypes);
        paymentEditable.setAdapter(adapter);
    }

    private void initializeCategoryField() {

        List<String> categories = viewModal.getAllCategories();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_dropdown_item, categories);
        categoryEditable.setAdapter(adapter);

    }

    private void savePressed(View view) {

        String expenseAmount = expenseAmountField.getText().toString().trim();
        if (expenseAmount.equals("")) {
            Toast.makeText(this, "Please enter amount", Toast.LENGTH_SHORT).show();
            return;
        }


        String date = dateEditText.getText().toString().trim();
        String time = timeEditText.getText().toString().trim();
        String category = categoryEditable.getText().toString().trim();
        String paymentType = paymentEditable.getText().toString().trim();
        String note = noteField.getText().toString().trim();
        String payee = payeeField.getText().toString().trim();


        double amount = Double.parseDouble(expenseAmount);
        int selectedTabPosition = tabLayout.getSelectedTabPosition();
        if (selectedTabPosition == 0) amount = amount * (-1);   // Save expenses as negative

        boolean isDateValid = validateDate();
        boolean isTimeValid = validateTime();
        boolean isCategoryValid = validateCategory();

        boolean isEveryThingValid = isCategoryValid && isTimeValid && isDateValid;

        if (isEveryThingValid) {

            Toast.makeText(this, "Everything valid", Toast.LENGTH_SHORT).show();
            Expenditure expenditure = new Expenditure(amount, date, time, category, paymentType, payee, note);
            viewModal.addExpenditure(expenditure);
            //   viewModal.addExpenditure()
        }


    }

    private boolean validateDate() {
        String date = dateEditText.getText().toString().trim();
        if (date.equals("")){
            dateField.setError(getString(R.string.date_required));
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
            timeField.setError(getString(R.string.time_required));
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

        dateEditText = findViewById(R.id.date_edit_text);
        timeEditText = findViewById(R.id.time_edit_text);
        saveButton = findViewById(R.id.saveBtn);

        categoryEditable = findViewById(R.id.category_edit_text);
        paymentEditable = findViewById(R.id.payment_field_edit_text);
        categoryField = findViewById(R.id.category_field);
        paymentField = findViewById(R.id.payment_field);


        categoryEditable.setShowSoftInputOnFocus(false);
        categoryEditable.setCursorVisible(false);

        paymentEditable.setShowSoftInputOnFocus(false);
        paymentEditable.setCursorVisible(false);


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

        String hourText = prettifyIntToString(hour);
        String minuteText = prettifyIntToString(minute);


        String text = hourText + ":" + minuteText + ":00";
        timeField.getEditText().setText(text);
    }

    private String prettifyIntToString(int integerToBeConverted) {

        if (integerToBeConverted >= 10) return String.valueOf(integerToBeConverted);

        return "0" + integerToBeConverted;
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
        String yearText = prettifyIntToString(year);
        String monthText = prettifyIntToString(month);
        String dayText = prettifyIntToString(day);

        String text = yearText + "-" + monthText + "-" + dayText;
        dateField.getEditText().setText(text);
    }
}