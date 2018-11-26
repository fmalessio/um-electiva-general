package com.electiva_general.um.misnumeritos;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public final class AboutActivity extends AppCompatActivity {

    ListView listview;
    ArrayList<String> lines = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_about);

        listview = (ListView) findViewById(R.id.listView_aboutApp);

        loadText();

        addListeners();
    }

    private void addListeners() {
    }


    private void loadText() {

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lines);
        listview.setAdapter(adapter);

        lines.add("Esta app de juego ha sido desarrollada por alumnos de la Universidad de Morón.");
        lines.add("449 - ELECTIVA GENERAL\n713 - PROGRAMACIÓN AVANZADA II");
        lines.add("Alumnos:");
        lines.add("Alessio, Federico    3701-0374");
        lines.add("Alonso, Matías       4601-0229");
        lines.add("Lewitski, Milena     4501-0701");
        lines.add("Roldós, Ignacio      3901-2463");
        lines.add("UM - 2018 C2");

        adapter.notifyDataSetChanged();
    }



}
