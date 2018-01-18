package com.example.mesut.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ItemActivity extends AppCompatActivity {
    private static final String TAG = "ItemActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        initPrioSpinner();
    }

    public void onClick(View v){
        switch(v.getId()) {
            case R.id.cancel_button :
                Log.d(TAG, "Abbrechen");
                startMainActivity();
                break;
            case R.id.save_button :
                Log.d(TAG, "Speichern");
                saveItem();
                break;
            default :
                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveItem(){
        //Speichern...
        startMainActivity();
    }

    private void startMainActivity(){
        Toast.makeText(getApplicationContext(), "Settings geht klar", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void initPrioSpinner(){
        Spinner spinner = (Spinner) findViewById(R.id.prio_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.prio_Array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

    }
}
