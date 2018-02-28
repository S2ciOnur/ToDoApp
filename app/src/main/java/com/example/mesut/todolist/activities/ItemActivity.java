package com.example.mesut.todolist.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mesut.todolist.R;
import com.example.mesut.todolist.core.Category;
import com.example.mesut.todolist.core.Priority;
import com.example.mesut.todolist.db.DatabaseHelper;
import com.example.mesut.todolist.util.DatePickerFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Diese Klasse steuert die Activity, welche zum erstellen neuer "Todo-items"
 * verantwortlich ist
 */

public class ItemActivity extends AppCompatActivity {
    private static final String TAG = "ItemActivity";

    private EditText txtTitle;
    private EditText txtDesc;
    private Spinner spinnerPrio;
    private Button buttonCat;
    private Button buttonDate;

    private DatePickerDialog datePicker;
    private DatabaseHelper dbh;
    private boolean update = false;

    private int prio_id;
    private int[] cat_ids = new int[50];
    private int[] selectedIdDb = new int[50];


    /**
     * initialisiert und füllt den Spinner
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        this.txtTitle = (EditText) findViewById(R.id.title_editText);
        this.txtDesc = (EditText) findViewById(R.id.desc_editText);
        this.spinnerPrio = (Spinner) findViewById(R.id.prio_spinner);
        this.buttonCat = (Button) findViewById(R.id.cat_button);
        this.buttonDate = (Button) findViewById(R.id.date_button);

        dbh = new DatabaseHelper(this);

        initPrioSpinner();

        Intent intent = getIntent();
        // Receiving the Data

        update = intent.getBooleanExtra("update" , false);
        String intentTitle = intent.getStringExtra("title");
        String intentDesc = intent.getStringExtra("desc");
        String intentDate = intent.getStringExtra("date");
        int intentPrio_id = intent.getIntExtra("prio_id" , 0);
        int [] intentCat_ids = new int[50];
        intentCat_ids = intent.getIntArrayExtra("cats");

        Log.e("Second Screen", intentTitle + "." + intentDesc + "." + intentDate  + "." + prio_id);

        // Displaying Received data
        txtTitle.setText(intentTitle);
        txtDesc.setText(intentDesc);
        buttonDate.setText(intentDate);
        prio_id = intentPrio_id;
        cat_ids = intentCat_ids;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_button:
                Log.d(TAG, "Abbrechen");
                startMainActivity();
                break;
            case R.id.save_button:
                Log.d(TAG, "Speichern");
                saveItem();
                break;
            case R.id.date_button:
                Log.d(TAG, "Open Datepicker");
                openDatePicker();
                break;
            case R.id.cat_button:
                Log.d(TAG, "Open CatAlertDialog");
                showCatDialog();
                break;
            default:
                Toast.makeText(getApplicationContext(), getString(R.string.toast_error_message), Toast.LENGTH_SHORT).show();
        }
    }

    private void showCatDialog() {

        Dialog dialog;
        final ArrayList<Category> categories = dbh.getAllCategories();
        final ArrayList<String> itemsList = new ArrayList<String>();
        final ArrayList<Integer> itemsSelected = new ArrayList<Integer>();

        for (Category cat : categories) {
            itemsList.add(cat.getName());

            //  Toast.makeText(getApplicationContext(),  itemsSelected.remove(Integer.valueOf(selectedItemId)), Toast.LENGTH_SHORT).show();
        }

        final String[] items = itemsList.toArray(new String[itemsList.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.builder_categorie_title));
        builder.setMultiChoiceItems(items, null,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedItemId,
                                        boolean isSelected) {
                        if (isSelected) {
                            itemsSelected.add(selectedItemId);


                        } else if (itemsSelected.contains(selectedItemId)) {
                            itemsSelected.remove(Integer.valueOf(selectedItemId));
                        }
                    }
                })
                .setPositiveButton(getString(R.string.alert_btn_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Log.e(TAG, "Selected Items: " + itemsSelected.toString());

                        int[] ids = new int[itemsSelected.size()];


                        for (int i = 0; i < ids.length; i++) {

                            ids[i] = itemsSelected.get(i).intValue();

                        }
                        //Log.e(TAG, "ID Array Laenge: " + ids.length);
                        writeInIntArray(categories, ids);
                    }
                });

        dialog = builder.create();
        dialog.show();
    }

    /**
     * nimmt die ID's der ausgewaehlten Kategorien aus der DB um diese im Save setzen zu koennen.
     *
     * @param selectedItemId hier stehen die positionen der ausgewaehlten checkboxen drinn (Lokale ID --> Position)
     * @param categories     eine ArrayList mit allen Kategorien aus der DB
     *                       <p>
     *                       ids[] in diesem Array stehen die ID's der ausgewaehlten Kategorien drin, wie sie in der DB gespeichert sind // !!nicht die Lokale ID (Position)!!
     *                       <p>
     *                       Hash set wird genutzt um duplikate zu vermeiden.
     */
    private void writeInIntArray(ArrayList<Category> categories, int[] selectedItemId) {
        Set<Integer> set = new HashSet<Integer>();
        int[] ids = new int[selectedItemId.length];

        for (int i = 0; i < selectedItemId.length; i++) {
            set.add(categories.get(selectedItemId[i]).getId());
        }

        int i = 0;
        for (Integer val : set) {
            ids[i++] = val;
        }
        Arrays.sort(ids);
        cat_ids = ids;
    }

    private void openDatePicker() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), getString(R.string.datepicker));
    }

    private void saveItem() {
        String newTitle = txtTitle.getText().toString();
        String newDesc = txtDesc.getText().toString();
        String newDate = buttonDate.getText().toString();
        int newPrio_id = prio_id;
        int[] newCat_ids = cat_ids;

        if(update){
            //dbh.updateTodo()
        }else {
            dbh.createTodo(newTitle, newDesc, newDate, newPrio_id, newCat_ids);

            Toast.makeText(getApplicationContext(), newTitle + " erstellt!", Toast.LENGTH_SHORT).show();
        }
        startMainActivity();
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void initPrioSpinner() {
        final ArrayList<Priority> prioritys = dbh.getAllPriorities();
        ArrayList<String> names = new ArrayList<String>();


        for (Priority prio : prioritys) {
            names.add(prio.getName());
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
                prio_id = prioritys.get(position).getId();
                //Toast.makeText(getApplicationContext(), "id: " + prioritys.get(position).getId() + " Weigth: " + prioritys.get(position).getWeight(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

    }
}
