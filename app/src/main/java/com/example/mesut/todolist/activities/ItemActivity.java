package com.example.mesut.todolist.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mesut.todolist.R;
import com.example.mesut.todolist.db.DatabaseHelper;

public class ItemActivity extends AppCompatActivity {
    private static final String TAG = "ItemActivity";

    private DatabaseHelper todoDbHelper;
    private EditText title;
    private EditText desc;
    private Spinner prio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        this.title = (EditText) findViewById(R.id.title_editText);
        this.desc = (EditText) findViewById(R.id.desc_editText);
        this.prio = (Spinner) findViewById(R.id.prio_spinner);

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

        // Datenbank zum Schreiben öffnen
        SQLiteDatabase db = todoDbHelper.getWritableDatabase();
        // Datensatz erstellen
        ContentValues vals = new ContentValues();
        vals.put(DatabaseHelper.TITLE_FIELD_NAME, title.getText().toString());
        vals.put(DatabaseHelper.DESC_FIELD_NAME, desc.getText().toString());

        // Datensatz in die Datenbank einfügen
        db.insert(DatabaseHelper.TABLE_NAME,null, vals);

        // Datenbank schließen
        db.close();

        startMainActivity();
    }

    private void startMainActivity(){
        Toast.makeText(getApplicationContext(), "Settings geht klar", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void initPrioSpinner(){
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.prio_Array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        prio.setAdapter(adapter);

    }
}
