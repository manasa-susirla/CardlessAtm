package com.example.lenovo.cardlessatm;

/**
 * Created by HP on 10-05-2018.
 */



/**
 * Created by HP on 09-05-2018.
 */

public class Security {

    String question,answer;

    public Security(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }
}
