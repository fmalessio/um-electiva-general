package com.electiva_general.um.misnumeritos;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.electiva_general.um.misnumeritos.business.Game;
import com.electiva_general.um.misnumeritos.business.Move;

public class GameActivity extends AppCompatActivity {

    private Game game;
    private String username;

    // From view
    private TextView numberView;
    private Button executeButton;
    private Button leaveButton;
    private TextView statusView;
    private EditText movesET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_game);

        Bundle extras = getIntent().getExtras();
        // TODO: THESE VALUES MAY BE RETRIEVED FROM DATABASE AS A SAFER PRACTICE, INSTEAD OF GETTING THEM FROM THE PREVIOUS ACTIVITY
        // TODO: if extras is null, or any of the keys are not found, send error message to screen
        if (extras != null) {
            username = extras.getString("Username");
        }

        // Layout references
        executeButton = findViewById(R.id.executeButton);
        leaveButton = findViewById(R.id.leaveButton);
        numberView = findViewById(R.id.numberInputText);
        statusView = findViewById(R.id.statusText);
        movesET = findViewById(R.id.movesET);

        movesET.setTag(movesET.getKeyListener());
        movesET.setKeyListener(null);
//        movesET.setKeyListener((KeyListener) movesET.getTag());

        // Create new Game
        game = new Game();

        Toast.makeText(getApplicationContext(), "Ya tenemos un numerito para que adivines", Toast.LENGTH_SHORT).show();

        // Test show
        this.statusView.setText(game.getNumberToGuess().toString());

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
                    if (game.isGameFinished()) {
                        GoToFinishedGameActivity();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                String moves = lastMove.toString() + "\n" + movesET.getText().toString();
                movesET.setText(moves);

//                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
            }
        });

        leaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game.leave();
                GoToFinishedGameActivity();
            }
        });
    }

    private void GoToFinishedGameActivity() {
        Intent finishedGameActivity = new Intent(GameActivity.this, FinishedGameActivity.class);
        finishedGameActivity.putExtra("NumberToGuess", game.getNumberToGuess());
        finishedGameActivity.putExtra("Username", username);
        finishedGameActivity.putExtra("Attempts", game.getNumberOfMoves());
        finishedGameActivity.putExtra("IsGameWon", game.isGameWon());

        startActivity(finishedGameActivity);
        finish();
    }
}
