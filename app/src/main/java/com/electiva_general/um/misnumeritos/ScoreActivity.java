package com.electiva_general.um.misnumeritos;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.Collections;

public class ScoreActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dref = database.getReference();

    ListView listview;
    ArrayList<Score> topTenList = new ArrayList<>();

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
    }


    private void loadTopTen() {

        final ArrayAdapter<Score> adapter = new ArrayAdapter<Score>(this, android.R.layout.simple_list_item_1, topTenList){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the Item from ListView
                View view = super.getView(position, convertView, parent);

                // Initialize a TextView for ListView each Item
                TextView tv = (TextView) view.findViewById(android.R.id.text1);

                // Set the text color of TextView (ListView Item)
                tv.setTextColor(Color.rgb(119, 51, 255));
                tv.setTypeface(Typeface.DEFAULT_BOLD);
                tv.setShadowLayer(0.5f, 2, 2, Color.BLACK);

                // Generate ListView Item using TextView
                return view;
            }
        };
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
}
