package e.flashcards;

import java.io.Serializable;


public class QuizCard implements Serializable {

    private String question ;
    private String answer;

    public String getQuestion(){
        return question;
    }

    public void setQuestion(String input) {
        question = input;
    }

    public String getAnswer(){
        return answer;
    }

    public void setAnswer(String input) {
        answer = input;
    }

}
