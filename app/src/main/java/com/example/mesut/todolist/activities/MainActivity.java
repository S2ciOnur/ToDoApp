package com.example.mesut.todolist.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mesut.todolist.R;
import com.example.mesut.todolist.core.Todo;
import com.example.mesut.todolist.db.DatabaseHelper;
import com.example.mesut.todolist.util.TodoListAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    TodoListAdapter todoListAdapter = null;
    ListView listView = null;
    DatabaseHelper todoDbh = null;
    ArrayList<Todo> todos = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todoDbh = new DatabaseHelper(this);


        todos = todoDbh.getAllTodos();
        todoListAdapter = new TodoListAdapter(this,R.layout.layout_todolist, todos);

        listView = (ListView) findViewById(R.id.simpleListView);
        listView.setAdapter(todoListAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.todomenu, menu);
        return true;
    }

    /**
     * Für "Action" Buttons
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                startSettingsActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Für alle "normalen" Buttons
     * @param v
     */
    public void onClick(View v){
        switch(v.getId()) {
            case R.id.add_fab :
                todoDbh.insertTestDataForDebug();
                Log.d(TAG, "TODO_DATA IN!!!");

                startItemActivity();
                break;
            default :
                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
        }
    }

    private void startSettingsActivity(){
        Log.d(TAG, "Start new Activity: SettingsActivity");
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void startItemActivity(){
        Log.d(TAG, "Start new Activity: ItemActivity");
        Intent intent = new Intent(this, ItemActivity.class);
        startActivity(intent);
    }
}


