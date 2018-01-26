package com.example.mesut.todolist.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.mesut.todolist.core.TodoItem;

import java.util.ArrayList;

/**
 * Created by Janik on 19.01.2018.
 */
public class TodoDatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "TodoDatabaseHelper";


    //Name + Version
    public static final String DATABASE_NAME = "todoapp.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TODO_TABLE_NAME = "todo";

    public static final String ID_TODO_NAME = "id";
    public static final String ID_TODO_TYPE = "INTEGER PRIMARY KEY AUTOINCREMENT";

    public static final String TITLE_TODO_NAME = "title";
    public static final String TITLE_TODO_TYPE = "TEXT";

    public static final String DESC_TODO_NAME = "desc";
    public static final String DESC_TODO_TYPE = "TEXT";

    public static final String PRIO_TODO_NAME = "prio";
    public static final String PRIO_TODO_TYPE = "INTEGER";


    //public static final String TEST_FIELD_NAME = "todo_id";
    //public static final String TEST_FIELD_TYPE = "INTEGER FOREIGN KEY(todoid) REFERENCES todo(id)";

    // SQL statement zum Erstellen der Tabelle
    private static final String TABLE_CREATE = "CREATE TABLE " + TODO_TABLE_NAME + "(" +
            ID_TODO_NAME    + " " + ID_TODO_TYPE + ", " +
            TITLE_TODO_NAME + " " + TITLE_TODO_TYPE + ", " +
            DESC_TODO_NAME + " " + DESC_TODO_TYPE + ", " +
            PRIO_TODO_NAME + " " + PRIO_TODO_TYPE + ")";

    // Konstruktor
    public TodoDatabaseHelper(Context context) {
        super(context, DATABASE_NAME,null , DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(TABLE_CREATE);

        } catch(SQLException ex) {
            Log.e(TAG,"Error creating tables", ex);
        }
    }

    public boolean insertPlate(String title, String desc, int prio) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE_TODO_NAME, title);
        contentValues.put(DESC_TODO_NAME, desc);
        contentValues.put(PRIO_TODO_NAME, prio);

        db.insert(TODO_TABLE_NAME, null, contentValues);
        return true;
    }

    public ArrayList<TodoItem> getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<TodoItem> todoItems = new ArrayList<TodoItem>();
        Cursor result = db.rawQuery("select * from " + TODO_TABLE_NAME, null);
        while(result.moveToNext()){
            todoItems.add(new TodoItem(
                    result.getInt(result.getColumnIndex(ID_TODO_NAME)),
                    result.getString(result.getColumnIndex(TITLE_TODO_NAME)),
                    result.getString(result.getColumnIndex(DESC_TODO_NAME)),
                    result.getInt(result.getColumnIndex((PRIO_TODO_NAME)))
            ));

        }
        return todoItems;
    }

    public boolean updatePlates(int id, String title, String desc, int prio) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE_TODO_NAME, title);
        contentValues.put(DESC_TODO_NAME, desc);
        contentValues.put(PRIO_TODO_NAME, prio);

        db.update(TODO_TABLE_NAME, contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deletePlates(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TODO_TABLE_NAME,
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE_NAME);
        onCreate(db);
    }

    // ab API-Level 11
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE_NAME);
        onCreate(db);
    }

    /**
     * Debug
     */
    public void insertTestDataForDebug() {
        insertPlate("Butter kaufen" , "" , 1);
        insertPlate("Ölwechsel" , "bei ATU" , 4);
        insertPlate("Uhr umstellen" , "1h vor" , 3);
        insertPlate("MPT lernen" , "" , 2);
        insertPlate("TODOListe programmieren" , "Quack Quack Quack" , 10);
    }
}