package com.example.expensemanager.ui.Expense;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.expensemanager.DatabaseHelper;
import com.example.expensemanager.R;
import com.example.expensemanager.ui.Overview.OverviewFragment;

import java.util.Calendar;


public class FragmentExpense extends Fragment implements View.OnClickListener {

    DatabaseHelper dbHelper;
    Button btnCancel, btnSubmit;
    EditText datePicker, txtAmount;
    DatePickerDialog datePickerDialog;
    Spinner spinner1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_expense, container, false);

        dbHelper = new DatabaseHelper(getContext());
        btnSubmit = (Button) rootView.findViewById(R.id.btnSubmit);
        btnCancel = (Button) rootView.findViewById(R.id.btnCancel);
        datePicker = (EditText) rootView.findViewById(R.id.datePicker);
        txtAmount = (EditText) rootView.findViewById(R.id.txtAmount);
        spinner1 = (Spinner)rootView.findViewById(R.id.spinner1);

        btnCancel.setOnClickListener(this);
        datePicker.setOnClickListener(this);
        txtAmount.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getStringArray(spinnerlist));

        return rootView;


    }





    public void onClick (View v){
        switch (v.getId()) {

            case R.id.btnCancel:
                OverviewFragment overviewFragment = new OverviewFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.mainView,
                        overviewFragment,
                        overviewFragment.getTag()).commit();
                break;

            case R.id.btnSubmit:
                // Database Implementations...
                String date = datePicker.getText().toString().trim();
                String amount = txtAmount.getText().toString();
                String Category="Food";

                if(date.equals("") || amount.equals("") || Category.equals(""))
                {
                    Toast.makeText(getActivity(), "Empty Field", Toast.LENGTH_SHORT).show();
                }
                else {
                    boolean insertdata = dbHelper.addExpenseData(date, Category, amount);

                    if (insertdata == true)

                    {
                        Toast.makeText(getActivity(), "Successfully inserted", Toast.LENGTH_SHORT).show();
                        clearText();
                    } else {
                        Toast.makeText(getActivity(), "Something Wrong", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case R.id.datePicker:
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {


                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                datePicker.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
        }
    }
    public void clearText()
    {
        datePicker.setText("");
        txtAmount.setText("");
        datePicker.requestFocus();
    }






}

