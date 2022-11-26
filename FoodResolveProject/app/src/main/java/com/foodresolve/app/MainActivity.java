package com.foodresolve.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onClickOpenProductActivity(View view) {
        try {
            Intent intent_fabOpen = new Intent(MainActivity.this, AddProductActivity.class);
            startActivity(intent_fabOpen);
        }catch (Exception e){
            Log.e("Exception", "Intent error integrety: " + e);
        }
    }
}