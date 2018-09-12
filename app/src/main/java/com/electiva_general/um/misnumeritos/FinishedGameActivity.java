package com.electiva_general.um.misnumeritos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;

public class FinishedGameActivity extends AppCompatActivity {

    private ArrayList<String> numberToGuess;
    private int attempts;
    private boolean isGameWon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished_game);

        Bundle extras = getIntent().getExtras();

        // TODO: THESE VALUES MAY BE RETRIEVED FROM DATABASE AS A SAFER PRACTICE, INSTEAD OF GETTING THEM FROM THE PREVIOUS ACTIVITY
        // TODO: if extras is null, or any of the keys are not found, send error message to screen
        if (extras != null) {
            numberToGuess = extras.getStringArrayList("NumberToGuess");
            attempts = extras.getInt("Attempts");
            isGameWon = extras.getBoolean("IsGameWon");
        }


        // TODO: load winner or looser message on screen according to variable "isGameWon"
        // if isGameWon == false, the player left the game; otherwise, the game was won
        // TODO: show on screen the number that the player had to guess
        // TODO: show on screen the number of moves (attempts) that the player made before leaving or winning

        addListeners();
    }

    private void addListeners() {
        // TODO: add button to start a new game (navigate to game activity)
        // TODO: add button to see scores (navigate to scores activity)
    }
}
