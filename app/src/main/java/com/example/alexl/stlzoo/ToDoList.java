package com.example.alexl.stlzoo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by samikshasm on 4/5/18.
 */

public class ToDoList extends AppCompatActivity {
    // Array of strings...
    ListView simpleList;
    private ArrayList<String> toDoListItems = new ArrayList<>();

    String countryList[] = {"India", "China", "australia", "Portugle", "America", "NewZealand"};

    @Override   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        ArrayList<String> selectedItems = getIntent().getStringArrayListExtra("To Do List Items");
        for (int i=0; i<selectedItems.size(); i++){
            String[] substr = selectedItems.get(i).split("=");
            //String substr1 = substr[1];
            //String[] separated = substr1.split("}");
            String separated = substr[1].replace(substr[1].substring(substr[1].length()-1), "");
            toDoListItems.add(separated);
        }
        simpleList = (ListView)findViewById(R.id.simpleListView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_todo, R.id.textView, toDoListItems);
        simpleList.setAdapter(arrayAdapter);


    }
}
