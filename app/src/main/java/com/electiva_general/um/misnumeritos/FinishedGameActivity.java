package com.electiva_general.um.misnumeritos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.electiva_general.um.misnumeritos.business.Score;
import com.electiva_general.um.misnumeritos.business.ScoreNode;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class FinishedGameActivity extends AppCompatActivity {

    private ArrayList<String> numberToGuess;
    private String username;
    private int attempts;
    private boolean isGameWon;

    private TextView tvTitle;
    private TextView tvMessage;
    private TextView tvNumber;
    private TextView tvAttempts;

    // Para recuperar los datos del top ten
    DatabaseReference dref;
    ListView listview;
    ArrayList<String> list=new ArrayList<>();
    ArrayList<ScoreNode> topTenList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


        // TODO: load winner or looser message on screen according to variable "isGameWon"
        // if isGameWon == false, the player left the game; otherwise, the game was won
        // TODO: show on screen the number that the player had to guess
        // TODO: show on screen the number of moves (attempts) that the player made before leaving or winning
        if(isGameWon){

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference scoresRef = database.getReference();

            // RECORDAR: TRABAJA CON ESTRUCTURA DE ARBOL, NO COMO DB RELACIONAL !!!
            // Realiza el insert de un ID en el nodo "scores", y recupera en key el valor del ID insertado
            String key = scoresRef.child("scores").push().getKey();
            // Inserta los datos de la clase Score dentro del nodo "key" creado

            final ScoreNode scoreNode = new ScoreNode(key, new Score(username,attempts));
            scoresRef.child("scores/"+key).setValue(scoreNode);

            Toast.makeText(this, scoreNode.getScore().getUser() + "-"+scoreNode.getScore().getAttempts(), Toast.LENGTH_SHORT).show();


        }



        addListeners();
    }

    private void addListeners() {
        // TODO: add button to start a new game (navigate to game activity)
        // TODO: add button to see scores (navigate to scores activity)
    }

    @Override
    protected void onStart() {
        super.onStart();

        loadUIMessages();
        loadTopTen();
        /*
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                loadTopTen();
            }
        },1000);
        //*/
    }


    private void loadTopTen(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference dref = database.getReference();
        final ArrayAdapter<ScoreNode> adapter=new ArrayAdapter<ScoreNode>(this,android.R.layout.simple_list_item_1,topTenList);
        listview.setAdapter(adapter);

        dref.child("scores").orderByChild("score/attempts").limitToFirst(10)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        ScoreNode sn = dataSnapshot.getValue(ScoreNode.class);
                        if (topTenList.size() < 10 ||
                                sn.getScore().getAttempts() < ((ScoreNode)topTenList.get(9)).getScore().getAttempts()) {
                            topTenList.add(sn);
                        }
                        adapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        ScoreNode sn = dataSnapshot.getValue(ScoreNode.class);
                        int i=0;
                        while (i < topTenList.size()){
                            if(topTenList.get(i).getKey() == sn.getKey()) {
                                topTenList.get(i).setKey(sn.getKey());
                                i=topTenList.size();
                            }
                            else
                                i++;
                        }
                        adapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        ScoreNode sn = dataSnapshot.getValue(ScoreNode.class);
                        topTenList.remove(sn);
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

    public void loadUIMessages(){
        String title;
        String message;

        if(isGameWon){
            title = "GANASTE!";
            if(attempts == 1) {
             title = "GENIO/A!!!";
                message = "En un solo intento!";
            }
            else if(attempts < 4) {
                message = "Adivinaste el número";
            }
            else if(attempts < 10){
                message = "Probá hacerlo en menos intentos";
            }
            else{
                message = "Pero son muchos intentos...";
            }
        }
        else{
            title = "Pecho frío!";
            message = "No te animaste a adivinarlo...";
        }
        tvTitle.setText(title);
        tvMessage.setText(message);
        tvNumber.setText("Número: "+numberToGuess);
        tvAttempts.setText("Intentos: "+attempts);
    }





}
