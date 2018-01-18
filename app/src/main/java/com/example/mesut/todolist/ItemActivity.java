package com.example.mesut.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class ItemActivity extends AppCompatActivity {
    private static final String TAG = "ItemActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
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
            default :
                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveItem(){
        //Speichern...
        startMainActivity();
    }

    private void startMainActivity(){
        Toast.makeText(getApplicationContext(), "Settings geht klar", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}
