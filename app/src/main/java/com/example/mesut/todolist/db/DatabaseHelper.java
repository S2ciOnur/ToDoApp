package com.example.mesut.todolist.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.mesut.todolist.core.Category;
import com.example.mesut.todolist.core.Priority;
import com.example.mesut.todolist.core.Todo;

import java.util.ArrayList;

/**
 * Created by Janik on 19.01.2018.
 *
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "PriorityDatabaseHelper";

    public static final String DATABASE_NAME  = "todoapp.db";
    private static final int DATABASE_VERSION = 1;

    //Tabelle für Todo
    public static final String TODO_TABLE_NAME = "todo";

    public static final String ID_TODO_NAME    = "id";
    public static final String ID_TODO_TYPE    = "INTEGER PRIMARY KEY AUTOINCREMENT";

    public static final String TITLE_TODO_NAME = "title";
    public static final String TITLE_TODO_TYPE = "TEXT";

    public static final String DESC_TODO_NAME  = "desc";
    public static final String DESC_TODO_TYPE  = "TEXT";

    public static final String DATE_TODO_NAME  = "date";
    public static final String DATE_TODO_TYPE  = "TEXT";

    public static final String PRIO_TODO_NAME  = "prio_id";
    public static final String PRIO_TODO_TYPE  = "INTEGER";

    //Tabelle für Category
    public static final String CAT_TABLE_NAME = "category";

    public static final String ID_CAT_NAME    = "id";
    public static final String ID_CAT_TYPE    = "INTEGER PRIMARY KEY AUTOINCREMENT";

    public static final String NAME_CAT_NAME  = "name";
    public static final String NAME_CAT_TYPE  = "TEXT";

    //Tabelle für Priority
    public static final String PRIO_TABLE_NAME  = "priority";

    public static final String ID_PRIO_NAME     = "id";
    public static final String ID_PRIO_TYPE     = "INTEGER PRIMARY KEY AUTOINCREMENT";

    public static final String NAME_PRIO_NAME   = "name";
    public static final String NAME_PRIO_TYPE   = "TEXT";

    public static final String WEIGHT_PRIO_NAME = "weight";
    public static final String WEIGHT_PRIO_TYPE = "INTEGER";

    //Tabelle für TodoCat
    public static final String TODO_CAT_TABLE_NAME   = "todocat";

    public static final String TODO_ID_TODOCAT_NAME = "todo_id";
    public static final String TODO_ID_TODOCAT_TYPE = "INTEGER";

    public static final String CAT_ID_TODOCAT_NAME  = "cat_id";
    public static final String CAT_ID_TODOCAT_TYPE  = "INTEGER";

    //
    //TODO: DAS ISCH VERDREHT
    //"INTEGER FOREIGN KEY(id) REFERENCES priority(id)";

    // SQL statement zum Erstellen der Tabelle
    private static final String TODO_TABLE_CREATE =
            "CREATE TABLE " + TODO_TABLE_NAME + "(" +
            ID_TODO_NAME    + " " + ID_TODO_TYPE    + ", " +
            TITLE_TODO_NAME + " " + TITLE_TODO_TYPE + ", " +
            DESC_TODO_NAME  + " " + DESC_TODO_TYPE  + ", " +
            DATE_TODO_NAME  + " " + DATE_TODO_TYPE  + ", " +
            PRIO_TODO_NAME  + " " + PRIO_TODO_TYPE  + ")";

    private static final String CAT_TABLE_CREATE =
            "CREATE TABLE " + CAT_TABLE_NAME + "(" +
            ID_CAT_NAME     + " " + ID_CAT_TYPE    + ", " +
            NAME_CAT_NAME   + " " + NAME_CAT_TYPE  + ")";

    private static final String PRIO_TABLE_CREATE =
            "CREATE TABLE "  + PRIO_TABLE_NAME  + "(" +
            ID_PRIO_NAME     + " " + ID_PRIO_TYPE     + ", " +
            NAME_PRIO_NAME   + " " + NAME_PRIO_TYPE   + ", " +
            WEIGHT_PRIO_NAME + " " + WEIGHT_PRIO_TYPE + ")";

    private static final String TODO_CAT_TABLE_CREATE =
            "CREATE TABLE "      + TODO_CAT_TABLE_NAME   + "(" +
            TODO_ID_TODOCAT_NAME + " " + TODO_ID_TODOCAT_TYPE + ", " +
            CAT_ID_TODOCAT_NAME  + " " + CAT_ID_TODOCAT_TYPE  + ")";

    // Konstruktor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME,null , DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(TODO_TABLE_CREATE);
        } catch(SQLException ex) {
            Log.e(TAG,"Error creating table: todo!", ex);
        }

        try {
            db.execSQL(CAT_TABLE_CREATE);
        } catch(SQLException ex) {
            Log.e(TAG,"Error creating table: category!", ex);
        }

        try {
            db.execSQL(PRIO_TABLE_CREATE);
        } catch(SQLException ex) {
            Log.e(TAG,"Error creating table: priority!", ex);
        }

        try {
            db.execSQL(TODO_CAT_TABLE_CREATE);
        } catch(SQLException ex) {
            Log.e(TAG,"Error creating table: todocat!", ex);
        }
    }

    /**
     * Inserts
     */

    public boolean createTodo(String title, String desc, String date, int prio_id, int [] cat_ids) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TITLE_TODO_NAME, title);
        values.put(DESC_TODO_NAME, desc);
        values.put(DATE_TODO_NAME, date);
        values.put(PRIO_TODO_NAME, prio_id);

        int todo_id =  (int) db.insert(TODO_TABLE_NAME, null, values);

        for (int cat_id : cat_ids) {
            createTodoCat(todo_id, cat_id);
        }

        return true;
    }

    public boolean createCategory(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME_CAT_NAME, name);

        db.insert(CAT_TABLE_NAME, null, values);

        return true;
    }

    public boolean createPriority(String name, int weight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME_PRIO_NAME, name);
        values.put(WEIGHT_PRIO_NAME, weight);

        db.insert(PRIO_TABLE_NAME, null, values);

        return true;
    }

    private boolean createTodoCat(int todo_id, int cat_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TODO_ID_TODOCAT_NAME, todo_id);
        values.put(CAT_ID_TODOCAT_NAME, cat_id);

        db.insert(TODO_CAT_TABLE_NAME, null, values);

        return true;
    }


    /**
     * Gets
     */

    public ArrayList<Todo> getAllTodos() {
        ArrayList<Todo> todos = new ArrayList<Todo>();
        String selectQuery = "SELECT  * FROM " + TODO_TABLE_NAME;

        Log.e(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Todo todo = new Todo();
                todo.setId(c.getInt((c.getColumnIndex(ID_TODO_NAME))));
                todo.setTitle((c.getString(c.getColumnIndex(TITLE_TODO_NAME))));
                todo.setDesc((c.getString(c.getColumnIndex(DESC_TODO_NAME))));
                todo.setDate((c.getString(c.getColumnIndex(DATE_TODO_NAME))));
                todo.setPrio_id(c.getInt(c.getColumnIndex(PRIO_TODO_NAME)));

                // adding to todo list
                todos.add(todo);
            } while (c.moveToNext());
        }

        return todos;
    }

    public ArrayList<Todo>getTodosFromCat(int cat_id){

        ArrayList<Todo> todos = new ArrayList<Todo>();
        String selectQuery = "SELECT DISTINCT n.* " +
                "FROM todo n, category g, todocat ng " +
                "WHERE n.id = ng.todo_id " +
                "AND ng.cat_id = " + cat_id;

        Log.e(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Todo todo = new Todo();
                todo.setId(c.getInt((c.getColumnIndex(ID_TODO_NAME))));
                todo.setTitle((c.getString(c.getColumnIndex(TITLE_TODO_NAME))));
                todo.setDesc((c.getString(c.getColumnIndex(DESC_TODO_NAME))));
                todo.setDate((c.getString(c.getColumnIndex(DATE_TODO_NAME))));
                todo.setPrio_id(c.getInt(c.getColumnIndex(PRIO_TODO_NAME)));

                // adding to todo list
                todos.add(todo);
            } while (c.moveToNext());
        }

        return todos;
    }

    public ArrayList<Category> getAllCategories() {
        ArrayList<Category> categories = new ArrayList<Category>();
        String selectQuery = "SELECT  * FROM " + CAT_TABLE_NAME;

        Log.e(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Category cat = new Category();
                cat.setId(c.getInt((c.getColumnIndex(ID_CAT_NAME))));
                cat.setName((c.getString(c.getColumnIndex(NAME_CAT_NAME))));

                // adding to cat list
                categories.add(cat);
            } while (c.moveToNext());
        }
        return categories;
    }

    public ArrayList<Priority> getAllPriorities() {
        ArrayList<Priority> priorities = new ArrayList<Priority>();
        String selectQuery = "SELECT  * FROM " + PRIO_TABLE_NAME;

        Log.e(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Priority prio = new Priority();
                prio.setId(c.getInt(c.getColumnIndex(ID_PRIO_NAME)));
                prio.setName(c.getString(c.getColumnIndex(NAME_PRIO_NAME)));
                prio.setWeight(c.getInt(c.getColumnIndex(WEIGHT_PRIO_NAME)));

                // adding to cat list
                priorities.add(prio);
            } while (c.moveToNext());
        }
        return priorities;
    }

    /**
     * Updates
     */


    /**
     * Deletes
     */
    public Integer deleteTodo(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TODO_TABLE_NAME,
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    public Integer deleteCat(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(CAT_TABLE_NAME,
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    public Integer deletePrio(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(CAT_TABLE_NAME,
                "id = ? ",
                new String[]{Integer.toString(id)});
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CAT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PRIO_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TODO_CAT_TABLE_NAME);

        // create new tables
        onCreate(db);
    }

    // ab API-Level 11
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVer, int newVer) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CAT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PRIO_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TODO_CAT_TABLE_NAME);

        // create new tables
        onCreate(db);
    }

    /**
     * Debug
     */
    public void insertTestDataForDebug() {

        createPriority("WICHTIG!" , 20);
        createPriority("Normal" , 10);
        createPriority("Kann warten..." , 5);

        createCategory("Haus");
        createCategory("Uni");
        createCategory("Auto");

        int [] cats1 = {1,2};
        createTodo("MPT Lernen", "Treffpunkt in der Fachschaft", "Jeden Tag um 10" , 2, cats1);
        int [] cats2 = {3};
        createTodo("Ölwechsel", "Beim ATU", "14.02.2018 9:00Uhr" , 1, cats2);
        int [] cats3 = {1};
        createTodo("Staubsaugen", "", "" , 3, cats3);

    }
}