package com.electiva_general.um.misnumeritos.business;

public final class Move {

    private Number playedNumber;
    private int assertedNumberAndIndex = 0;
    private int assertedNumber = 0;

    public Move(final Number numberToGuess,final Number playedNumber) {

        this.playedNumber = playedNumber;

        // Comparar el dígito 1 del número jugado respecto al número a adivinar
        if(playedNumber.getX1()==numberToGuess.getX1())
            assertedNumberAndIndex++;
        else if (playedNumber.getX1() == numberToGuess.getX2() || playedNumber.getX1() == numberToGuess.getX3() || playedNumber.getX1() == numberToGuess.getX4() )
            assertedNumber++;

        // Comparar el dígito 2 del número jugado respecto al número a adivinar
        if(playedNumber.getX2()==numberToGuess.getX2())
            assertedNumberAndIndex++;
        else if (playedNumber.getX2() == numberToGuess.getX1() || playedNumber.getX2() == numberToGuess.getX3() || playedNumber.getX2() == numberToGuess.getX4() )
            assertedNumber++;

        // Comparar el dígito 3 del número jugado respecto al número a adivinar
        if(playedNumber.getX3()==numberToGuess.getX3())
            assertedNumberAndIndex++;
        else if (playedNumber.getX3() == numberToGuess.getX2() || playedNumber.getX3() == numberToGuess.getX1() || playedNumber.getX3() == numberToGuess.getX4() )
            assertedNumber++;

        // Comparar el dígito 4 del número jugado respecto al número a adivinar
        if(playedNumber.getX4()==numberToGuess.getX4())
            assertedNumberAndIndex++;
        else if (playedNumber.getX4() == numberToGuess.getX2() || playedNumber.getX4() == numberToGuess.getX3() || playedNumber.getX4() == numberToGuess.getX1() )
            assertedNumber++;
    }

    public Number getPlayed() {
        return playedNumber;
    }

    public int getAssertedNumberAndIndex() {
        return assertedNumberAndIndex;
    }

    public int getAssertedNumber() {
        return assertedNumber;
    }


    public boolean isGameWon(){
        return assertedNumberAndIndex == Number.NUMBERS_LENGTH;
    }

    @Override
    public String toString() {
        return "Resultado{" +
                "Número jugado =" + playedNumber +
                ", Cantidad Bien =" + assertedNumberAndIndex +
                ", Cantidad Regular =" + assertedNumber +
                '}';
    }
}
