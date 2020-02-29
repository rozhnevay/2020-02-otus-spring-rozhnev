package ru.otus.spring.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class Test {
    private List<Question> questionList;
    private final String csvFile;
    private int countAnswered = 0;
    private int countCorrect = 0;
    public void incrementCountAnswered() {
        this.countAnswered++;
    }
    public void incrementCountCorrect() {
        this.countCorrect++;
    }
}
