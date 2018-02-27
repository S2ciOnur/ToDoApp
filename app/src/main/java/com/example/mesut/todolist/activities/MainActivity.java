package com.example.mesut.todolist.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mesut.todolist.R;
import com.example.mesut.todolist.core.Priority;
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
        todoListAdapter = new TodoListAdapter(this, R.layout.layout_todolist, todos , prios);

        //TODO INITLIST
        listView = (ListView) findViewById(R.id.simpleListView);
        listView.setAdapter(todoListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Todo clickedTodo = todos.get((int) l);

                String todoText = "ArrayID: " + l + " Todo: " + clickedTodo.toString();
                Toast.makeText(MainActivity.this, todoText, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), ItemActivity.class);

                //Sending data to another Activity
                intent.putExtra("update", true);
                intent.putExtra("title", clickedTodo.getTitle());
                intent.putExtra("desc", clickedTodo.getDesc());
                intent.putExtra("date", clickedTodo.getDate());
                //intent.putExtra("prio_id", clickedTodo.getPrio_id());
                //intent.putExtra("cats" , clickedTodo.getCats());


                //Log.e("n", inputName.getText()+"."+ inputEmail.getText());

                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, "LÖSCHE " + todos.get((int) l).getTitle() + "???", Toast.LENGTH_SHORT).show();
                deleteAlert();
                return true;
            }
        });

        //TODO ENDINITLIST

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
                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
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

    private void deleteAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirm");
        builder.setMessage("Are you sure to delete?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }
}


