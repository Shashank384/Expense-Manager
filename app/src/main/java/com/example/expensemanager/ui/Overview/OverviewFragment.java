package com.example.expensemanager.ui.Overview;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.expensemanager.DatabaseHelper;
import com.example.expensemanager.R;
import java.util.Calendar;

public class OverviewFragment extends Fragment implements View.OnClickListener {

    DatabaseHelper dbHelper;
    TextView textInc2,textExp2,textBal2,textDate;
    public Integer a,c,temp;
    public int b;

    java.text.SimpleDateFormat sdf = new  java.text.SimpleDateFormat("MMM , yyyy");
    Calendar dateTime = Calendar.getInstance();
    int mYear = (dateTime.get(Calendar.YEAR));
    int mMonth = (dateTime.get(Calendar.MONTH));
    private Button mBtnIncrease, mBtnDecrease;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_overview, container, false);

        textInc2 = (TextView)rootView.findViewById(R.id.textInc2);
        textExp2 = (TextView)rootView.findViewById(R.id.textExp2);
        textBal2 = (TextView)rootView.findViewById(R.id.textBal2);
        textDate = (TextView)rootView.findViewById(R.id.textDate);

        mBtnDecrease = (Button) rootView.findViewById(R.id.btnLeft);
        mBtnIncrease = (Button) rootView.findViewById(R.id.btnRight);

        mBtnDecrease.setOnClickListener(this);
        mBtnIncrease.setOnClickListener(this);
        dbHelper = new DatabaseHelper(getContext());

        mYear = (dateTime.get(Calendar.YEAR));
        mMonth = (dateTime.get(Calendar.MONTH));
        String date = sdf.format(dateTime.getTime());
        textDate.setText(date);

        try {
            Database1();
            Database2();

        }
        catch (NullPointerException e)
        {
            Log.d("Null Value","No Expense");
        }

        return rootView;
    }



    private void decreaseCalender(){

        dateTime.set(mYear, mMonth-1,1);
        mYear = (dateTime.get(Calendar.YEAR));
        mMonth = (dateTime.get(Calendar.MONTH));
        String date = sdf.format(dateTime.getTime());
        textDate.setText(date);

    }
    private void increaseCalender(){
        dateTime.set(mYear, mMonth+1,1);
        mYear = (dateTime.get(Calendar.YEAR));
        mMonth = (dateTime.get(Calendar.MONTH));
        String date = sdf.format(dateTime.getTime());
        textDate.setText(date);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLeft:
                decreaseCalender();
                Database1();
                Database2();
                break;

            case R.id.btnRight:
                increaseCalender();
                Database1();
                Database2();
                break;
        }

    }


    //Function for Income
    public void Database1()
    {
        Cursor income = dbHelper.showIncome(mMonth+1,mYear);

        int temp1=income.getCount();
        Log.d("AMAN3", "check cursor : "+mYear);
        if (temp1<= 0) {
            Log.d("AMAN4", "Count is 0  : ");
            textInc2.setText("0");
            textBal2.setText("0");
            Toast.makeText(getContext(),"Enter Income ",Toast.LENGTH_SHORT).show();
        }
        else {
            StringBuffer buffer = new StringBuffer();
            while (income.moveToNext()) {
                buffer.append(income.getString(0));
                Log.d("AMAN4", "Buffer value  : "+ buffer.toString());
                textInc2.setText(buffer.toString());
                a = Integer.parseInt(buffer.toString());
            }
        }
    }


    //Function for Expense
    public void Database2() {
        Cursor expense = dbHelper.showExpense(mMonth+1,mYear);
        StringBuffer buffer = new StringBuffer();

        if(expense!=null){
        expense.moveToNext();
            buffer.append(expense.getString(0));
//            Log.d("SHASHANK1", "check cursor : "+ expense.getString(0));
        }

        if (expense.isNull(0))
        {
//            Log.d("SHASHANK2", "when Value is null : "+ buffer.toString());
                Toast.makeText(getContext(), "No Expense", Toast.LENGTH_SHORT).show();
                textExp2.setText("0");
                textBal2.setText(textInc2.getText() + "");
        }
        else
            {
//                    Log.d("SHASHANK3", "when Value is not null : "+ buffer.toString());

                    b=Integer.parseInt(buffer.toString());
                    textExp2.setText(b + "");
                    a = Integer.parseInt(textInc2.getText().toString());
                    c = a - b;
                    textBal2.setText(c + "");
            }
        expense.close();
        }

    }




