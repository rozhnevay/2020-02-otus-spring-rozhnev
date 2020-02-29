package ru.otus.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.model.Question;
import ru.otus.spring.model.Student;
import ru.otus.spring.model.Test;
import ru.otus.spring.service.Utils;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String HELLO_MSG_PATTERN = "Please, enter your last and first name";
    private static final String QUESTION_PATTERN = "Question %1$s. %2$s";
    private static final String ANSWER_CHOOSE_MSG_PATTERN = "Enter number of answer or 0 for skip";
    private static final String ANSWER_BAD_CHOOSE_MSG_PATTERN = "Please, enter correct number of answer or 0 for skip";
    private static final String RESULT_PATTERN = "Your result: %1$s/%2$s";
    private static final String CONTEXT_FILE = "/spring-context.xml";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";


    public static void main(String[] args) {
        /* Context loading */
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(CONTEXT_FILE);
        Student student = context.getBean(Student.class);
        Test test = context.getBean(Test.class);
        Utils utils = context.getBean(Utils.class);
        /*Questions loading*/
        List<Question> listQuestions = utils.loadQuestionsFromCSV(test.getCsvFile());
        test.setQuestionList(listQuestions);

        /* Welcome message */
        System.out.println(HELLO_MSG_PATTERN);
        Scanner in = new Scanner(System.in);
        String name = in.nextLine();
        student.setName(name);

        /* Test questions loop */
        for (int i = 0; test.getCountAnswered() < test.getQuestionList().size(); i++) {
            if (i == test.getQuestionList().size()) {
                i = 0;
            }

            Question question = test.getQuestionList().get(i);
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
                        test.incrementCountAnswered();
                    }
                    if (question.isCorrect()) {
                       test.incrementCountCorrect();
                    }
                }
            }
        }
        /* Test results */
        String consoleColor = ANSI_RED;
        if ((test.getCountCorrect()/test.getCountAnswered())*100 > 80) {
            consoleColor = ANSI_GREEN;
        }
        System.out.println(consoleColor + String.format(RESULT_PATTERN, test.getCountCorrect(), test.getCountAnswered()));
    }
}
