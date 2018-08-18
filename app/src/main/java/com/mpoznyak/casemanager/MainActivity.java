package com.mpoznyak.casemanager;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mpoznyak.data.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHelper db = DatabaseHelper.getInstance(this);
        SQLiteDatabase data = db.getWritableDatabase();

        data.execSQL("INSERT INTO entries (case_id, path) VALUES (1, '/Users');");

        data.close();

    }
}
