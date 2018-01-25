package com.example.mesut.todolist.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mesut.todolist.R;

public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = "SettingsActivity";
    private Button settingsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        this.settingsBtn = (Button) findViewById(R.id.btnSettings);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSettings:
                showSettingsDialog();
                break;
            default:
                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
        }
    }


    public void showSettingsDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.textsize_alert, null);
        dialogBuilder.setView(dialogView);

        final EditText userInput = (EditText) dialogView.findViewById(R.id.userInput);
        final Spinner choosenTextSize = (Spinner) dialogView.findViewById(R.id.choosenTextSize);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.string_size_Array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        choosenTextSize.setAdapter(adapter);

        dialogBuilder.setTitle("Change Text Size");

        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String textSize = userInput.getText().toString();
                String sizeType = choosenTextSize.getSelectedItem().toString();
                getInputValue(textSize, sizeType);
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





    private void getInputValue(String textSize, String sizeType) {
        Toast.makeText(getApplicationContext(), textSize, Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), sizeType, Toast.LENGTH_SHORT).show();
        try {
            int textSizeInput = Integer.parseInt(textSize);
        } catch (RuntimeException e) {
            Toast.makeText(getApplicationContext(), "Bitte nur Integer Werte", Toast.LENGTH_SHORT).show();
        }
    }
}