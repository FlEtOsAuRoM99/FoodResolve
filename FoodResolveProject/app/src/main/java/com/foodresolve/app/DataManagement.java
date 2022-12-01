package com.foodresolve.app;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class DataManagement {
    DataManagement(){

        FileOutputStream fos = openFileOutput(TEXTFILE, Context.MODE_PRIVATE);

        FileInputStream fis = openFileInput(TEXTFILE);
        fis.close();
    }
}
