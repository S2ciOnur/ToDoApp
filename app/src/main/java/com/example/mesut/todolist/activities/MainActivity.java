package com.example.mesut.todolist.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mesut.todolist.R;
import com.example.mesut.todolist.core.Category;
import com.example.mesut.todolist.core.Priority;
import com.example.mesut.todolist.core.Textsize;
import com.example.mesut.todolist.core.Todo;
import com.example.mesut.todolist.db.DatabaseHelper;
import com.example.mesut.todolist.util.TodoListAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private TodoListAdapter todoListAdapter = null;
    private ListView listView = null;
    private DatabaseHelper dbh = null;
    private ArrayList<Todo> todos = null;
    private ArrayList<Priority> prios = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbh = new DatabaseHelper(this);

        todos = dbh.getAllTodos();
        prios = dbh.getAllPriorities();
        if(prios.isEmpty()){
            dbh.createPriority("Wichtig" , 100);
            prios = dbh.getAllPriorities();
        }

        todoListAdapter = new TodoListAdapter(this, R.layout.layout_todolist, todos, prios);

        listView = (ListView) findViewById(R.id.simpleListView);
        updateListView(todoListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Todo clickedTodo = todos.get((int) l);

                //String todoText = "ArrayID: " + l + " Todo: " + clickedTodo.toString();
                //Toast.makeText(MainActivity.this, todoText, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), ItemActivity.class);

                //Sending data to another Activity
                intent.putExtra(getString(R.string.activity_update), true);
                intent.putExtra(getString(R.string.activity_id), clickedTodo.getId());
                intent.putExtra(getString(R.string.activity_title), clickedTodo.getTitle());
                intent.putExtra(getString(R.string.activity_desc), clickedTodo.getDesc());
                intent.putExtra(getString(R.string.activity_date), clickedTodo.getDate());
                intent.putExtra(getString(R.string.activity_prio_id), clickedTodo.getPrio_id());

                int [] cat_ids = new int [50];
                ArrayList<Category> cats = clickedTodo.getCats();
                if(!cats.isEmpty()) {
                    for (int z = 0; z <= cats.size(); z++) {
                        cat_ids[i] = cats.get(i).getId();
                    }
                }
                intent.putExtra(getString(R.string.activity_cats) , cat_ids);

                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Todo clickedTodo = todos.get((int) l);
                deleteAlert(clickedTodo.getId());
                return true;
            }
        });

        //TODO ENDINITLIST

    }

    private void updateListView(TodoListAdapter listAdapter) {
        //TODO INITLIST
        listView = (ListView) findViewById(R.id.simpleListView);
        listView.setAdapter(todoListAdapter);
    }
    @Override
    public void onResume() {
        dbh = new DatabaseHelper(this);
        int unit;
        float textSize;

        Textsize textSizeObj = dbh.getTextsize();
        unit = textSizeObj.getUnit();
        textSize = textSizeObj.getDigit();

        if (unit == 0) {
            unit = TypedValue.COMPLEX_UNIT_SP;
        }

        if (textSize == 0) {
            textSize = 12;
        }

        todoListAdapter.setTextSize(unit, textSize);
        updateListView(todoListAdapter);
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.todomenu, menu);
        return true;
    }

    /**
     * Für "Action" Buttons
     *
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
     *
     * @param v
     */
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_fab:

                startItemActivity();
                break;
            default:
                Toast.makeText(getApplicationContext(), getString(R.string.toast_error_message), Toast.LENGTH_SHORT).show();
        }
    }

    private void startSettingsActivity() {
        Log.d(TAG, "Start new Activity: SettingsActivity");
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void startItemActivity() {
        Log.d(TAG, "Start new Activity: ItemActivity");
        Intent intent = new Intent(this, ItemActivity.class);
        startActivity(intent);
    }

    private void deleteAlert(final int todoId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(getString(R.string.alert_title_confirm));
        builder.setMessage(getString(R.string.alert_message));

        builder.setPositiveButton(getString(R.string.alert_btn_yes), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dbh.deleteTodo(todoId);
                updateScreen();
            }
        });

        builder.setNegativeButton(getString(R.string.alert_btn_no), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

    /**
     * Refresht den Priority Screen
     */
    private void updateScreen() {

        Intent intent = getIntent();

        finish();

        startActivity(intent);

    }

}


