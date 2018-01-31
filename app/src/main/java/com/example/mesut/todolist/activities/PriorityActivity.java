package com.example.mesut.todolist.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mesut.todolist.R;
import com.example.mesut.todolist.core.Priority;
import com.example.mesut.todolist.db.DatabaseHelper;

import java.util.ArrayList;

public class PriorityActivity extends AppCompatActivity {

    private DatabaseHelper dbh = null;
    private ArrayList<Priority> prios;

    private static final String TAG = "PriorityActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        dbh = new DatabaseHelper(this);

        //TODO: Beispiel Datenbankhelper für Prio wie man was macht
        //prios = dbh.getAllPriorities();
        //dbh.createPriority("NET SO WICHTIG" , -3);
        //dbh.deletePrio(prios.get(1).getId());
    }

    public void onClick(View v){
        switch(v.getId()) {
            case R.id.add_fab :
                newPriority();
                break;
            default :
                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
        }
    }

    private void newPriority(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.priority_alert, null);
        dialogBuilder.setView(dialogView);

        final EditText userInput = (EditText) dialogView.findViewById(R.id.userInputnewPrio);
        final EditText prioWeight = (EditText) dialogView.findViewById(R.id.priorityWeight);

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
                }
                catch(RuntimeException e) {
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

    private void getInputValue(String usersNewCategory, Integer priorityWeight){
        Toast.makeText(getApplicationContext(), usersNewCategory, Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), priorityWeight.toString(), Toast.LENGTH_SHORT).show();
        dbh.createPriority(usersNewCategory, priorityWeight);
    }
}