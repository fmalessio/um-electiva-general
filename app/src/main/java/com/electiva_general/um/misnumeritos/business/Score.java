package com.electiva_general.um.misnumeritos.business;


public class Score {

    private String userId;
    private int attempts;

    public Score(){}

    public Score(String _userId, int _attempts){
        this.userId = _userId;
        this.attempts = _attempts;
    }


    public String getUserId() {
        return userId;
    }
    public void setUserId(String _userId){
        this.userId = _userId;
    }

    public int getAttempts(){
        return attempts;
    }
    public void setAttempts(int _attempts){
        this.attempts = _attempts;
    }
}
