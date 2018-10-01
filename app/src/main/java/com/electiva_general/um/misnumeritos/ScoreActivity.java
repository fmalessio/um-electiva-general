package com.electiva_general.um.misnumeritos;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.electiva_general.um.misnumeritos.business.ScoreNode;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ScoreActivity extends AppCompatActivity {

    ListView listview;
    ArrayList<ScoreNode> topTenList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_score);

        listview = (ListView) findViewById(R.id.listView_score);

        loadTopTen();

        addListeners();
    }

    private void addListeners() {
        // TODO: add button to start a new game (navigate to game activity)
        // TODO: add button to see scores (navigate to scores activity)
    }


    private void loadTopTen() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference dref = database.getReference();
        final ArrayAdapter<ScoreNode> adapter = new ArrayAdapter<ScoreNode>(this, android.R.layout.simple_list_item_1, topTenList);
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
                        int i = 0;
                        while (i < topTenList.size()) {
                            if (topTenList.get(i).getKey() == sn.getKey()) {
                                topTenList.get(i).setKey(sn.getKey());
                                i = topTenList.size();
                            } else
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
