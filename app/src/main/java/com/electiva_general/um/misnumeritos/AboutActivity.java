package com.electiva_general.um.misnumeritos;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public final class AboutActivity extends AppCompatActivity {

    TextView about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_about);

        about = findViewById(R.id.aboutTV);
        loadText();

        addListeners();
    }

    private void addListeners() {
    }

    private void loadText() {
        String text = "Esta app de juego ha sido desarrollada por alumnos de la Universidad de Morón. \n\n";

        text = text + "449 - ELECTIVA GENERAL\n713 - PROGRAMACIÓN AVANZADA II\n\n";
        text = text + "Alumnos:\n";
        text = text + "Alessio, Federico    3701-0374\n";
        text = text + "Alonso, Matías       4601-0229\n";
        text = text + "Lewitzki, Milena     4501-0701\n";
        text = text + "Roldós, Ignacio      3901-2463\n\n";

        text = text + "UM - 2018 C2";

        about.setText(text);
    }
}
