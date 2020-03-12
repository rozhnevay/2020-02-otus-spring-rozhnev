package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.model.Exam;
import ru.otus.spring.model.Question;
import ru.otus.spring.model.Student;

import java.util.Locale;
import java.util.Scanner;


@RequiredArgsConstructor
@Service
public class ExamService {

    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";

    private final Exam exam;
    private final MessageSource messageSource;
    private final Locale locale;

    @Value("${threshold}")
    private int threshold;


    private void printMessage(String messageCode) {
        printMessage(messageCode, null);
    }

    private void printMessage(String messageCode, String[] params) {
        printMessage(messageCode, params, null);
    }

    private void printMessage(String messageCode, String[] params, String color) {
        String msg = messageSource.getMessage(messageCode, params, locale);
        if (color != null) {
            msg = color + msg;
        }
        System.out.println(msg);
    }

    public void runTest() {
        Student student = new Student();
        /* Welcome message */
        printMessage("hello");
        Scanner in = new Scanner(System.in);
        String name = in.nextLine();
        student.setName(name);

        /* Test questions loop */
        for (int i = 0; exam.getCountAnswered() < exam.getQuestionList().size(); i++) {
            if (i == exam.getQuestionList().size()) {
                i = 0;
            }

            Question question = exam.getQuestionList().get(i);
            if (question.isAnswered()){
                continue;
            }
            printMessage("question", new String[]{String.valueOf(i + 1), question.getText()});

            for (int a = 0; a < question.getAnswerList().size(); a++) {
                System.out.println((a + 1) + ". " + question.getAnswerList().get(a));
            }
            printMessage("answer.choose");

            boolean appliedAnswer = false;
            while (!appliedAnswer) {
                appliedAnswer = question.applyAnswer(in.nextInt());
                if (!appliedAnswer) {
                    printMessage("answer.bad.choose");
                } else {
                    if (question.isAnswered()) {
                        exam.incrementCountAnswered();
                    }
                    if (question.isCorrect()) {
                        exam.incrementCountCorrect();
                    }
                }
            }
        }
        /* Test results */
        String consoleColor = ANSI_RED;
        if ((exam.getCountCorrect() / exam.getCountAnswered()) * 100 > threshold) {
            consoleColor = ANSI_GREEN;
        }
        printMessage("answer.result", new String[]{String.valueOf(exam.getCountCorrect()), String.valueOf(exam.getCountAnswered())}, consoleColor);
    }
}
