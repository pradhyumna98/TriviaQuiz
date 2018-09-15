package com.example.pradhyumna.triviaquiz.model;

/**
 * Created by pradhyumna on 6/30/2018.
 */

public class Ranking {

    private String username;
    private long score;

    public Ranking(String username, long score) {
        this.username = username;
        this.score = score;
    }

    public Ranking() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }
}
