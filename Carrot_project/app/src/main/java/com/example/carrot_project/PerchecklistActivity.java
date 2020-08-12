package com.example.carrot_project;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ListViewAutoScrollHelper;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import checklist.ChecklistAdapter;

public class PerchecklistActivity extends AppCompatActivity {

    List<String> data;
    ArrayAdapter<String> adapter;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perchecklist);

        ActionBar ab = getSupportActionBar();
        ab.hide();

        data = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,data);
        list = (ListView)findViewById(R.id.perlistview);
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = data.get(position);

                Toast.makeText(PerchecklistActivity.this,item,Toast.LENGTH_LONG).show();
            }
        });

        Button Add = (Button)findViewById(R.id.add);
        /*Add.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                EditText newItem = (EditText)findViewById(R.id.);
                String item = newItem.getText().toString();
                if(item!=null || item.trim().length()>0){
                    data.add(item.trim());
                    adapter.notifyDataSetChanged();
                    newItem.setText("");
                }
            }
        });*/



    }
}
