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
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";


    //Name + Version
    public static final String DATABASE_NAME = "todoapp.db";
    private static final int DATABASE_VERSION = 1;

    //todo Tabelle:
    public static final String TABLE_NAME = "todo";

    public static final String TITLE_FIELD_NAME = "title";
    public static final String TITLE_FIELD_TYPE = "TEXT";

    public static final String DESC_FIELD_NAME = "desc";
    public static final String DESC_FIELD_TYPE = "TEXT";

    public static final String PRIO_FIELD_NAME = "prio";
    public static final String PRIO_FIELD_TYPE = "INTEGER";

    // SQL statement zum Erstellen der Tabelle
    private static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + "(" +
            TITLE_FIELD_NAME + " " + TITLE_FIELD_TYPE + ", " +
            DESC_FIELD_NAME  + " " + DESC_FIELD_TYPE  + ", " +
            PRIO_FIELD_NAME  + " " + PRIO_FIELD_TYPE  + ")";

    // Konstruktor
    public DatabaseHelper(Context context) {
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

    public boolean insertPlate(String title, String desc, int prio ) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE_FIELD_NAME, title);
        contentValues.put(DESC_FIELD_NAME, desc);
        contentValues.put(PRIO_FIELD_NAME, prio);

        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public ArrayList<TodoItem> getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<TodoItem> todoItems = new ArrayList<TodoItem>();
        Cursor result = db.rawQuery("select * from " + TABLE_NAME , null);
        while(result.moveToNext()){
            todoItems.add(new TodoItem(result.getString(result.getColumnIndex(TITLE_FIELD_NAME)), result.getString(result.getColumnIndex(DESC_FIELD_NAME)), 0 ));
        }
        return todoItems;
    }

    public boolean updatePlates(int id, String title, String desc, int prio) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE_FIELD_NAME, title);
        contentValues.put(DESC_FIELD_NAME, desc);
        contentValues.put(PRIO_FIELD_NAME, prio);

        db.update(TABLE_NAME, contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deletePlates(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // alte Tabellen auslesen und löschen, neue Tabellen anlegen
        // alten Inhalt in neue Tabellen einfügen
    }

    // ab API-Level 11
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVer, int newVer) {
        // alte Tabellen auslesen und löschen, neue Tabellen anlegen
        // alten Inhalt in neue Tabellen einfügen
    }

    /**
     * Debug
     */
    public void insertTestDataForDebug() {
        insertPlate("Butter kaufen" , "" , 1);
        insertPlate("Ölwechsel" , "bei ATU" , 1);
        insertPlate("Uhr umstellen" , "1h vor" , 1);
        insertPlate("MPT lernen" , "" , 1);
        insertPlate("TODOListe programmieren" , "Quack Quack Quack" , 1);
    }
}