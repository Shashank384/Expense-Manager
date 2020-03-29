package com.example.expensemanager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Calendar;

import com.example.expensemanager.Model.Expense;

public class ListView1 extends AppCompatActivity implements AdapterView.OnItemClickListener,View.OnClickListener {


    //All the Declarations
    private static final String TAG = "ListDataActivity";
    TextView textDate;
    Calendar dateTime = Calendar.getInstance();
    int mYear = (dateTime.get(Calendar.YEAR));
    int mMonth = (dateTime.get(Calendar.MONTH));
    private Button mBtnIncrease, mBtnDecrease;
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMM , yyyy");
    DatabaseHelper dbHelper;
    ListView listView;
    ArrayList<Expense> arrayList;
    MyAdapter myAdapter;


    //Definitions
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);

//        txtempty = (TextView) findViewById(R.id.txtempty);
        textDate = (TextView) findViewById(R.id.textDate);
        mBtnDecrease = (Button) findViewById(R.id.btnLeft);
        mBtnIncrease = (Button) findViewById(R.id.btnRight);
        listView = (ListView) findViewById(R.id.listView);
        //Date Changer
        mYear = (dateTime.get(Calendar.YEAR));
        mMonth = (dateTime.get(Calendar.MONTH));
        String date = sdf.format(dateTime.getTime());
        textDate.setText(date);
        //////
        dbHelper = new DatabaseHelper(this);
        mBtnDecrease.setOnClickListener(this);
        mBtnIncrease.setOnClickListener(this);
        listView.setOnItemClickListener(this);

        showList();

    }


//
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView temp = (TextView) view;
        String Value = listView.getItemAtPosition(position).toString();
        Toast.makeText(getApplicationContext(), Value, Toast.LENGTH_SHORT).show();
        //Intent intent = new Intent(this,List_View.class);
        //startActivity(intent);



    }

    void showList() {
        listView.setVisibility(View.VISIBLE);
        Cursor data = dbHelper.showExpenseData(mMonth + 1,mYear);

        ArrayList<Expense> arrayList = new ArrayList<>();

        if (data.getCount() == 0 || data == null)
        {
            Toast.makeText(ListView1.this, "No Expense for This Month", Toast.LENGTH_SHORT).show();
        }
        else {
            while (data.moveToNext()) {
                Integer rowId = data.getInt(0);
                String rowDate = data.getString(1);
                String rowCategory = data.getString(2);
                Integer rowAmount = data.getInt(3);
                Expense expense = new Expense(rowId, rowDate, rowCategory, rowAmount);

                arrayList.add(expense);
            }
        }
        myAdapter  = new MyAdapter(this,arrayList);
        listView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();

        //Onclick Event to pass the data to EditDataActivity.java File

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Expense expense = (Expense) myAdapter.getItem(position);
                Intent intent = new Intent(ListView1.this, EditDataActivity.class);
                intent.putExtra("ID",expense.getRowId());
                intent.putExtra("DATE",expense.getRowDate());
                intent.putExtra("CATEGORY",expense.getRowCategory());
                intent.putExtra("AMOUNT",expense.getRowAmount());
                startActivity(intent);

            }
        });

    }



    private void decreaseCalender() {

        dateTime.set(mYear, mMonth - 1, 1);
        mYear = (dateTime.get(Calendar.YEAR));
        mMonth = (dateTime.get(Calendar.MONTH));
        String date = sdf.format(dateTime.getTime());
        textDate.setText(date);

    }

    private void increaseCalender() {
        dateTime.set(mYear, mMonth + 1, 1);
        mYear = (dateTime.get(Calendar.YEAR));
        mMonth = (dateTime.get(Calendar.MONTH));
        String date = sdf.format(dateTime.getTime());
        textDate.setText(date);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLeft:
                decreaseCalender();
                showList();
                break;

            case R.id.btnRight:
                increaseCalender();
                showList();
                break;
        }

    }

}
