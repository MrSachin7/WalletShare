package com.sachin_himal.walletshare.ui.expenditure;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TimePicker;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.sachin_himal.walletshare.R;
import com.sachin_himal.walletshare.entity.Expenditure;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.Calendar;
import java.util.List;

public class AddExpenditureFragment extends Fragment {

    LinearLayout colorChangingLinearLayout;
    TabLayout tabLayout;

    TextInputLayout dateField, timeField, categoryField, paymentField;
    TextInputEditText dateEditText, timeEditText;
    AppCompatButton saveButton;
    AppCompatEditText expenseAmountField, noteField, payeeField;
    AppCompatAutoCompleteTextView categoryEditable, paymentEditable;
    ProgressBar progressBar;


    private ExpenditureViewModel viewModal;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_expenditure, container, false);
        initializeFields(view);
        setUpTabs();
        viewModal = new ViewModelProvider(this).get(ExpenditureViewModel.class);
        saveButton.setOnClickListener(this::savePressed);
        viewModal.isDone().observe(getViewLifecycleOwner(), this::done);
        viewModal.getError().observe(getViewLifecycleOwner(), this::errorObserver);

        return view;

    }

    private void errorObserver(String s) {
        FancyToast.makeText(getContext(), s, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true);
    }

    private void done(Boolean aBoolean) {
        progressBar.setVisibility(View.INVISIBLE);
        if (aBoolean){
            // Go back to home button
            FancyToast.makeText(getContext(),"Added expense", FancyToast.LENGTH_SHORT,FancyToast.SUCCESS, true).show();
        }
        else{
            FancyToast.makeText(getContext(),"Failed to add expense", FancyToast.LENGTH_SHORT,FancyToast.ERROR, true).show();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        initializeCategoryField();
        initializePaymentField();

    }


    private void expenditureListener(Expenditure expenditure) {
        FancyToast.makeText(getContext(),"Expenditure added of "+ expenditure.getAmount(), FancyToast.LENGTH_SHORT,FancyToast.SUCCESS, true).show();
    }


    private void initializePaymentField() {

        List<String> paymentTypes = viewModal.getAllPaymentCategories();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_dropdown_item, paymentTypes);
        paymentEditable.setAdapter(adapter);
    }

    private void initializeCategoryField() {

        List<String> categories = viewModal.getAllCategories();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_dropdown_item, categories);
        categoryEditable.setAdapter(adapter);

    }

    private void savePressed(View view) {

        String expenseAmount = expenseAmountField.getText().toString().trim();
        if (expenseAmount.equals("")) {
            FancyToast.makeText(getContext(),"Amount must be entered", FancyToast.LENGTH_SHORT,FancyToast.WARNING, true).show();
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

        String expenditureType = "Income";
        if (selectedTabPosition == 0){
            amount = amount * (-1);   // Save expenses as negative
            expenditureType = "Expense";
        }

        boolean isDateValid = validateDate();
        boolean isTimeValid = validateTime();
        boolean isCategoryValid = validateCategory();

        boolean isEveryThingValid = isCategoryValid && isTimeValid && isDateValid;

        if (isEveryThingValid) {

            progressBar.setVisibility(View.VISIBLE);
            Expenditure expenditure = new Expenditure(amount, date, time, category, paymentType, payee, note, expenditureType);
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
        expenseTab.view.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.rounded_corners, getContext().getTheme()));


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
                    colorChangingLinearLayout.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.palette_red, getContext().getTheme()));
                    expenseAmountField.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.palette_red, getContext().getTheme()));
//                    tabLayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.tab_selector_expense, getTheme()));
                } else if (tab.getPosition() == 1) {
                    colorChangingLinearLayout.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.palette_cyan, getContext().getTheme()));
                    expenseAmountField.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.palette_cyan, getContext().getTheme()));
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

    private void initializeFields(View view) {
        colorChangingLinearLayout = view.findViewById(R.id.color_changing_linear_layout);
        tabLayout = view.findViewById(R.id.incomeTypeTab);
        expenseAmountField = view.findViewById(R.id.expense_amount_field);
        dateField = view.findViewById(R.id.date_field);
        timeField = view.findViewById(R.id.time_field);
        noteField = view.findViewById(R.id.note_field);
        payeeField = view.findViewById(R.id.payee_field);
        progressBar = view.findViewById(R.id.add_expense_progress_bar);
        progressBar.setVisibility(View.INVISIBLE);

        dateEditText = view.findViewById(R.id.date_edit_text);
        timeEditText = view.findViewById(R.id.time_edit_text);
        saveButton = view.findViewById(R.id.saveBtn);

        categoryEditable = view.findViewById(R.id.category_edit_text);
        paymentEditable = view.findViewById(R.id.payment_field_edit_text);
        categoryField = view.findViewById(R.id.category_field);
        paymentField = view.findViewById(R.id.payment_field);


        categoryEditable.setShowSoftInputOnFocus(false);
        categoryEditable.setCursorVisible(false);

        paymentEditable.setShowSoftInputOnFocus(false);
        paymentEditable.setCursorVisible(false);


        // Disable edittext on popping the keyboard, we are using pickers

        dateEditText.setShowSoftInputOnFocus(false);
        dateEditText.setCursorVisible(false);
        dateEditText.setOnClickListener(view1 -> {
            showDateDialog();
        });
        dateEditText.setFocusable(false);

        timeEditText.setShowSoftInputOnFocus(false);
        timeEditText.setCursorVisible(false);
        timeEditText.setOnClickListener(view1 -> {
            showTimeDialog();
        });
        timeEditText.setFocusable(false);

    }

    private void showTimeDialog() {
        Calendar calendar = Calendar.getInstance();
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);


        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), this::updateTimeField, hour, minute, false);
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


        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), this::updateDateField, year, month, day);
        datePickerDialog.show();

    }

    private void updateDateField(DatePicker datePicker, int year, int month, int day) {
        String yearText = prettifyIntToString(year);
        String monthText = prettifyIntToString(month+1);
        String dayText = prettifyIntToString(day);

        String text = yearText + "-" + monthText + "-" + dayText;
        dateField.getEditText().setText(text);
    }
}