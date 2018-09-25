package com.electiva_general.um.misnumeritos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

public class ScoreActivity extends AppCompatActivity {


    DatabaseReference dref;
    ListView listview;
    ArrayList<String> list=new ArrayList<>();
    ArrayList<ScoreNode> topTenList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        listview = (ListView) findViewById(R.id.listView_score);

        loadTopTen();

        /*
        int att = (int)(Math.random()*100);
        final Score score = new Score("Pepe Rodriguez",att);
        Toast.makeText(this, score.getUser() + "-"+score.getAttempts(), Toast.LENGTH_SHORT).show();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                saveScore(score);
            }
        }, 3000);
        //*/

        addListeners();
    }

    private void addListeners() {
        // TODO: add button to start a new game (navigate to game activity)
        // TODO: add button to see scores (navigate to scores activity)
    }

    // TODO: para borrar, solo prueba
    private void saveScore(Score score){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference dref = database.getReference();
        // RECORDAR: TRABAJA CON ESTRUCTURA DE ARBOL, NO COMO DB RELACIONAL !!!
        // Realiza el insert de un ID en el nodo "scores", y recupera en key el valor del ID insertado
        String key = dref.child("scores").push().getKey();
        // Inserta los datos de la clase Score dentro del nodo "key" creado
        ScoreNode scoreNode = new ScoreNode(key, score);
        dref.child("scores/"+key).setValue(scoreNode);
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
                        topTenList.add(sn);
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
}
