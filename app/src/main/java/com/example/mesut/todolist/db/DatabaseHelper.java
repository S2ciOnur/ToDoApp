package com.example.mesut.todolist.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.util.TypedValue;

import com.example.mesut.todolist.core.Category;
import com.example.mesut.todolist.core.Priority;
import com.example.mesut.todolist.core.Textsize;
import com.example.mesut.todolist.core.Todo;

import java.util.ArrayList;

/**
 * Created by Janik on 19.01.2018.
 *
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";

    private static final String DATABASE_NAME  = "todoapp.db";
    private static final int DATABASE_VERSION = 5;

    //Tabelle für Todo
    private static final String TODO_TABLE_NAME = "todo";

    private static final String ID_TODO_NAME    = "id";
    private static final String ID_TODO_TYPE    = "INTEGER PRIMARY KEY AUTOINCREMENT";

    private static final String TITLE_TODO_NAME = "title";
    private static final String TITLE_TODO_TYPE = "TEXT";

    private static final String DESC_TODO_NAME  = "desc";
    private static final String DESC_TODO_TYPE  = "TEXT";

    private static final String DATE_TODO_NAME  = "date";
    private static final String DATE_TODO_TYPE  = "TEXT";

    private static final String PRIO_TODO_NAME  = "prio_id";
    private static final String PRIO_TODO_TYPE  = "INTEGER";

    //Tabelle für Category
    private static final String CAT_TABLE_NAME = "category";

    private static final String ID_CAT_NAME    = "id";
    private static final String ID_CAT_TYPE    = "INTEGER PRIMARY KEY AUTOINCREMENT";

    private static final String NAME_CAT_NAME  = "name";
    private static final String NAME_CAT_TYPE  = "TEXT";

    //Tabelle für Priority
    private static final String PRIO_TABLE_NAME  = "priority";

    private static final String ID_PRIO_NAME     = "id";
    private static final String ID_PRIO_TYPE     = "INTEGER PRIMARY KEY AUTOINCREMENT";

    private static final String NAME_PRIO_NAME   = "name";
    private static final String NAME_PRIO_TYPE   = "TEXT";

    private static final String WEIGHT_PRIO_NAME = "weight";
    private static final String WEIGHT_PRIO_TYPE = "INTEGER";

    //Tabelle für TodoCat
    private static final String TODO_CAT_TABLE_NAME   = "todocat";

    private static final String TODO_ID_TODOCAT_NAME = "todo_id";
    private static final String TODO_ID_TODOCAT_TYPE = "INTEGER";

    private static final String CAT_ID_TODOCAT_NAME  = "cat_id";
    private static final String CAT_ID_TODOCAT_TYPE  = "INTEGER";

    //Tabelle für TextSize
    private static final String TEXTSIZE_TABLE_NAME   = "textsize";

    private static final String ID_TEXTSIZE_NAME = "id";
    private static final String ID_TEXTSIZE_TYPE = "INTEGER";

    private static final String DIGIT_TEXTSIZE_NAME = "digit";
    private static final String DIGIT_TEXTSIZE_TYPE = "FLOAT";

    private static final String UNIT_TEXTSIZE_NAME  = "unit";
    private static final String UNIT_TEXTSIZE_TYPE  = "INTEGER";

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

    private static final String TEXTSIZE_TABLE_CREATE =
            "CREATE TABLE " + TEXTSIZE_TABLE_NAME + "(" +
            ID_TEXTSIZE_NAME + " " + ID_TEXTSIZE_TYPE + ", " +
            DIGIT_TEXTSIZE_NAME + " " + DIGIT_TEXTSIZE_TYPE + ", " +
            UNIT_TEXTSIZE_NAME + " " + UNIT_TEXTSIZE_TYPE + ")";

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

        try {
            db.execSQL(TEXTSIZE_TABLE_CREATE);
        } catch(SQLException ex) {
            Log.e(TAG,"Error creating table: textsize!", ex);
        }
    }

    /**
     * Inserts
     */

    public boolean createTodo(String title, String desc, String date, int prio_id, int [] cat_ids) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(TITLE_TODO_NAME, title);
            values.put(DESC_TODO_NAME, desc);
            values.put(DATE_TODO_NAME, date);
            values.put(PRIO_TODO_NAME, prio_id);

            int todo_id = (int) db.insert(TODO_TABLE_NAME, null, values);

            for (int cat_id : cat_ids) {
                createTodoCat(todo_id, cat_id);
            }

            return true;
        }catch (SQLException ex){
            Log.e(TAG, "Couldn't create new TODO with title=" + title + ".\n" + ex);
            return false;
        }
    }

    public boolean createCategory(String name) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(NAME_CAT_NAME, name);

            db.insert(CAT_TABLE_NAME, null, values);

            return true;
        }catch (SQLException ex){
            Log.e(TAG, "Couldn't create new TODO with name=" + name + ".\n" + ex);
            return false;
        }
    }

    public boolean createPriority(String name, int weight) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(NAME_PRIO_NAME, name);
            values.put(WEIGHT_PRIO_NAME, weight);

            db.insert(PRIO_TABLE_NAME, null, values);

            return true;
        }catch (SQLException ex){
            Log.e(TAG, "Couldn't create new PRIO with name=" + name + ".\n" + ex);
            return false;
        }
    }

    private boolean createTodoCat(int todo_id, int cat_id){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(TODO_ID_TODOCAT_NAME, todo_id);
            values.put(CAT_ID_TODOCAT_NAME, cat_id);

            db.insert(TODO_CAT_TABLE_NAME, null, values);

            return true;
        }catch (SQLException ex){
            Log.e(TAG, "Couldn't create new TODO_CAT with FATAL ERROR!\n" + ex);
            return false;
        }
    }


    /**
     * Gets
     */

    public ArrayList<Todo> getAllTodos() {
        try {
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
                    todo.setCats(getCatsFromTodo(todo.getId()));

                    todos.add(todo);
                } while (c.moveToNext());
            }

            return todos;
        }catch (SQLException ex){
            Log.e(TAG, "Couldn't get ALL TODOS.\n" + ex);
            return null;
        }
    }

    public ArrayList<Todo> getTodosFromCat(int cat_id){
        try {

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
        }catch (SQLException ex){
            Log.e(TAG, "Couldn't update TODO from Cat with id = " + cat_id + ".\n" + ex);
            return null;
        }
    }

    public ArrayList<Category> getCatsFromTodo(int todo_id){
        try {

            ArrayList<Category> cats = new ArrayList<Category>();
            String selectQuery = "SELECT DISTINCT n.* " +
                    "FROM category n, todo g, todocat ng " +
                    "WHERE n.id = ng.cat_id " +
                    "AND ng.todo_id = " + todo_id;

            Log.e(TAG, selectQuery);

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor c = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (c.moveToFirst()) {
                do {
                    Category cat = new Category();
                    cat.setId(c.getInt((c.getColumnIndex(ID_CAT_NAME))));
                    cat.setName(c.getString(c.getColumnIndex(NAME_CAT_NAME)));

                    cats.add(cat);
                } while (c.moveToNext());
            }

            Log.e(TAG,"ID: " + todo_id + " " +  cats.toString());
            return cats;
        }catch (SQLException ex){
            Log.e(TAG, "Couldn't get CATS from todo with id = " + todo_id + ".\n" + ex);
            return null;
        }
    }

    public ArrayList<Category> getAllCategories() {
        try {
            ArrayList<Category> categories = new ArrayList<Category>();
            String selectQuery = "SELECT  * FROM " + CAT_TABLE_NAME;

            Log.e(TAG, selectQuery);

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor c = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (c.moveToFirst()) {
                do {
                    Category cat = new Category();
                    cat.setId(c.getInt(c.getColumnIndex(ID_CAT_NAME)));
                    cat.setName(c.getString(c.getColumnIndex(NAME_CAT_NAME)));

                    // adding to cat list
                    categories.add(cat);
                } while (c.moveToNext());
            }
            return categories;
        }catch (SQLException ex){
            Log.e(TAG, "Couldn't get ALL CATS.\n" + ex);
            return null;
        }
    }

    public ArrayList<Priority> getAllPriorities() {
        try {
            ArrayList<Priority> priorities = new ArrayList<Priority>();
            String selectQuery = "SELECT  *" +
                    " FROM " + PRIO_TABLE_NAME +
                    " ORDER BY " + WEIGHT_PRIO_NAME + " DESC";

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
        }catch (SQLException ex){
            Log.e(TAG, "Couldn't get ALL PRIOS.\n" + ex);
            return null;
        }
    }

    public String getPrioNameById(int id){
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            String selectQuery = "SELECT " + NAME_PRIO_NAME +
                    "FROM " + PRIO_TABLE_NAME
                    + " WHERE " + ID_PRIO_NAME + " = " + id;

            Log.e(TAG, selectQuery);

            Cursor c = db.rawQuery(selectQuery, null);

            if (c != null)
                c.moveToFirst();

            String s = c.getString(c.getColumnIndex(NAME_PRIO_NAME));

            return s;
        }catch (SQLException ex){
            Log.e(TAG, "Couldn't get PRIO with id = " + id + ".\n" + ex);
            return null;
        }
    }

    public Textsize getTextsize(){
        Textsize textsize = new Textsize();

        try {
            SQLiteDatabase db = this.getReadableDatabase();

            String selectQuery = "SELECT *" +
                    " FROM " + TEXTSIZE_TABLE_NAME +
                    " WHERE " + ID_TEXTSIZE_NAME + " = " + 0;

            Log.e(TAG, selectQuery);

            Cursor c = db.rawQuery(selectQuery, null);

            if (c != null) {
                c.moveToFirst();

                if (c.getCount() != 0) {
                    textsize.setDigit(c.getFloat(c.getColumnIndex(DIGIT_TEXTSIZE_NAME)));
                    textsize.setUnit(c.getInt(c.getColumnIndex(UNIT_TEXTSIZE_NAME)));
                } else {
                    textsize.setDigit(12);
                    textsize.setUnit(TypedValue.COMPLEX_UNIT_SP);
                }
            } else {
                textsize.setDigit(12);
                textsize.setUnit(TypedValue.COMPLEX_UNIT_SP);
            }

            return textsize;
        }catch (SQLException ex){
            Log.e(TAG, "Couldn't get textsize.\n" + ex);
            return null;
        }
    }

    /**
     * Updates
     */

    public boolean updateTodo(int id, String title, String desc, String date, int prio_id, int [] cat_ids) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(TITLE_TODO_NAME, title);
            values.put(DESC_TODO_NAME, desc);
            values.put(DATE_TODO_NAME, date);
            values.put(PRIO_TODO_NAME, prio_id);

            // updating row
            db.update(TODO_TABLE_NAME, values, ID_TODO_NAME + " = ?", new String[]{String.valueOf(id)});

            deleteTodoCatByTodoId(id);

            for (int cat_id : cat_ids) {
                createTodoCat(id, cat_id);
            }
            return true;
        }catch (SQLException ex){
            Log.e(TAG, "Couldn't update TODO with id = " + id + ".\n" + ex);
            return false;
        }
    }

    public boolean updatePrio(int id, String name, int weight) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(NAME_PRIO_NAME, name);
            values.put(WEIGHT_PRIO_NAME, weight);

            // updating row
            db.update(PRIO_TABLE_NAME, values, ID_PRIO_NAME + " = ?", new String[]{String.valueOf(id)});

            return true;
        }catch (SQLException ex){
            Log.e(TAG, "Couldn't update PRIO with id = " + id + ".\n" + ex);
            return false;
        }
    }

    public boolean updateCat(int id, String name) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(NAME_CAT_NAME, name);

            // updating row
            db.update(CAT_TABLE_NAME, values, ID_CAT_NAME + " = ?", new String[]{String.valueOf(id)});

            return true;
        }catch (SQLException ex){
            Log.e(TAG, "Couldn't update CAT with id = " + id + ".\n" + ex);
            return false;
        }
    }

    public boolean updateTextsize(float digit, int unit){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            db.delete(TEXTSIZE_TABLE_NAME, "id = ? ", new String[]{Integer.toString(0)});

            values.put(ID_TEXTSIZE_NAME, 0);
            values.put(DIGIT_TEXTSIZE_NAME, digit);
            values.put(UNIT_TEXTSIZE_NAME, unit);

            db.insert(TEXTSIZE_TABLE_NAME, null, values);
            return true;
        }catch (SQLException ex){
            Log.e(TAG, "Couldn't update Textsize!");
            return false;
        }
    }

    /**
     * Deletes
     */
    public boolean deleteTodo(Integer id) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TODO_TABLE_NAME, "id = ? ", new String[]{Integer.toString(id)});
            db.delete(TODO_CAT_TABLE_NAME, "todo_id = ? ", new String[]{Integer.toString(id)});
            return true;
        }catch (SQLException ex){
            Log.e(TAG, "Couldn't delete TODO with id = " + id.toString() + ".\n" + ex);
            return false;
        }
    }

    public boolean deleteCat(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(CAT_TABLE_NAME, "id = ? ", new String[]{Integer.toString(id)});
            db.delete(TODO_CAT_TABLE_NAME, "cat_id = ? ", new String[]{Integer.toString(id)});
            return true;
        }catch (SQLException ex){
            Log.e(TAG, "Couldn't delete CAT with id = " + id.toString());
            return false;
        }
    }

    public boolean deletePrio(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(PRIO_TABLE_NAME, "id = ? ", new String[]{Integer.toString(id)});
            return true;
        }catch (SQLException ex){
            Log.e(TAG, "Couldn't delete PRIO with id = " + id.toString());
            return false;
        }
    }

    private boolean deleteTodoCatByTodoId(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(TODO_CAT_TABLE_NAME, "todo_id = ? ", new String[]{Integer.toString(id)});
            return true;
        }catch (SQLException ex){
            Log.e(TAG, "Couldn't delete TODOCAT with todo_id = " + id.toString());
            return false;
        }
    }

    private boolean deleteTodoCatByCatId(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(TODO_CAT_TABLE_NAME, "cat_id = ? ", new String[]{Integer.toString(id)});
            return true;
        }catch (SQLException ex){
            Log.e(TAG, "Couldn't delete TODOCAT with cat_id = " + id.toString());
            return false;
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CAT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PRIO_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TODO_CAT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TEXTSIZE_TABLE_NAME);

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
        db.execSQL("DROP TABLE IF EXISTS " + TEXTSIZE_TABLE_NAME);

        // create new tables
        onCreate(db);
    }

    /**
     * Counts
     */


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