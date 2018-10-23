package com.electiva_general.um.misnumeritos.business;

import com.electiva_general.um.misnumeritos.business.Score;

    public final class ScoreNode {

    private String key;
    private Score score;

    public ScoreNode(){}

    public ScoreNode(String _key, Score _score){
        key = _key;
        score = _score;
    }


    //*
    public String getKey() {
        return key;
    }
    public void setKey(String _key){
        key =_key;
    }
    //*/

    public Score getScore() {
        return score;
    }
    public void setScore(Score _score){
        score = _score;
    }



    @Override
    public String toString() {
        String response = "En " + score.getAttempts();
        if(score.getAttempts() == 1)
            response += " intento!!! :D";
        else if(score.getAttempts() <= 4)
            response += " intentos! :)";
        else if(score.getAttempts() <= 10)
            response += " intentos :|";
        else
            response += " intentos :(";
        response += "   "+score.getUser();
        return response;
    }
}