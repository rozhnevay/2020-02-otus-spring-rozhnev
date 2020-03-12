package ru.otus.spring.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Getter
@Setter
@Service
public class Exam {
    private List<Question> questionList;
    private int countAnswered = 0;
    private int countCorrect = 0;
    public void incrementCountAnswered() {
        this.countAnswered++;
    }
    public void incrementCountCorrect() {
        this.countCorrect++;
    }

    private final Locale locale;
    private final MessageSource messageSource;

    public Exam(Locale locale, MessageSource messageSource) {
        this.locale = locale;
        this.messageSource = messageSource;
        this.loadQuestionsFromCSV(messageSource.getMessage("file.path", null, locale));
    }

    private void loadQuestionsFromCSV (String csvFile) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        if (csvFile == null || classloader.getResourceAsStream(csvFile) == null) {
            throw new RuntimeException("Cannot open file with questions");
        }
        List<Question> questions = new ArrayList<>();
        String line;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(classloader.getResourceAsStream(csvFile)))) {
            for (int i = 0; (line = br.readLine()) != null; i++) {
                if (i == 0) {
                    continue;
                }
                String[] arr = line.split(";");
                List<String> listAnswers = new ArrayList<>();
                for (int q = 1; q < 4; q++) {
                    if (arr[q] != null && arr[q].length() > 0) {
                        listAnswers.add(arr[q]);
                    }
                }
                if (listAnswers.size() == 0) {
                    throw new RuntimeException("No answers in question " + i);
                }
                int answer = Integer.parseInt(arr[4]);
                Question question = new Question(arr[0], listAnswers, answer);
                questions.add(question);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (questions.size() == 0) {
            throw new RuntimeException("No questions in file!");
        }
        this.questionList = questions;
    }
}
