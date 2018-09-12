package com.electiva_general.um.misnumeritos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.electiva_general.um.misnumeritos.business.Game;
import com.electiva_general.um.misnumeritos.business.Move;

public class GameActivity extends AppCompatActivity {

    private Game game;

    // From view
    private TextView numberView;
    private Button executeButton;
    private TextView statusView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Layout references
        executeButton = findViewById(R.id.executeButton);
        numberView = findViewById(R.id.numberInputText);
        statusView = findViewById(R.id.statusText);

        // Create new Game
        game = new Game();

        Toast.makeText(getApplicationContext(), "Ya tenemos un numerito para que adivines", Toast.LENGTH_SHORT).show();

        // Test show TODO: delete this row
        this.statusView.setText(game.getNumberToGuess().toString());

        // TODO: add "Me doy" button which should call to game.leave() and redirect to finished game activity

        // events
        addListeners();
    }

    private void addListeners() {
        executeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String playerNumber = numberView.getText().toString();

                Move lastMove;
                try {

                    lastMove = game.doNewMove(playerNumber);

                    // IF THE PLAYER WON OR LEFT (FINISHED IS ABORTED OR WON), OPEN FINISHED GAME ACTIVITY
                    if (game.isGameFinished())
                    {
                        GoToFinishedGameActivity();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                String response = lastMove.toString();

                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void GoToFinishedGameActivity()
    {
        Intent finishedGameActivity = new Intent(GameActivity.this, FinishedGameActivity.class);
        finishedGameActivity.putExtra("NumberToGuess", game.getNumberToGuess());
        finishedGameActivity.putExtra("Attempts", game.getNumberOfMoves());
        finishedGameActivity.putExtra("IsGameWon", game.isGameWon());
        startActivity(finishedGameActivity);
    }
}
