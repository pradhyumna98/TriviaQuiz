package com.example.pradhyumna.triviaquiz.model;

/**
 * Created by pradhyumna on 6/25/2018.
 */

public class Question_Score {

    private String ques_score;
    private String user;
    private String score;

    public Question_Score() {
    }

    public Question_Score(String ques_score, String user, String score) {
        this.ques_score = ques_score;
        this.user = user;
        this.score = score;
    }

    public String getQues_score() {
        return ques_score;
    }

    public void setQues_score(String ques_score) {
        this.ques_score = ques_score;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
