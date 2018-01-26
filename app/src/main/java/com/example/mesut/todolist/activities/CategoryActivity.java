package com.example.mesut.todolist.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mesut.todolist.R;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
    }

    public void onClick(View v){
        switch(v.getId()) {
            case R.id.add_fab :
                startCategoryActivity();
                break;
            default :
                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
        }
    }

    public void startCategoryActivity(){
        Intent intent = new Intent(this, CategoryActivity.class);
        startActivity(intent);
    }
}
