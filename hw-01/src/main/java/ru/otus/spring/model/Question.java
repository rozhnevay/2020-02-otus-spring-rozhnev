package ru.otus.spring.model;

import lombok.Getter;
import lombok.Setter;
import lombok.RequiredArgsConstructor;

import java.util.List;


@Getter
@Setter
@RequiredArgsConstructor
public class Question {
    private final String text;
    private final List<String> answerList;
    private final int answer;
    private boolean correct = false;
    private boolean answered = false;

    public boolean applyAnswer(int numAnswer) {
        if (numAnswer == 0) {
            return true;
        }
        if (numAnswer > 0 && numAnswer <= this.getAnswerList().size()) {
            if (this.answer == numAnswer) {
                this.correct = true;
            }
            this.answered = true;
            return true;
        }
        return false;
    }

}
