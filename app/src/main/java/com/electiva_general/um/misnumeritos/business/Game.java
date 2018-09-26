package com.electiva_general.um.misnumeritos.business;

import java.util.ArrayList;

public final class Game {

    // Game status
    private static final byte PLAYING = 1;
    private static final byte ABORTED = 2;
    private static final byte FINISHED = 3;

    // Business rules
    private static final int MAXIMUM_NUMBER = 1023;
    private static final int MINIMUM_NUMBER = 9876;
    private static final int NUMBERS_LENGTH = 4;

    private ArrayList<Move> moves;
    private byte state;

    private ArrayList<String> numberToGuess;

    public Game() {
        this.state = Game.PLAYING;
        this.moves = new ArrayList<>();
        setRandomNumber();

        // TODO: save new game on database
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
        moves.add(output);

        if (isWinnerMove()) {
            state = Game.FINISHED;
        }

        // TODO: save on database: the new move, the number of attempts and the new game state if applicable

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

    private boolean isWinnerMove() {
        return lastMove().getAssertedNumberAndIndex() == Game.NUMBERS_LENGTH;
    }

    public Move lastMove() {
        return moves.get(moves.size() - 1);
    }

    public ArrayList<Move> getMoves() {
        return moves;
    }

    // e) Leave the game ("Me doy")
    public void leave() {
        state = Game.ABORTED;

        // TODO: save new game state on database
    }

    public boolean isGameFinished(){
        //return (state != Game.ABORTED && state != Game.FINISHED);
        return (state == Game.ABORTED || state == Game.FINISHED);
    }

    public int getNumberOfMoves(){
        return moves.size();
    }

    public boolean isGameWon(){
        return state == Game.FINISHED;
    }

}
