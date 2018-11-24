package com.electiva_general.um.misnumeritos.business;

import java.util.ArrayList;

public final class Move {

    private ArrayList<String> playedNumber;
    private int assertedNumberAndIndex = 0;
    private int assertedNumber = 0;

    public Move(ArrayList<String> numberToGuess, ArrayList<String> playedNumber) {
        this.playedNumber = playedNumber;
        validatePlayerNumber(numberToGuess, playedNumber);
    }

    private void validatePlayerNumber(ArrayList<String> numberToGuess, ArrayList<String> playerNumberList) {

        for (int i = 0; i < numberToGuess.size(); i++)
        {
            if (numberToGuess.get(i).equals(playedNumber.get(i)))
            {
                assertedNumberAndIndex++;
            }
            else if (numberToGuess.contains(playedNumber.get(i)))
            {
                assertedNumber++;
            }
        }
    }

    @Override
    public String toString() {
        return "Nº: " + ShowPlayedNumber() +
                " - Bien=" + assertedNumberAndIndex +
                ", Regular=" + assertedNumber;
    }

    public int getAssertedNumberAndIndex() {
        return assertedNumberAndIndex;
    }

    public int getAssertedNumber() {
        return assertedNumber;
    }

    public String ShowPlayedNumber() {
        String number = "";

        for(String s: playedNumber)
        {
            number = number.concat(s);
        }

        return number;
    }
}
