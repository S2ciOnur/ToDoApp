package com.example.mesut.todolist.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mesut.todolist.R;
import com.example.mesut.todolist.core.Category;
import com.example.mesut.todolist.db.DatabaseHelper;
import com.example.mesut.todolist.util.StartListAdapter;

import java.util.ArrayList;

public class StartActivity extends AppCompatActivity {

    private StartListAdapter startListAdapter = null;
    private ListView listView = null;
    private DatabaseHelper dbh = null;
    private ArrayList<Category> cats = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        cats = dbh.getAllCategories();
        startListAdapter = new StartListAdapter(this, R.layout.layout_todolist, cats);

        listView = (ListView) findViewById(R.id.simpleListView);
        listView.setAdapter(startListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Category clickedCat = cats.get((int) l);

                String catText = "ArrayID: " + l + " Cat: " + clickedCat.toString();
                Toast.makeText(StartActivity.this, catText, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                //Sending data to another Activity
                intent.putExtra("id", clickedCat.getId());


                //Log.e("n", inputName.getText()+"."+ inputEmail.getText());

                startActivity(intent);
            }
        });
    }
}
