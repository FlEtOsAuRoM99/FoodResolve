package com.foodresolve.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onClickOpenProductActivity(View view) {
        Intent intent_fabOpenPruductActivity = new Intent(MainActivity.this,AddProductActivity.class);
        startActivity(intent_fabOpenPruductActivity);
    }
}