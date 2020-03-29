package com.example.expensemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;


public class DatabaseHelper extends SQLiteOpenHelper {

    ListView1 l;
    private static final String TAG = "ListDataActivity";
    private static final int version = 4;
    public static final String Database_Name = "Shashank.db";

    //Income Table
    public static final String Income_Table = "IncomeTable";
    public static final String Income_ID = "IncomeID";
    public static final String Income_Date = "IncomeDate";
    public static final String Income_Amount = "IncomeAmount";

    //Expense Table
    public static final String Expense_Table = "ExpenseTable";
    public static final String Expense_ID = "ExpenseID";
    public static final String Expense_Date = "ExpenseDate";
    public static final String Expense_Category = "ExpenseCategory";
    public static final String Expense_Amount = "ExpenseAmount";

    //Category Table
    public static final String Category_Table = "CategoryTable";
    public static final String Category_Id = "CategoryId";
    public static final String Category_Name = "CategoryName";

    public DatabaseHelper(@Nullable Context context) {
        super(context, Database_Name, null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + Income_Table + " (IncomeID INTEGER, IncomeDate VARCHAR,IncomeAmount INTEGER)");
        db.execSQL("create table " + Expense_Table + " (ExpenseID INTEGER PRIMARY KEY AUTOINCREMENT,ExpenseDate VARCHAR,ExpenseCategory VARCHAR, ExpenseAmount INTEGER)");
        db.execSQL("create table " + Category_Table + " (CategoryId INTEGER PRIMARY KEY AUTOINCREMENT,CategoryName VARCHAR)");
        Log.d(TAG,"Calling : onCreate is called");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion < newVersion) {
            Log.d(TAG, "Calling : onUpgrade is called");
            db.execSQL("DROP TABLE IF EXISTS " + Income_Table);
            db.execSQL("DROP TABLE IF EXISTS " + Expense_Table);
            db.execSQL("DROP TABLE IF EXISTS " + Category_Table);
            onCreate(db);
        }
//        if (newVersion > oldVersion) {
//            db.execSQL("ALTER TABLE " + Expense_Table + " ADD COLUMN ExpenseID INTEGER PRIMARY KEY AUTOINCREMENT");
//        }

    }

    ///////////////Income Database Queries/////////////
    public boolean addIncomeData(String IncomeDate, String IncomeAmount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Income_Date, IncomeDate);
        contentValues.put(Income_Amount, IncomeAmount);

        long result = db.insert(Income_Table, null, contentValues);

        if (result == -1) {
            return false;

        } else
            return true;

    }

    public Cursor showIncome(int mMonth,int mYear) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT IncomeAmount FROM " + Income_Table + " WHERE IncomeDate LIKE '%" + mMonth + "/" + mYear + "%'", null);
        return data;
    }

    /////Expense Database Queries///////////

    public boolean addExpenseData(String Date, String Category, String Amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Expense_Category, Category);
        contentValues.put(Expense_Date, Date);
        contentValues.put(Expense_Amount, Amount);

        long result = db.insert(Expense_Table, null, contentValues);

        if (result == -1) {
            return false;

        } else
            return true;
    }

    //Displays Expense Details at ListView Screen
    public Cursor showExpenseData(int mMonth,int mYear) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT ExpenseID,ExpenseDate,ExpenseCategory,ExpenseAmount FROM " + Expense_Table + " WHERE ExpenseDate LIKE '%" + mMonth + "/" + mYear + "%' ORDER BY ExpenseDate ASC", null);
        return data;
    }



    //Displays Totals Expense of the particular month at Overview Screen
    public Cursor showExpense(int mMonth,int mYear) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT SUM(ExpenseAmount) FROM " + Expense_Table + " WHERE ExpenseDate LIKE '%" + mMonth + "/" + mYear + "%'", null);
        return data;
    }


    //Update the database by taking values from EditDataActivity class
    public void updateName(Integer Id,String Date, String Category,Integer Amount){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Expense_Date,Date);
        contentValues.put(Expense_Category,Category);
        contentValues.put(Expense_Amount,Amount);
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(Expense_Table,contentValues,Expense_ID + "=" + Id,null);
        db.close();
    }

    public void deleteName(int Id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM "+ Expense_Table + " WHERE ExpenseID = '"+Id+"'";
        db.execSQL(query);
        db.close();

        }


}

