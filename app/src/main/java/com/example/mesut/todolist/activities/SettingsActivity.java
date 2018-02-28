package com.example.mesut.todolist.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mesut.todolist.R;
import com.example.mesut.todolist.core.Textsize;
import com.example.mesut.todolist.db.DatabaseHelper;

import java.lang.reflect.Type;

/**
 * Diese Klasse ind für die Settings Activity zuständig.
 * sie satrtet die Jeweiligen Activitys on Click
 * bzw. das Alert Fenster fuer die TextSize
 */
public class SettingsActivity extends AppCompatActivity {
    private DatabaseHelper dbh;
    private static final String TAG = "SettingsActivity";
    private Button textSizeBtn;
    private Button priorityBtn;
    private Button categoryBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dbh = new DatabaseHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        this.textSizeBtn = (Button) findViewById(R.id.btnTextSize);
        this.priorityBtn = (Button) findViewById(R.id.btnPriority);
        this.categoryBtn = (Button) findViewById(R.id.btnCategory);


    }

    /**
     * Reagiert auf die events der einzelnen Buttons
     * Text Size, Category, Priority
     *
     * @param v enthält die View der Button --> XML
     */
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnTextSize:
                showTextSizeDialog();
                break;
            case R.id.btnCategory:
                startCategoryActivity();
                break;
            case R.id.btnPriority:
                startPriorityActivity();
                break;
            default:
                Toast.makeText(getApplicationContext(), getString(R.string.toast_error_message), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * startet die Kategorien Activity
     */
    private void startCategoryActivity() {
        Intent intent = new Intent(this, CategoryActivity.class);
        startActivity(intent);
    }

    /**
     * startet die Prioritäten Activity
     */
    private void startPriorityActivity() {
        Intent intent = new Intent(this, PriorityActivity.class);
        startActivity(intent);
    }

    /**
     * erstellt ein Alert dialog
     * um die Textsize ändern zu können.
     * initialisiert den Spinenr fuer die Auswahl der Text Groese
     */
    private void showTextSizeDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.textsize_alert, null);
        dialogBuilder.setView(dialogView);

        final EditText userInput = (EditText) dialogView.findViewById(R.id.userInput);
        final Spinner choosenTextSize = (Spinner) dialogView.findViewById(R.id.choosenTextSize);

        Textsize textsize = dbh.getTextsize();


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.string_size_Array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        choosenTextSize.setAdapter(adapter);

        dialogBuilder.setTitle(getString(R.string.dialog_title_textsize));

        userInput.setText(String.valueOf(textsize.getDigit()));
        choosenTextSize.setSelection(textsize.getUnit());

        dialogBuilder.setPositiveButton(getString(R.string.dialog_done_btn), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String textSize = userInput.getText().toString();
                String sizeType = choosenTextSize.getSelectedItem().toString();
                getInputValue(textSize, sizeType);
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


    private void getInputValue(String textSize, String sizeType) {
        int unit = TypedValue.COMPLEX_UNIT_SP;
        float textSizeInput = 12;

        switch (sizeType) {
            case "px":
                unit = TypedValue.COMPLEX_UNIT_PX;
                break;
            case "dp":
                unit = TypedValue.COMPLEX_UNIT_DIP;
                break;
            case "sp":
                unit = TypedValue.COMPLEX_UNIT_SP;
                break;
            case "pt":
                unit = TypedValue.COMPLEX_UNIT_PT;
                break;
            case "in":
                unit = TypedValue.COMPLEX_UNIT_IN;
                break;
            case "mm":
                unit = TypedValue.COMPLEX_UNIT_MM;
                break;
        }

        try {
            textSizeInput = Float.parseFloat(textSize);
        } catch (RuntimeException e) {
            Toast.makeText(getApplicationContext(), getString(R.string.toast_integer_message), Toast.LENGTH_SHORT).show();
        }

        dbh.updateTextsize(textSizeInput, unit);
    }
}