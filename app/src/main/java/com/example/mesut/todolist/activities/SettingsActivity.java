package com.example.mesut.todolist.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mesut.todolist.R;

public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = "SettingsActivity";
    private EditText userInput;
    private TextView userOutput;
    private Spinner choosentextSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initSizeSpinner();
        this.choosentextSize = (Spinner)findViewById(R.id.spinner_sizeUnit);
        this.userInput = (EditText)findViewById(R.id.fontsize_editText);
        this.userOutput = (TextView)findViewById(R.id.fontsize_textView);
    }

    public void onClick(View v){
        switch(v.getId()) {
            case R.id.fontSize_button :
                Log.d(TAG, "Start new Activity: ItemActivity");
                getInputValue();
                break;
            default :
                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
        }
    }

    private void getInputValue(){
      String input = userInput.getText().toString();
      String textSizeString = choosentextSize.getSelectedItem().toString();
        Toast.makeText(getApplicationContext(), textSizeString, Toast.LENGTH_SHORT).show();
      try {
          int textSize = Integer.parseInt(input);
          userOutput.setText(input);
      }
      catch (RuntimeException e){
          Toast.makeText(getApplicationContext(), "Bitte nur Integer Werte", Toast.LENGTH_SHORT).show();
      }
    }

    private void initSizeSpinner(){
        Spinner spinner = (Spinner) findViewById(R.id.spinner_sizeUnit);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.string_size_Array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

    }
}
