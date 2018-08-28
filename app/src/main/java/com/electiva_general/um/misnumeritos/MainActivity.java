package com.electiva_general.um.misnumeritos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Attributes
    int minNumberRandom = 1023;
    int maxNumberRandom = 9876;
    private ArrayList<String> randomNumberInList;
    // From view
    private TextView numberView;
    private Button executeButton;
    private TextView statusView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Layout references
        executeButton = findViewById(R.id.executeButton);
        numberView = findViewById(R.id.numberInputText);
        statusView = findViewById(R.id.statusText);

        setRandomNumber();

        // events
        addListeners();
    }

    private void setRandomNumber() {
        int randomNumber = minNumberRandom + (int)(Math.random() * ((maxNumberRandom - minNumberRandom) + 1));
        this.randomNumberInList = numberToStringList(randomNumber);

        Toast.makeText(getApplicationContext(), "Ya tenemos un numerito para que adivines", Toast.LENGTH_SHORT).show();
        // Test show TODO: delete this row
        this.statusView.setText(randomNumberInList.toString());
    }

    private void addListeners() {
        executeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String playerNumber = numberView.getText().toString();
                ArrayList<String> playerNumberList = numberToStringList(new Integer(playerNumber));

                // TODO: compare "playerNumberList" with "randomNumberInList"

                // TODO: Show result in new view element
                Toast.makeText(getApplicationContext(), playerNumberList.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private ArrayList<String> numberToStringList(int number) {
        // Split digits
        String sNumber = String.valueOf(number);
        char[] digits = sNumber.toCharArray();

        // Convert char[] to ArrayList<String>
        ArrayList<String> output = new ArrayList();
        for (char digit : digits) {
            String sChar = Character.toString(digit);
            output.add(sChar);
        }

        return output;
    }

}
