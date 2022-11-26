package com.foodresolve.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class AddProductActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private Button dateButton; //for two buttons
    private TextView textBuyDate;
    private static final String TEXTFILE = "history.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        initDatePicker();
        textBuyDate = (TextView) findViewById(R.id.textView_Buy);
        textBuyDate.setText(getTodaysDate());
        dateButton = findViewById(R.id.datePickerButton_Deadline);
        dateButton.setText(getTodaysDate());
    }

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year)
    {
        return day + "-" + month  + "-" + year;
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }

    public void onClickAddProduct(View view) {
        TextView text = findViewById(R.id.textView_Test);
        String addText = "";
        String outputEnd;

        EditText name = findViewById(R.id.editText_Name);
        EditText type = findViewById(R.id.editText_Type);
        EditText descr = findViewById(R.id.editTextMultiline_Description);
        Button dateDeadline = findViewById(R.id.datePickerButton_Deadline);

        if(name.getText().toString().equals("")) {
            addText = "nome non inserito\n";
        }

        if (type.getText().toString().equals(""))
            addText = addText + "tipo non inserito\n";


        EditText amount = findViewById(R.id.editTextNumber_Amount);
        if(amount.getText().toString().equals(""))
            addText = addText + "quantità non inserita\n";
        try {
            float f = Float.parseFloat(amount.getText().toString());
        }catch (Exception e){
            addText = addText + "la quantità non può essere una stringa\n";
        }
        if(!addText.equals(""))
            text.setText(addText);
        else{
            text.setText("");
            outputEnd = textBuyDate.getText().toString() + "**" + dateDeadline.getText().toString() + "**"
                    + name.getText().toString() + "**" + type.getText().toString() + "**"
                    + descr.getText().toString() + "**" + amount.getText().toString()+"Kg" + "==%&";

            writeToFile(outputEnd);
        }

    }
    private void writeToFile(String data) {
        data = data + "\n";
        try {
            FileOutputStream fos = openFileOutput(TEXTFILE, Context.MODE_PRIVATE);
            fos.write(data.getBytes());
            fos.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e);
        }
    }
}