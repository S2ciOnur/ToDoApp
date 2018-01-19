package com.example.mesut.todolist.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mesut.todolist.R;

public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = "SettingsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }
}
