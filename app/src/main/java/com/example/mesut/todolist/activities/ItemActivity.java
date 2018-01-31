package com.example.mesut.todolist.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mesut.todolist.R;
import com.example.mesut.todolist.core.Category;
import com.example.mesut.todolist.core.Priority;
import com.example.mesut.todolist.core.Todo;
import com.example.mesut.todolist.db.DatabaseHelper;
import com.example.mesut.todolist.util.DatePickerFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ItemActivity extends AppCompatActivity {
    private static final String TAG = "ItemActivity";

    private EditText txtTitle;
    private EditText txtDesc;
    private Spinner spinnerPrio;
    private Spinner spinnerCat;
    private Button buttonDate;

    private DatePickerDialog datePicker;
    private DatabaseHelper dbh;
    private int idPrioritaet;
    private String categorie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        this.txtTitle = (EditText) findViewById(R.id.title_editText);
        this.txtDesc = (EditText) findViewById(R.id.desc_editText);
        this.spinnerPrio = (Spinner) findViewById(R.id.prio_spinner);
        this.spinnerCat = (Spinner) findViewById(R.id.cat_spinner);
        this.buttonDate = (Button) findViewById(R.id.date_button);


        dbh = new DatabaseHelper(this);

        initPrioSpinner();
        initCatSpinner();

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
            case R.id.date_button :
                Log.d(TAG, "Open Datepicker");
                openDatePicker();
                break;
            default :
                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
        }
    }

    private void openDatePicker(){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(),"Date Picker");
    }

    private void saveItem(){
        String newTitle = txtTitle.getText().toString();
        String newDesc = txtDesc.getText().toString();
        String newDate = buttonDate.getText().toString();
        int newPrio_id = idPrioritaet;
        int[] newCats = {2,3};
        dbh.createTodo(newTitle, newDesc, newDate, newPrio_id, newCats);
        Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
        startMainActivity();
    }

    private void startMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void initPrioSpinner(){
        final ArrayList<Priority> prioritys = dbh.getAllPriorities();
        ArrayList<String> names = new ArrayList<String>();


        for(Priority prio : prioritys) {
            names.add(prio.getName());
          //  Toast.makeText(getApplicationContext(), prio.getName(), Toast.LENGTH_SHORT).show();
        }


        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, names);
        // Specify the layout to use when the list of choices appears
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerPrio.setAdapter(dataAdapter);

        spinnerPrio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                idPrioritaet = prioritys.get(position).getWeight();
                Toast.makeText(getApplicationContext(), "id: " + prioritys.get(position).getId() + " Weigth: " + prioritys.get(position).getWeight(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

    }

    private void initCatSpinner(){
        final ArrayList<Category> categorys = dbh.getAllCategories();
        ArrayList<String> names = new ArrayList<String>();


        for(Category cat : categorys) {
            names.add(cat.getName());
            //  Toast.makeText(getApplicationContext(), prio.getName(), Toast.LENGTH_SHORT).show();
        }


        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, names);
        // Specify the layout to use when the list of choices appears
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerCat.setAdapter(dataAdapter);

        spinnerCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                categorie = categorys.get(position).getName();
                Toast.makeText(getApplicationContext(), "id: " + categorys.get(position).getId() + " Name: " + categorys.get(position).getName(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

    }
}
