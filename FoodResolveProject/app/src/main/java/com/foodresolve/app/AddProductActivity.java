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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;

public class AddProductActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private Button dateButton; //for two buttons
    private TextView textBuyDate;
    private int index_FileLine;
    private static final String TEXTFILE = "history.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        initDatePicker();
        MainActivity model = (MainActivity) getIntent().getSerializableExtra("sendFile");
        textBuyDate = (TextView) findViewById(R.id.textView_Buy);
        textBuyDate.setText(getTodaysDate());
        dateButton = findViewById(R.id.datePickerButton_Deadline);
        dateButton.setText(getTodaysDate());
        index_FileLine = -1;
        try {
            FileInputStream fis = openFileInput(TEXTFILE);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line;
            String splitString = "==%&";
            while ((line = br.readLine()) != null) {
                System.out.println("------------------------------------------------");
                try {
                    System.out.println(line);
                    index_FileLine = Integer.parseInt(line.split(splitString)[0]);
                }catch (Exception e){
                    Log.e("Exception", "Value unuseless " + e);
                }
                System.out.println("------------------------------------------------");
            }

            System.out.println(index_FileLine);
            isr.close();
            fis.close();
        }catch (Exception e){
            Log.e("Exception", "File write failed: " + e);
        }
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

        if(name.getText().toString().equals(""))
            addText = "Nome non inserito\n";

        if (type.getText().toString().equals(""))
            addText = addText + "Tipo non inserito\n";


        EditText amount = findViewById(R.id.editTextNumber_Amount);
        if(amount.getText().toString().equals(""))
            addText = addText + "Quantità non inserita\n";
        else {
            try {
                float f = Float.parseFloat(amount.getText().toString());
            } catch (Exception e) {
                addText = addText + "La quantità non può essere una stringa\n";
            }
        }

        if(!addText.equals(""))
            text.setText(addText);
        else{
            text.setText("");
            outputEnd = name.getText().toString() + "==%&"
                    + dateDeadline.getText().toString() + "==%&" + type.getText().toString() + "==%&"
                    + descr.getText().toString() + "==%&" + amount.getText().toString()+"Kg" + "==%&";

            writeToFile(outputEnd);
        }

    }

    //This part of code will go to new class "DataManagement"
    //------------------------ DataManagement --------------------------------
    private void writeToFile(String data) {
        data = data + "\n";

        try {
            FileOutputStream fos = openFileOutput(TEXTFILE, Context.MODE_APPEND);
            data = (index_FileLine + 1) + "==%&" + data;
            fos.write(data.getBytes());
            fos.close();
            index_FileLine = index_FileLine + 1;
            System.out.println(index_FileLine);
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e);
        }
    }
    //------------------------- DataManagement -------------------------------
}