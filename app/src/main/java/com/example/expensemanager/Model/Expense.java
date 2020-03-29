package com.example.expensemanager.Model;

import java.io.Serializable;

public class Expense implements Serializable {

    String rowDate,rowCategory;
    int rowAmount,rowId;

    public Expense(int rowId, String rowDate, String rowCategory, int rowAmount)
    {
        this.rowDate = rowDate;
        this.rowCategory = rowCategory;
        this.rowAmount = rowAmount;
        this.rowId = rowId;
    }
    public Expense(){}

    public String getRowDate() {
        return rowDate;
    }

    public void setRowDate(String rowDate) {
        this.rowDate = rowDate;
    }

    public String getRowCategory() {
        return rowCategory;
    }

    public void setRowCategory(String rowCategory) {
        this.rowCategory = rowCategory;
    }

    public int getRowAmount() {
        return rowAmount;
    }

    public void setRowAmount(int rowAmount) {
        this.rowAmount = rowAmount;
    }

    public int getRowId() { return rowId; }

    public void setRowId(int rowId) { this.rowId = rowId;   }
}
