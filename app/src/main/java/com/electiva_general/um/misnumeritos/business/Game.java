package com.electiva_general.um.misnumeritos.business;

public final class Game {

    //ESTADOS EN LOS QUE PUEDE ESTAR EL JUEGO
    private static final byte PLAYING  = 1; //En juego
    private static final byte GAVE_UP  = 2; //Abandonado
    private static final byte FINISHED = 3; //Finalizado
    private static final int MAX_ATTEMPTS = 10; //Cantidad máxima de intentos


    private Number numberToGuess = Number.Generate();
    private Move[] moves = new Move[MAX_ATTEMPTS];
    private int attempt = 0;
    private byte state = Game.PLAYING;


    public Move doNewMove(final Number playedNumber) throws Exception {
        if(state != PLAYING)
            throw new Exception("PARTIDA FINALIZADA");

        Move output = new Move(numberToGuess, playedNumber);
        moves[attempt] = output;

        attempt++;

        if(attempt == MAX_ATTEMPTS || output.isGameWon())
            state = Game.FINISHED;

        return output;
    }

    public Move lastMove(){
        return moves[attempt];
    }


    public Move[] getMoves(){
        return moves;
    }

    // e) Abandonar el juego ("Me doy")
    public void leave(){
        state = Game.GAVE_UP;
    }


}
