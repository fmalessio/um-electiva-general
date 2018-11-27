package com.electiva_general.um.misnumeritos;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public final class InstructionsActivity extends AppCompatActivity {

    ListView listview;
    TextView instructionsTV;
    ArrayList<String> lines = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_instructions);

//        listview = (ListView) findViewById(R.id.listView_instructions);
        instructionsTV = findViewById(R.id.instructionsTV);
        loadText();

        addListeners();
    }

    private void addListeners() {
    }


    private void loadText() {
        String text = "El objetivo del juego consiste en tratar de adivinar el numerito! ¿Crees que podes lograrlo? \n\n";

        text = text + "¿Como se juega?\n";
        text = text + "1. El jugador ingresa un número en el cuadro de texto (leer las reglas del número.)\n";
        text = text + "2. En cada jugada, se indicará el Nº jugado, y la cantidad de aciertos (BIEN) y de existentes pero en otra posición (REGULARES).\n";
        text = text + "3. A partir de estos datos el jugador deberá adivinar el número.\n";
        text = text + "4. Sí tu número está entre los 10 primeros tendrás un lugar en EL SALÓN DE LA FAMA!!!\n\n";


        text = text + "El número a adivinar debe cumplir las siguientes condiciones:\n";
        text = text + "1. Debe estar comprendido entre 1023 y 9876, ambos inclusive.\n";
        text = text + "2. Ser de 4 dígitos, los cuales no pueden estar repetidos. \n";
        text = text + "Ejemplos de números NO válidos:\n\t\t*0389 (comienza con cero)\n\t\t*1424 (se repite uno o más dígitos)\n\t\t*629 (no es de 4 dígitos)";

        instructionsTV.setText(text);
    }




}
