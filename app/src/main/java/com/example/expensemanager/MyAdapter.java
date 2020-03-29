package com.example.expensemanager;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.expensemanager.Model.Expense;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    Context context;
    ArrayList<Expense> arrayList;
    public MyAdapter(Context context,ArrayList<Expense> arrayList)
    {
        this.context=context;
        this.arrayList=arrayList;

    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_row,null);
            TextView rowDate = (TextView)convertView.findViewById(R.id.rowDate);
            TextView rowCategory = (TextView)convertView.findViewById(R.id.rowCategory);
            TextView rowAmount = (TextView)convertView.findViewById(R.id.rowAmount);

            Expense expense =  arrayList.get(position);
            rowDate.setText(expense.getRowDate());
            rowCategory.setText(expense.getRowCategory());
            rowAmount.setText(String.valueOf(expense.getRowAmount()));

        if (position % 2 == 1) {
            convertView.setBackgroundColor(Color.parseColor("#fff5e6"));
        } else {
            convertView.setBackgroundColor(Color.WHITE);
        }

        return convertView;
    }
}
