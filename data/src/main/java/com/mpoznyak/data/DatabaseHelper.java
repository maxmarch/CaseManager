package com.mpoznyak.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.*;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();
    private static DatabaseHelper mDatabaseHelper = null;
    private Context mContext;

    private static final String DATABASE_NAME = "case_manager_db";
    private static final int VERSION = 2;

    public static class DatabaseContract {

        public static final String TABLE_CASES = "cases";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_CREATION_DATE = "creation_date";
        public static final String COLUMN_TODO_ID = "todo_id";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_ENTRIES_ID = "entries_id";

        public static final String TABLE_TODOS = "todos";

        public static final String COLUMN_TASK = "task";

        public static final String COLUMN_CASE_ID = "case_id";

        public static final String TABLE_ENTRIES = "entries";
        public static final String COLUMN_PATH = "path";


        public static final String CREATE_TABLE_CASES = "CREATE TABLE " + TABLE_CASES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT UNIQUE NOT NULL, "
                + COLUMN_TYPE + " TEXT, "
                + COLUMN_CREATION_DATE + " TEXT "
                + ");";

        public static final String CREATE_TABLE_TODOS = "CREATE TABLE " + TABLE_TODOS + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_CASE_ID + " INTEGER, "
                + COLUMN_TASK + " TEXT, "
                + "FOREIGN KEY (" + COLUMN_CASE_ID + ")" + " REFERENCES " + TABLE_CASES + "(" + COLUMN_ID + ") ON DELETE CASCADE "
                + ");";

        public static final String CREATE_TABLE_ENTRIES = "CREATE TABLE " + TABLE_ENTRIES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_CASE_ID + " INTEGER, "
                + COLUMN_PATH + " TEXT, "
                + "FOREIGN KEY (" + COLUMN_CASE_ID + ")" + " REFERENCES " + TABLE_CASES + "(" + COLUMN_ID + ") ON DELETE CASCADE "
                + ");";
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        mContext = context;
    }

    public static DatabaseHelper newInstance(Context context) {
        if (mDatabaseHelper == null) {
            return new DatabaseHelper(context.getApplicationContext());
        }
        return mDatabaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE_TODOS);
            db.execSQL(CREATE_TABLE_ENTRIES);
            db.execSQL(CREATE_TABLE_CASES);
        } catch (SQLException e) {
            Log.d(TAG, "Thrown exception during creating tables: " + e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }
}
