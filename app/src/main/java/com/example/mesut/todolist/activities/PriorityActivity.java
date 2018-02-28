package com.example.mesut.todolist.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mesut.todolist.R;
import com.example.mesut.todolist.core.Priority;
import com.example.mesut.todolist.core.Textsize;
import com.example.mesut.todolist.db.DatabaseHelper;
import com.example.mesut.todolist.util.PrioListAdapter;

import java.util.ArrayList;

/**
 * Diese Klasse steuert die Priority aktivität in welcher
 * neue Prioritäten erstellt, bearbeitet oder gelöscht werden
 * <p>
 * Listen die Daten aus der DB
 */
public class PriorityActivity extends AppCompatActivity {
    private static final String TAG = "PriorityActivity";

    private DatabaseHelper dbh = null;
    private ArrayList<Priority> prios;
    private PrioListAdapter prioListAdapter;
    private ListView listView;


    /**
     * Entschiedet ob ein neues Item erstellt wird oder ein Bestehendes geändert wird
     * initialisiert die Click Listener auf die Listview (on Click; On Long Click),
     * Sellt die Listview aus der Datenbank her
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        dbh = new DatabaseHelper(this);
        listView = findViewById(R.id.simpleListView);
        updateListView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Priority clickedPrio = prios.get((int) l);


                String prioName = clickedPrio.getName();
                int prioGewicht = clickedPrio.getWeight();
                String prioWeight = prioGewicht + "";

                Integer priorityId = new Integer(clickedPrio.getId());

                editPriority(priorityId, prioName, prioWeight);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Priority clickedPrio = prios.get((int) l);
                Integer priorityId = new Integer(clickedPrio.getId());
                deleteAlert(priorityId);
                return true;
            }
        });
    }

    private void updateListView() {
        prios = dbh.getAllPriorities();
        prioListAdapter = new PrioListAdapter(this, R.layout.layout_priority_settings, prios);
        listView = findViewById(R.id.simpleListView);
        updateAdapter(prioListAdapter);
        setTextSize();
    }

    private void updateAdapter(PrioListAdapter prioListAdapter) {
        listView.setAdapter(prioListAdapter);
    }

    @Override
    public void onResume() {
        setTextSize();
        super.onResume();
    }

    private void setTextSize() {
        int textUnit;
        float textSize;

        Textsize textSizeObj = dbh.getTextsize();
        textUnit = textSizeObj.getUnit();
        textSize = textSizeObj.getDigit();

        if (textUnit == 0) {
            textUnit = TypedValue.COMPLEX_UNIT_SP;
        }

        if (textSize == 0) {
            textSize = 12;
        }

        prioListAdapter.setTextSize(textUnit, textSize);
        updateAdapter(prioListAdapter);
    }

    /**
     * startet ein Alert Fenster --> "Yes" Löscht die gewählte Kategorie
     *
     * @param priorityId enthält die Id der Prioritaet, die geclickt wurde --> OnCreate OnLongClick
     */
    private void deleteAlert(final Integer priorityId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(getString(R.string.alert_title_confirm));
        builder.setMessage(getString(R.string.alert_message));

        builder.setPositiveButton(getString(R.string.alert_btn_yes), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dbh.deletePrio(priorityId);
                updateListView();
                dialog.dismiss();
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
     * Reagiert auf den Klick des FAB Button "+"
     * Startet newCategory()
     *
     * @param v enthält die View des FAB Button --> XML
     */
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_fab:
                newPriority();
                break;
            default:
                Toast.makeText(getApplicationContext(), getString(R.string.toast_error_message), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * startet ein Alert zum bearbeiten einer Vorhaneden Kategorie
     * aktualisiert den namen der gewählten kategorie in der DB
     *
     * @param priorityId     Enthählt die Id der zu bearbeitenden prioritaet
     * @param iva_prioName   Enthaelt den Namen der zu bearbeitenden Prio
     * @param iva_prioWeight Enthält das Gewicht der Prio
     */
    private void editPriority(final Integer priorityId, final String iva_prioName, String iva_prioWeight) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.priority_alert, null);
        dialogBuilder.setView(dialogView);

        final EditText userInput = (EditText) dialogView.findViewById(R.id.userInputnewPrio);
        final EditText prioWeight = (EditText) dialogView.findViewById(R.id.priorityWeight);

        userInput.setText(iva_prioName);
        prioWeight.setText(iva_prioWeight);
        dialogBuilder.setTitle(getString(R.string.dialog_title_priority));

        dialogBuilder.setPositiveButton(getString(R.string.dialog_done_btn), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String usersNewCategory = userInput.getText().toString();
                String usersPrioWeight = prioWeight.getText().toString();
                try {
                    Integer priorityWeight = Integer.parseInt(usersPrioWeight);
                    dbh.updatePrio(priorityId, usersNewCategory, priorityWeight);
                    updateListView();
                } catch (RuntimeException e) {
                    // Toast muss bleiben, Info an User
                    Toast.makeText(getApplicationContext(), getString(R.string.toast_message), Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialogBuilder.setNegativeButton(getString(R.string.dialog_cancel_btn), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });

        AlertDialog b = dialogBuilder.create();
        b.show();

    }

    /**
     * startet ein Alert zum erstellen einer neuen Kategorie
     * Speichert in der db
     */
    private void newPriority() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.priority_alert, null);
        dialogBuilder.setView(dialogView);

        final EditText userInput = (EditText) dialogView.findViewById(R.id.userInputnewPrio);
        final EditText prioWeight = (EditText) dialogView.findViewById(R.id.priorityWeight);


        dialogBuilder.setTitle(getString(R.string.dialog_title_priority));

        dialogBuilder.setPositiveButton(getString(R.string.dialog_done_btn), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String usersNewCategory = userInput.getText().toString();
                String usersPrioWeight = prioWeight.getText().toString();
                try {

                    Integer priorityWeight = Integer.parseInt(usersPrioWeight);
                    dbh.createPriority(usersNewCategory, priorityWeight);

                    updateListView();

                } catch (RuntimeException e) {
                    // Toast muss bleiben, Info an User
                    Toast.makeText(getApplicationContext(), getString(R.string.toast_message), Toast.LENGTH_SHORT).show();
                }

            }
        });
        dialogBuilder.setNegativeButton(getString(R.string.dialog_cancel_btn), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });

        AlertDialog b = dialogBuilder.create();
        b.show();
    }
}