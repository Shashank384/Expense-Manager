package com.example.expensemanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditDataActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "ListDataActivity";
    EditText editable_item1,editable_item2,editable_item3;
    private Button btnSave, btnDelete;
    DatabaseHelper dbHelper;
    public String selectedDate,selectedCategory;
    public int selectedAmount,selectedId;
    ListView1 listView1;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_data_layout);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.x = -100;
        params.height = 850;
        params.width = 1100;
        params.y = -20;

        this.getWindow().setAttributes(params);

        btnSave = (Button)findViewById(R.id.btnSave);
        btnDelete = (Button)findViewById(R.id.btnDelete);
        editable_item1 = (EditText)findViewById(R.id.editable_item1);
        editable_item2 = (EditText)findViewById(R.id.editable_item2);
        editable_item3 = (EditText)findViewById(R.id.editable_item3);
        dbHelper = new DatabaseHelper(this);

        Intent receiveIntent = getIntent();

        if(receiveIntent != null) {
            selectedId = receiveIntent.getIntExtra("ID", -1);
            selectedDate = receiveIntent.getStringExtra("DATE");
            selectedCategory = receiveIntent.getStringExtra("CATEGORY");
            selectedAmount = receiveIntent.getIntExtra("AMOUNT", -1);

        }
        editable_item1.setText(selectedDate);
        editable_item2.setText(selectedCategory);
        editable_item3.setText(selectedAmount + "");

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int setAmount = Integer.parseInt(editable_item3.getText().toString());
                if (true){
                    dbHelper.updateName(selectedId, editable_item1.getText().toString(), editable_item2.getText().toString(), setAmount);
                    finish();

//                    Intent intent = new Intent(getApplicationContext(),ListView1.class);
//                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Not updated", Toast.LENGTH_SHORT).show();
                }
            }
        });

    btnDelete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            int setAmount = Integer.parseInt(editable_item3.getText().toString());
            if (true){
                dbHelper.deleteName(selectedId);
                Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(),ListView1.class);
                startActivity(intent);
                finish();

            }
            else
            {
                Toast.makeText(getApplicationContext(), "Not Deleted", Toast.LENGTH_SHORT).show();
            }
        }
    });
    }


    @Override
    public void onClick(View v) {

    }
}
