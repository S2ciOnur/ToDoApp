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
import com.example.mesut.todolist.core.Category;
import com.example.mesut.todolist.core.Textsize;
import com.example.mesut.todolist.db.DatabaseHelper;
import com.example.mesut.todolist.util.CatListAdapter;

import java.util.ArrayList;

/**
 * Diese Klasse steuert die Kategorien erstellung, bearbeitung und Löschung in der App
 * Listet die Daten aus der Datenbank
 */
public class CategoryActivity extends AppCompatActivity {
    private DatabaseHelper dbh;
    private ArrayList<Category> cats;
    private CatListAdapter catListAdapter;
    private ListView listView;

    /**
     * Entschiedet ob ein neues Item erstellt wird oder ein Bestehendes geändert wird
     * initialisiert die Click Listener auf die Listview (on Click; On Long Click),
     * Sellt die Listview aus der Datenbank her
     *
     * @param savedInstanceState savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        dbh = new DatabaseHelper(this);

        //Cats werden in die ListView gepackt
        updateListView();

        listView = findViewById(R.id.simpleListView);

        //OnClick Listener witrd initialisiert
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int catId;

                Category clickedCat = cats.get((int) l); //geclickte Kategorie wird
                String catName = clickedCat.getName();          //Speichere den Namen der Kategorie

                catId = clickedCat.getId(); // Speichere Id der Kategorie
                editCategory(catId, catName);
            }
        });

        //OnLongClick Listener wird initalisiert
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                int catId;
                Category clickedCat = cats.get((int) l);
                catId = clickedCat.getId();
                deleteAlert(catId);
                return true;
            }
        });
    }

    private void updateListView() {
        //Datenbank wird ausgelesen
        cats = dbh.getAllCategories();
        //Cats werden aus der DB extrahiert
        catListAdapter = new CatListAdapter(this, R.layout.layout_category_settings, cats);
        listView = findViewById(R.id.simpleListView);
        updateAdapter(catListAdapter);
        setTextSize();
    }

    private void updateAdapter(CatListAdapter catListAdapter) {
        listView.setAdapter(catListAdapter);
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

        catListAdapter.setTextSize(textUnit, textSize);
        updateAdapter(catListAdapter);
    }

    /**
     * startet ein Alert Fenster --> "Yes" Löscht die gewählte Kategorie
     *
     * @param catId enthält die Id der Kategorie, die geclickt wurde --> OnCreate OnLongClick
     */
    private void deleteAlert(final Integer catId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(getString(R.string.alert_title_confirm));
        builder.setMessage(getString(R.string.alert_message));

        builder.setPositiveButton(getString(R.string.alert_btn_yes), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dbh.deleteCat(catId); //Kategorie wird aus der DB entfernt
                updateListView();       //das Activity Screen wird refresht
                dialog.dismiss();     // Alert verschwindet
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
                newCategory();
                break;
            default:
                Toast.makeText(getApplicationContext(), getString(R.string.toast_error_message), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * startet ein Alert zum bearbeiten einer Vorhaneden Kategorie
     * aktualisiert den namen der gewählten kategorie in der DB
     *
     * @param catId   Enthält die Id der Kategorie, welche verändert werden soll.
     * @param catName Enthält den Namen der Kategorie
     */
    private void editCategory(final Integer catId, final String catName) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.category_alert, null);
        dialogBuilder.setView(dialogView);


        //Setzt den Kategorie Namen in die Textbox des Alert
        final EditText userInput = dialogView.findViewById(R.id.userInputnewCat);
        userInput.setText(catName);

        dialogBuilder.setTitle(getString(R.string.dialog_title_category));

        dialogBuilder.setPositiveButton(getString(R.string.dialog_done_btn), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String usersNewCategory = userInput.getText().toString();

                dbh.updateCat(catId, usersNewCategory);
                updateListView();


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
     * speichert in der db
     */
    private void newCategory() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.category_alert, null);
        dialogBuilder.setView(dialogView);

        final EditText userInput = dialogView.findViewById(R.id.userInputnewCat);

        dialogBuilder.setTitle(getString(R.string.dialog_title_category));

        dialogBuilder.setPositiveButton(getString(R.string.dialog_done_btn), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String usersNewCategory = userInput.getText().toString();

                dbh.createCategory(usersNewCategory);
                updateListView();
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