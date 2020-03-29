package com.example.expensemanager.ui.Income;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.expensemanager.DatabaseHelper;
import com.example.expensemanager.R;
import com.example.expensemanager.ui.Overview.OverviewFragment;

import java.util.Calendar;


public class IncomeFragment extends Fragment {

    Button btnCancel,btnSubmit;
    EditText datePicker,textIncome;
    DatePickerDialog datePickerDialog;
    DatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_income, container, false);

        btnCancel = (Button) rootView.findViewById(R.id.btnCancel);
        btnSubmit = (Button) rootView.findViewById(R.id.btnSubmit);
        datePicker = (EditText) rootView.findViewById(R.id.datePicker);
        textIncome = (EditText) rootView.findViewById(R.id.txtAmount);
        dbHelper = new DatabaseHelper(getContext());
        addData();

        return rootView;
    }


    void addData()
    {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String date = datePicker.getText().toString().trim();
                String amount = textIncome.getText().toString();
                if(date.equals("") || amount.equals(""))
                {
                    Toast.makeText(getActivity(), "Empty Field", Toast.LENGTH_SHORT).show();
                    clearText();
                }
                else {
                    boolean insertdata = dbHelper.addIncomeData(date, amount);

                    if (insertdata == true)

                    {
                        Toast.makeText(getActivity(), "Successfully inserted", Toast.LENGTH_SHORT).show();
                        clearText();
                    } else {
                        Toast.makeText(getActivity(), "Something Wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OverviewFragment overviewFragment = new OverviewFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.mainView,
                        overviewFragment,
                        overviewFragment.getTag()).commit();
            }
        });

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                            {
                                datePicker.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


    }
    public void clearText()
    {
        datePicker.setText("");
        textIncome.setText("");
        datePicker.requestFocus();
    }
}
