package com.electiva_general.um.misnumeritos.business;


import java.util.Objects;

public class Score implements  Comparable{

    private String user;
    private int attempts;


    public Score(){}

    public Score(String _user, int _attempts){
        this.user = _user;
        this.attempts = _attempts;
    }

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

    public String toString() {
        String response = "En " + attempts;
        if(attempts == 1)
            response += " intento!!! :D";
        else if(attempts <= 4)
            response += " intentos! :)";
        else if(attempts <= 10)
            response += " intentos :|";
        else
            response += " intentos :(";
        response += "   "+user;
        return response;
    }



    @Override
    public int compareTo( Object o) {
        return this.attempts-((Score)o).attempts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Score score = (Score) o;
        return attempts == score.attempts &&
                Objects.equals(user, score.user);
    }

    @Override
    public int hashCode() {

        return Objects.hash(user, attempts);
    }
}
