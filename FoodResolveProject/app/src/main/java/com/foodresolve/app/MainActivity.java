package com.foodresolve.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private File file;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDatePicker();
        file = new File("res/", "history.txt");
        try {
            if(fileExists()){
                System.out.print("File esistente");
            }else {
                System.out.print("File non esistente, creato");
            }
        }catch (Exception e){
            Log.e("Exception", "File write failed: " + e.toString());
        }
        dateButton = findViewById(R.id.datePickerButton_Buy);
        dateButton.setText(getTodaysDate());
        dateButton = findViewById(R.id.datePickerButton_Deadline);
        dateButton.setText(getTodaysDate());
    }
    private boolean fileExists() throws IOException {
        if (file.createNewFile())
            return false;
        return true;
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
        return day + " " + getMonthFormat(month)  + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "Gen";
        if(month == 2)
            return "Feb";
        if(month == 3)
            return "Mar";
        if(month == 4)
            return "Apr";
        if(month == 5)
            return "Mag";
        if(month == 6)
            return "Giu";
        if(month == 7)
            return "Lug";
        if(month == 8)
            return "Ago";
        if(month == 9)
            return "Set";
        if(month == 10)
            return "Ott";
        if(month == 11)
            return "Nov";
        if(month == 12)
            return "Dic";

        //default should never happen
        return "Gen";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }

    public void onClickTest(View view) {
        TextView text = findViewById(R.id.textView_Test);
        String addText = "";
        String outputEnd;

        EditText name = findViewById(R.id.editText_Name);
        EditText type = findViewById(R.id.editText_Type);
        EditText descr = findViewById(R.id.editTextMultiline_Description);
        Button dateBuy = findViewById(R.id.datePickerButton_Buy);
        Button dateDeadline = findViewById(R.id.datePickerButton_Deadline);

        if(name.getText().toString().equals("")) {
            addText = "nome non inserito\n";
        }

        if (type.getText().toString().equals(""))
            addText = addText + "tipo non inserito\n";


        EditText amount = findViewById(R.id.editTextNumber_Amount);
        if(amount.getText().toString().equals(""))
            addText = addText + "quantità non inserita\n";

        if(!addText.equals(""))
            text.setText(addText);
        else{
            outputEnd = dateBuy.getText().toString() + "**" + dateDeadline.getText().toString() + "**"
                    + name.getText().toString() + "**" + type.getText().toString() + "**"
                    + descr.getText().toString() + "**" + amount.getText().toString()+"Kg" + "==%&";

            writeToFile(outputEnd);
        }

    }
    private void writeToFile(String data) {
        try {
            FileOutputStream outputStreamWriter = new FileOutputStream(file);
            outputStreamWriter.write(data.getBytes());
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e);
        }
    }
}