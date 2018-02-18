package com.example.mesut.todolist.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mesut.todolist.R;
import com.example.mesut.todolist.core.Priority;
import com.example.mesut.todolist.core.Todo;
import com.example.mesut.todolist.db.DatabaseHelper;
import com.example.mesut.todolist.util.PrioListAdapter;

import java.util.ArrayList;

public class PriorityActivity extends AppCompatActivity {
    private static final String TAG = "PriorityActivity";

    private DatabaseHelper dbh = null;
    private ArrayList<Priority> prios;
    private PrioListAdapter prioListAdapter;
    private ListView listView;
    private String prioName = "";
    private String prioWeight = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        dbh = new DatabaseHelper(this);

        //TODO: Beispiel Datenbankhelper für Prio wie man was macht
        //prios = dbh.getAllPriorities();
        //dbh.createPriority("NET SO WICHTIG" , -3);
        //dbh.deletePrio(prios.get(1).getId());

        prios = dbh.getAllPriorities();
        prioListAdapter = new PrioListAdapter(this, R.layout.layout_priority_settings, prios);
        listView = (ListView) findViewById(R.id.simpleListView);
        listView.setAdapter(prioListAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Priority clickedPrio = prios.get((int) l);

                String todoText = "ArrayID: " + l + " Prio: " + clickedPrio.toString();
                Toast.makeText(PriorityActivity.this, todoText, Toast.LENGTH_SHORT).show();


                prioName = clickedPrio.getName();
                int prioGewicht = clickedPrio.getWeight();
                prioWeight = prioGewicht + "";

                newPriority(prioName, prioWeight);
                dbh.deletePrio(clickedPrio.getId());
                prioName = "";
                prioWeight = "";
            }
        });


    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_fab:
                newPriority(prioName, prioWeight);
                break;
            default:
                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
        }
    }

    private void newPriority(String iva_prioName, String iva_prioWeight) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.priority_alert, null);
        dialogBuilder.setView(dialogView);

        final EditText userInput = (EditText) dialogView.findViewById(R.id.userInputnewPrio);
        final EditText prioWeight = (EditText) dialogView.findViewById(R.id.priorityWeight);

        userInput.setText(iva_prioName);
        prioWeight.setText(iva_prioWeight);
        /*
        Zum fuzellen des Spinners benoetigte Funktion

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.string_size_Array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        choosenTextSize.setAdapter(adapter);*/

        dialogBuilder.setTitle("Add Priority");

        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String usersNewCategory = userInput.getText().toString();
                String usersPrioWeight = prioWeight.getText().toString();
                try {
                    Integer priorityWeight = Integer.parseInt(usersPrioWeight);
                    getInputValue(usersNewCategory, priorityWeight);
                } catch (RuntimeException e) {
                    Toast.makeText(getApplicationContext(), "Weigth schould be Integer", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void getInputValue(String usersNewCategory, Integer priorityWeight) {
        Toast.makeText(getApplicationContext(), usersNewCategory, Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), priorityWeight.toString(), Toast.LENGTH_SHORT).show();
        dbh.createPriority(usersNewCategory, priorityWeight);
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}