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
        for (String playerNumber : playerNumberList) {
            for (String randomNumber : numberToGuess) {
                if (playerNumber.equals(randomNumber)) {
                    if (playerNumberList.indexOf(randomNumber) == numberToGuess.indexOf(randomNumber)) {
                        assertedNumberAndIndex++;
                    } else {
                        assertedNumber++;
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Resultado{" +
                "Número jugado =" + playedNumber +
                ", Cantidad Bien =" + assertedNumberAndIndex +
                ", Cantidad Regular =" + assertedNumber +
                '}';
    }

    public int getAssertedNumberAndIndex() {
        return assertedNumberAndIndex;
    }

    public int getAssertedNumber() {
        return assertedNumber;
    }
}
