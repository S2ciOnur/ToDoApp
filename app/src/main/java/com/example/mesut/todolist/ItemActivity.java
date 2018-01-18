package com.example.mesut.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ItemActivity extends AppCompatActivity {
    private static final String TAG = "ItemActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
    }
}
