package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import ru.otus.spring.model.Question;
import ru.otus.spring.model.Student;
import ru.otus.spring.model.Exam;

import java.util.Scanner;


@RequiredArgsConstructor
public class ExamService {
    private static final String HELLO_MSG_PATTERN = "Please, enter your last and first name";
    private static final String QUESTION_PATTERN = "Question %1$s. %2$s";
    private static final String ANSWER_CHOOSE_MSG_PATTERN = "Enter number of answer or 0 for skip";
    private static final String ANSWER_BAD_CHOOSE_MSG_PATTERN = "Please, enter correct number of answer or 0 for skip";
    private static final String RESULT_PATTERN = "Your result: %1$s/%2$s";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";

    private final Exam exam;


    public void runTest() {
        Student student = new Student();

        /* Welcome message */
        System.out.println(HELLO_MSG_PATTERN);
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
            System.out.println(String.format(QUESTION_PATTERN, i + 1, question.getText()));

            for (int a = 0; a < question.getAnswerList().size(); a++) {
                System.out.println((a + 1) + ". " + question.getAnswerList().get(a));
            }
            System.out.println(ANSWER_CHOOSE_MSG_PATTERN);

            boolean appliedAnswer = false;
            while (!appliedAnswer) {
                appliedAnswer = question.applyAnswer(in.nextInt());
                if (!appliedAnswer) {
                    System.out.println(ANSWER_BAD_CHOOSE_MSG_PATTERN);
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
        if ((exam.getCountCorrect()/ exam.getCountAnswered())*100 > 80) {
            consoleColor = ANSI_GREEN;
        }
        System.out.println(consoleColor + String.format(RESULT_PATTERN, exam.getCountCorrect(), exam.getCountAnswered()));
    }
}
