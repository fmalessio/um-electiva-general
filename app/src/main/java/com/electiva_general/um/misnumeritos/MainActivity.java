package com.electiva_general.um.misnumeritos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // Attributes
    int minNumberRandom = 1023;
    int maxNumberRandom = 9876;
    private ArrayList<String> randomNumberInList;
    // From view
    private TextView numberView;
    private Button executeButton;
    private TextView statusView;

    static final int NUMBERS_LENGTH = 4;

    static final int ASSERTED_NUMBER_AND_INDEX = 0;
    static final int ASSERTED_NUMBER = 1;
    static final int NOT_ASSERTED = -1;

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
        int randomNumber = getRandomNumber();
        this.randomNumberInList = numberToStringList(randomNumber);

        Toast.makeText(getApplicationContext(), "Ya tenemos un numerito para que adivines", Toast.LENGTH_SHORT).show();
        // Test show TODO: delete this row
        this.statusView.setText(randomNumberInList.toString());
    }

    private int getRandomNumber() {
        int randomNumber = minNumberRandom + (int)(Math.random() * ((maxNumberRandom - minNumberRandom) + 1));
        while (!this.isAValidRandomNumber(randomNumber)){
            randomNumber =  minNumberRandom + (int)(Math.random() * ((maxNumberRandom - minNumberRandom) + 1));
        }
        return randomNumber;
    }

    private boolean isAValidRandomNumber(int randomNumber) {
        this.randomNumberInList = numberToStringList(randomNumber);

        for(int i=0; i < NUMBERS_LENGTH; i++){
            for(int j=i+1; j < NUMBERS_LENGTH; j++){
                if(randomNumberInList.get(i).equals(randomNumberInList.get(j))){
                    return false;
                }
            }
        }
        return true;
    }

    private void addListeners() {
        executeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String playerNumber = numberView.getText().toString();
                ArrayList<String> playerNumberList = numberToStringList(new Integer(playerNumber));

                Map<Integer, Integer> assertions = validatePlayerNumber(playerNumberList, randomNumberInList);

                String response = "Cantidad de numeros bien:" + assertions.get(ASSERTED_NUMBER_AND_INDEX).toString() +
                        " .Cantidad de numeros Regular: " + assertions.get(ASSERTED_NUMBER).toString();
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Map<Integer, Integer> validatePlayerNumber(ArrayList<String> playerNumberList, ArrayList<String> randomNumberInList) {
        Map<Integer, Integer> assertions = new HashMap<>();
        assertions.put(ASSERTED_NUMBER_AND_INDEX, 0);
        assertions.put(ASSERTED_NUMBER, 0);

        for(String playerNumber : playerNumberList){
            for(String randomNumber : randomNumberInList){
                if(playerNumber.equals(randomNumber)){
                    if(playerNumberList.indexOf(randomNumber) == randomNumberInList.indexOf(randomNumber)){
                        assertions.put(ASSERTED_NUMBER_AND_INDEX, assertions.get(ASSERTED_NUMBER_AND_INDEX) + 1);
                    } else {
                        assertions.put(ASSERTED_NUMBER, assertions.get(ASSERTED_NUMBER) + 1);
                    }
                }
            }
        }
        return assertions;
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
