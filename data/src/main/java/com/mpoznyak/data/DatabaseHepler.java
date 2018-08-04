package com.mpoznyak.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHepler extends SQLiteOpenHelper {

    private static final String TAG = DatabaseHepler.class.getSimpleName();
    private static DatabaseHepler mDatabaseHelper = null;
    private Context mContext;

    private static final String DATABASE_NAME = "case_manager_db";
    private static final int VERSION = 2;

    private static final String TABLE_CASES = "cases";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_CREATION_DATE = "creation_date";
    private static final String COLUMN_TODO_ID = "todo_id";
    private static final String COLUMN_TYPE_ID = "type_id";

    private static final String TABLE_TODOS = "todos";

    private static final String TABLE_TASKS = "tasks";
    private static final String COLUMN_TASK = "task";

    private static final String TABLE_TYPES = "types";
    private static final String COLUMN_CASE_ID = "case_id";

    private static final String TABLE_ENTRIES = "entries";
    private static final String COLUMN_PATH = "path";

    private static final String CREATE_TABLE_CASES = "CREATE TABLE " + TABLE_CASES + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TYPE_ID + " INTEGER, "
            + COLUMN_NAME + " TEXT, "
            + COLUMN_CREATION_DATE + " DATETIME, "
            + COLUMN_TODO_ID + " INTEGER, "
            + "FOREIGN KEY (" + COLUMN_TYPE_ID + ")" + " REFERENCES " + TABLE_TYPES + "(" + COLUMN_ID + "),"
            + "FOREIGN KEY (" + COLUMN_TODO_ID + ")" + " REFERENCES " + TABLE_TODOS + "(" + COLUMN_ID + ")"
            + ");";

    private static final String CREATE_TABLE_TODOS = "CREATE TABLE " + TABLE_TODOS + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT "+ ");";


    private static final String CREATE_TABLE_TASKS = "CREATE TABLE " + TABLE_TASKS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TODO_ID + " INTEGER, "
            + COLUMN_TASK + " TEXT, "
            + "FOREIGN KEY (" + COLUMN_TODO_ID + ")" + " REFERENCES " + TABLE_TODOS + "(" + COLUMN_ID + ")"
            + ");";

    private static final String CREATE_TABLE_ENTRIES = "CREATE TABLE " + TABLE_ENTRIES + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_CASE_ID + " INTEGER, "
            + COLUMN_PATH + " TEXT, "
            + "FOREIGN KEY (" + COLUMN_CASE_ID + ")" + " REFERENCES " + TABLE_CASES + "(" + COLUMN_ID + ")"
            + ");";

    private static final String CREATE_TABLE_TYPES = "CREATE TABLE " + TABLE_TYPES + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT " + ");";

    public DatabaseHepler(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        mContext = context;
    }

    public static DatabaseHepler newInstance(Context context) {
        if (mDatabaseHelper == null) {
            return new DatabaseHepler(context.getApplicationContext());
        }
        return mDatabaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE_TODOS);
            db.execSQL(CREATE_TABLE_CASES);
            db.execSQL(CREATE_TABLE_ENTRIES);
            db.execSQL(CREATE_TABLE_TYPES);
            db.execSQL(CREATE_TABLE_TASKS);
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
