package com.example.mesut.todolist.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mesut.todolist.R;
import com.example.mesut.todolist.core.Category;
import com.example.mesut.todolist.core.Priority;
import com.example.mesut.todolist.db.DatabaseHelper;
import com.example.mesut.todolist.util.CatListAdapter;

import java.util.ArrayList;

/**
 * Diese Klasse steuert die Kategorien erstellung und Löschung in der AQpp
 */
public class CategoryActivity extends AppCompatActivity {

    private DatabaseHelper dbh;
    private ArrayList<Category> cats;
    private CatListAdapter catListAdapter;
    private ListView listView;
    private String catName = "";
    private Integer catId;
    private boolean newElement = true;

    private static final String TAG = "CategoryActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        dbh = new DatabaseHelper(this);

        cats = dbh.getAllCategories();
        catListAdapter = new CatListAdapter(this, R.layout.layout_category_settings, cats);
        listView = (ListView) findViewById(R.id.simpleListView);
        listView.setAdapter(catListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Category clickedCat = cats.get((int) l);

                String todoText = "ArrayID: " + l + " Prio: " + clickedCat.toString();
                Toast.makeText(CategoryActivity.this, todoText, Toast.LENGTH_SHORT).show();


                catName = clickedCat.getName();

                catId = new Integer(clickedCat.getId());
                newElement = false;

                newCategory(catId, catName);
            }
        });

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_fab:
                newCategory(catId, catName);
                break;
            default:
                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateScreen() {

        Intent intent = getIntent();

        finish();

        startActivity(intent);

    }

    private void newCategory(final Integer catId, final String iva_catName) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.category_alert, null);
        dialogBuilder.setView(dialogView);

        final EditText userInput = (EditText) dialogView.findViewById(R.id.userInputnewCat);

        if (newElement != true) {
            userInput.setText(iva_catName);
        }

        dialogBuilder.setTitle("Add Category");

        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String usersNewCategory = userInput.getText().toString();
                if (newElement != true) {

                    dbh.updateCat(catId, usersNewCategory);
                    updateScreen();
                } else {

                    getInputValue(usersNewCategory);
                }
                newElement = true;
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

    private void getInputValue(String usersNewCategory) {
        Toast.makeText(getApplicationContext(), usersNewCategory, Toast.LENGTH_SHORT).show();
        dbh.createCategory(usersNewCategory);
        updateScreen();
    }
}