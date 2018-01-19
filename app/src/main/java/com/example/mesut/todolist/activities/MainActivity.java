package com.example.mesut.todolist.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mesut.todolist.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(getApplicationContext(), "Start", Toast.LENGTH_SHORT).show();

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
                Log.d(TAG, "Start new Activity: SettingsActivity");
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
                Log.d(TAG, "Start new Activity: ItemActivity");
                startItemActivity();
                break;
            default :
                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
        }
    }

    private void startSettingsActivity(){
        Toast.makeText(getApplicationContext(), "Settings geht klar", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void startItemActivity(){
        Toast.makeText(getApplicationContext(), "fertig", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ItemActivity.class);
        startActivity(intent);
    }
}


