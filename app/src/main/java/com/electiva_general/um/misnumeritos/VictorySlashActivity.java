package com.electiva_general.um.misnumeritos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class VictorySlashActivity extends AppCompatActivity {

    private ArrayList<String> numberToGuess;
    private String username;
    private int attempts;
    private boolean isGameWon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_victory_slash);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            numberToGuess = extras.getStringArrayList("NumberToGuess");
            username = extras.getString("Username");
            attempts = extras.getInt("Attempts");
            isGameWon = extras.getBoolean("IsGameWon");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Intent finishedGameActivity = new Intent(VictorySlashActivity.this, FinishedGameActivity.class);
                finishedGameActivity.putExtra("NumberToGuess", numberToGuess);
                finishedGameActivity.putExtra("Username", username);
                finishedGameActivity.putExtra("Attempts", attempts);
                finishedGameActivity.putExtra("IsGameWon",isGameWon);

                startActivity(finishedGameActivity);
                finish();
            }
        }, 2000);
    }

    private void goFinishedGameScreen() {
        Intent finishedGameActivity = new Intent(VictorySlashActivity.this, FinishedGameActivity.class);
        finishedGameActivity.putExtra("NumberToGuess", numberToGuess);
        finishedGameActivity.putExtra("Username", username);
        finishedGameActivity.putExtra("Attempts", attempts);
        finishedGameActivity.putExtra("IsGameWon",isGameWon);

        startActivity(finishedGameActivity);
        finish();
    }
}
