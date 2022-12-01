package com.foodresolve.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    private String TEXTFILE = "history.txt";
    private Vector allFile  = new Vector<String[]>(0);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            FileInputStream fis = openFileInput(TEXTFILE);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line;
            String splitString = "==%&";
            int value = 0;
            while ((line = br.readLine()) != null) {
                try {
                    value = Integer.parseInt(line.split(splitString)[0]);
                    allFile.add(line.split(splitString));
                }catch (Exception e){
                    Log.e("Exception", "Value unuseless " + e);
                }
            }

        }catch (Exception e){

            Log.e("Exception", "File read failed: " + e);
        }
    }

    public void onClickOpenProductActivity(View view) {
        try {
            Intent intent_fabOpen = new Intent(MainActivity.this, AddProductActivity.class);
            intent_fabOpen.putExtra("sendFile", allFile);
            startActivity(intent_fabOpen);
        }catch (Exception e){
            Log.e("Exception", "Intent error integrety: " + e);
        }
    }
}