package com.electiva_general.um.misnumeritos.business;


public class Score {

    //private String key;
    private String user;
    private int attempts;

    public Score(){}

    public Score(String _user, int _attempts){
        //this.key = _key;
        this.user = _user;
        this.attempts = _attempts;
    }


    /*
    public String getKey() {
        return key;
    }
    public void setKey(String _key){
        key =_key;
    }
    //*/

    public String getUser() {
        return user;
    }
    public void setUser(String _user){
        this.user = _user;
    }

    public int getAttempts(){
        return attempts;
    }
    public void setAttempts(int _attempts){
        this.attempts = _attempts;
    }
}
