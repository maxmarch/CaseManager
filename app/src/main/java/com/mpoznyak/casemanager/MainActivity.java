package com.mpoznyak.casemanager;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mpoznyak.data.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHelper db = DatabaseHelper.newInstance(this);
        SQLiteDatabase data = db.getWritableDatabase();
        data.execSQL("INSERT INTO todos (task) VALUES ('ajsnd')");

        data.close();

    }
}
