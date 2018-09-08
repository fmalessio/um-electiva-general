package com.electiva_general.um.misnumeritos.business;

import java.util.ArrayList;

public final class Game {

    // Game status
    private static final byte PLAYING = 1;
    private static final byte ABORTED = 2;
    private static final byte FINISHED = 3;

    // Business rules
    private static final int MAX_ATTEMPTS = 10;
    private static final int MAXIMUM_NUMBER = 1023;
    private static final int MINIMUM_NUMBER = 9876;
    private static final int NUMBERS_LENGTH = 4;

    private Move[] moves;
    private int attempt = 0;
    private byte state;

    private ArrayList<String> numberToGuess;

    public Game() {
        this.state = Game.PLAYING;
        this.moves = new Move[MAX_ATTEMPTS];
        setRandomNumber();
    }

    private void setRandomNumber() {
        int numberToGuess = getRandomNumber();
        this.numberToGuess = numberToStringList(Integer.toString(numberToGuess));
    }

    private int getRandomNumber() {
        int randomNumber = MINIMUM_NUMBER + (int) (Math.random() * ((MAXIMUM_NUMBER - MINIMUM_NUMBER) + 1));
        while (!this.isAValidRandomNumber(randomNumber)) {
            randomNumber = MINIMUM_NUMBER + (int) (Math.random() * ((MAXIMUM_NUMBER - MINIMUM_NUMBER) + 1));
        }
        return randomNumber;
    }

    private boolean isAValidRandomNumber(int randomNumber) {
        this.numberToGuess = numberToStringList(Integer.toString(randomNumber));

        for (int i = 0; i < NUMBERS_LENGTH; i++) {
            for (int j = i + 1; j < NUMBERS_LENGTH; j++) {
                if (numberToGuess.get(i).equals(numberToGuess.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    public Move doNewMove(String playedNumber) throws Exception {
        ArrayList<String> playedNumberList = numberToStringList(playedNumber);

        if (state != PLAYING) {
            throw new Exception("PARTIDA FINALIZADA");
            // TODO: delete the throws and go to the finished activity
        }

        Move output = new Move(this.numberToGuess, playedNumberList);
        moves[attempt] = output;

        attempt++;

        if (attempt == MAX_ATTEMPTS || isGameWon())
            state = Game.FINISHED;

        return output;
    }

    private ArrayList<String> numberToStringList(String number) {
        // Split digits
        char[] digits = number.toCharArray();

        // Convert char[] to ArrayList<String>
        ArrayList<String> output = new ArrayList();
        for (char digit : digits) {
            String sChar = Character.toString(digit);
            output.add(sChar);
        }

        return output;
    }

    public ArrayList<String> getNumberToGuess() {
        return numberToGuess;
    }

    private boolean isGameWon() {
        // TODO: put logic to Won the game
        return false;
    }

    public Move lastMove() {
        return moves[attempt];
    }

    public Move[] getMoves() {
        return moves;
    }

    // e) Abandonar el juego ("Me doy")
    public void leave() {
        state = Game.ABORTED;
    }

}
