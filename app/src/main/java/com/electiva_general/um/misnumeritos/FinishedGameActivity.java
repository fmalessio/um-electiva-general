package com.electiva_general.um.misnumeritos;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.electiva_general.um.misnumeritos.business.Game;
import com.electiva_general.um.misnumeritos.business.Score;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;


public class FinishedGameActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dref = database.getReference();
    private boolean insertNode;
    private DataSnapshot victimNode;

    ListView listview;
    ArrayList<Score> topTenList = new ArrayList<>();
    private ArrayList<String> numberToGuess;
    private String username;
    private int attempts;
    private boolean isGameWon;

    private TextView tvTitle;
    private TextView tvMessage;
    private TextView tvNumber;
    private TextView tvAttempts;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_finished_game);

        Bundle extras = getIntent().getExtras();

        tvTitle = (TextView) findViewById(R.id.tvAFG_title);
        tvMessage = (TextView) findViewById(R.id.tvAFG_message);
        tvNumber = (TextView) findViewById(R.id.tvAFG_number);
        tvAttempts = (TextView) findViewById(R.id.tvAFG_attempts);

        listview = (ListView) findViewById(R.id.listView_finished_score);


        // TODO: THESE VALUES MAY BE RETRIEVED FROM DATABASE AS A SAFER PRACTICE, INSTEAD OF GETTING THEM FROM THE PREVIOUS ACTIVITY
        // TODO: if extras is null, or any of the keys are not found, send error message to screen
        if (extras != null) {
            numberToGuess = extras.getStringArrayList("NumberToGuess");
            username = extras.getString("Username");
            attempts = extras.getInt("Attempts");
            isGameWon = extras.getBoolean("IsGameWon");
        }


        // if isGameWon == false, the player left the game; otherwise, the game was won
        if (isGameWon) {
            insertNode = true;
            victimNode = null;

            Score score = new Score(username, attempts);
            updateDatabase(score);
        }

        addListeners();
    }

    private void addListeners() {
    }

    @Override
    protected void onStart() {
        super.onStart();

        loadUIMessages();
        loadTopTen();
    }


    private void loadTopTen() {

        final ArrayAdapter<Score> adapter = new ArrayAdapter<Score>(this, android.R.layout.simple_list_item_1, topTenList);
        listview.setAdapter(adapter);

        dref.child("scores").orderByChild("attempts").limitToFirst(Game.SCORES_QTY)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Score sn = dataSnapshot.getValue(Score.class);
                        topTenList.add(sn);
                        Collections.sort(topTenList);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        Score sn = dataSnapshot.getValue(Score.class);
                        topTenList.remove(sn);
                        Collections.sort(topTenList);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    public void loadUIMessages() {
        String title;
        String message;
        String attemptsQty = "Intentos: " + attempts;
        String playedNumber = "Número jugado: ";

        for (String s : numberToGuess) {
            playedNumber += s;
        }

        if (isGameWon) {
            title = "GANASTE!";
            if (attempts == 1) {
                title = "GENIO/A!!!";
                message = "En un solo intento!";
            } else if (attempts <= 4) {
                message = "Adivinaste el número";
            } else if (attempts <= 10) {
                message = "Probá hacerlo en menos intentos";
            } else {
                message = "Pero son muchos intentos...";
            }
        } else {
            title = "Pecho frío!";
            message = "No te animaste a adivinarlo...";
        }

        tvTitle.setText(title);
        tvMessage.setText(message);
        tvAttempts.setText(attemptsQty);
        tvNumber.setText(playedNumber);
    }

    private void updateDatabase(final Score score) {

        Query query = dref.child("scores").orderByKey();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange( DataSnapshot dataSnapshot) {
                 if (dataSnapshot.getChildrenCount() >= Game.SCORES_QTY) {
                     insertNode = false;
                     for (DataSnapshot dsScore : dataSnapshot.getChildren()) {
                         if (victimNode == null || victimNode.getValue(Score.class).getAttempts() <= dsScore.getValue(Score.class).getAttempts())
                             victimNode = dsScore;
                     }

                     if (victimNode.getValue(Score.class).getAttempts() > score.getAttempts()) {
                         dref.child("scores").push().setValue(score);
                         victimNode.getRef().removeValue();
                     }
                 }

                 if (insertNode) {
                     dref.child("scores").push().setValue(score);
                 } else {
                     insertNode = true;
                 }
             }

             @Override
             public void onCancelled( DatabaseError databaseError) {
             }
         }
        );
    }


}
