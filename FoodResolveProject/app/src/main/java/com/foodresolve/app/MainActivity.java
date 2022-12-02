package com.foodresolve.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    private String TEXTFILE = "history.txt";
    private Vector<String[]> allFile = new Vector<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            FileInputStream fis = openFileInput(TEXTFILE);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line, splitString = "==%&";
            int value = -1;
            while ((line = br.readLine()) != null) {
                System.out.println("------------------------------------------------");
                String[] sLineSuccess = line.split(splitString);
                try {
                    value = Integer.parseInt(sLineSuccess[0]);
                    allFile.add(value, sLineSuccess);
                    for (int i = 1; i < sLineSuccess.length; i++){
                        System.out.println(value + " " + sLineSuccess[i]);
                    }
                } catch (Exception e) {
                    Log.e("Exception", "Value unuseless " + e);
                }

                System.out.println("------------------------------------------------");

            }
            if(value != -1) {
                TextView test = (TextView) findViewById(R.id.textView_Test);
                test.setText("");
                for (int i = 0; i < allFile.size(); i++) {
                    String addProductInFile = "";
                    for(int j = 1; j < allFile.elementAt(i).length; j++)
                         addProductInFile = addProductInFile + "\n" + allFile.elementAt(i)[j];
                    addProductInFile = addProductInFile + "\n\n\n\n";
                    test.append(addProductInFile);
                }
            }
            /*
            for(int i = 0; i < value; i++){
                for(int j = 0; j < allFile[i]; )
            }
            */
            br.close();
            isr.close();
            fis.close();

        }catch (Exception e){

            Log.e("Exception", "File read failed: " + e);
        }
    }

    public void onClickOpenProductActivity(View view) {
        try {
            Intent intent_fabOpen = new Intent(MainActivity.this, AddProductActivity.class);
           // intent_fabOpen.putExtra("sendFile", allFile);
            startActivity(intent_fabOpen);
        }catch (Exception e){
            Log.e("Exception", "Intent error integrety: " + e);
        }
    }
}