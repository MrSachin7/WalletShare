package com.sachin_himal.walletshare.ui.all_expenses;

import com.sachin_himal.walletshare.entity.Expenditure;

public class ExpenseWithDateModel {

    public static final int LAYOUT_EXPENSE =1;
    public static final int LAYOUT_DATE =2;


    private final int viewType;
    private Expenditure expenditure;
    private String dateString;

    public ExpenseWithDateModel(int viewType, Expenditure expenditure) {
        this.viewType = viewType;
        this.expenditure = expenditure;
    }

    public ExpenseWithDateModel(int viewType, String dateString
    ) {
        this.viewType = viewType;
        this.dateString = dateString;
    }

    public int getViewType() {

        return viewType;
    }

    public Expenditure getExpenditure() {
        return expenditure;
    }

    public String getDateString() {
        return dateString;
    }
}
