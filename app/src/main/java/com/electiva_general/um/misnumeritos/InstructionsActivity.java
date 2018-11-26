package com.electiva_general.um.misnumeritos;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public final class InstructionsActivity extends AppCompatActivity {

    ListView listview;
    ArrayList<String> lines = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_instructions);

        listview = (ListView) findViewById(R.id.listView_instructions);

        loadText();

        addListeners();
    }

    private void addListeners() {
    }


    private void loadText() {

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lines);
        listview.setAdapter(adapter);

        lines.add("El objetivo del juego consiste en tratar de adivinar un número aleatorio, y en cada jugada se irá indicando la cantidad de dígitos acertados o regulares.");
        lines.add("El número a adivinar (y por tanto el de cada jugada), debe cumplir las siguientes condiciones:");
        lines.add("Ser de 4 dígitos, los cuales no pueden estar repetidos");
        lines.add("No comenzar con el dígito 0 (cero)");
        lines.add("Con estas condiciones, el número debe estar entre 1023 y 9876, y excluyendo cualquier combinación en la que hayan dígitos iguales");
        lines.add("En cada jugada, se indicará el Nº jugado, y la cantidad de aciertos (BIEN) y de existentes pero en otra posición (REGULARES)");
        lines.add("Ejemplos de números NO Válidos:\n0389 (comienza con cero)\n1424 (se repite uno o más dígitos)\n629 (no es de 4 dígitos)");
        lines.add("");
        lines.add("En caso de adivinar el número, si está entre los 10 con menor cantidad de intentos, su nombre ingresará al TOP TEN");

        adapter.notifyDataSetChanged();
    }




}
