package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.enums.ConsoleLevelEnum;
import ru.otus.spring.model.Exam;
import ru.otus.spring.model.Question;
import ru.otus.spring.model.Student;

import java.util.Locale;
import java.util.Scanner;


@RequiredArgsConstructor
@Service
@Slf4j
public class ExamService {

    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";

    private final Exam exam;
    private final MessageSource messageSource;
    private final Locale locale;


    private int threshold;

    public ExamService(Exam exam, MessageSource messageSource, Locale locale, @Value("${threshold}") int threshold, @Value("${load_file_only:false}") boolean loadOnly) {
        this.exam = exam;
        this.threshold = threshold;
        this.messageSource = messageSource;
        this.locale = locale;
        this.exam.loadQuestionsFromCSV(messageSource.getMessage("file.path", null, locale));

        if (!loadOnly) {
            this.runTest();
        }
    }



    private void printMessage(String messageCode) {
        printMessage(messageCode, null);
    }

    private void printMessage(String messageCode, String[] params) {
        printMessage(messageCode, params, ConsoleLevelEnum.WARNING);
    }

    private void printMessage(String messageCode, String[] params, ConsoleLevelEnum level) {
        String msg = messageSource.getMessage(messageCode, params, locale);
        switch (level) {
            case INFO:
                log.info(msg);
                break;
            case ERROR:
                log.error(msg);
                break;
            case WARNING:
                log.warn(msg);
                break;
        }
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
                log.warn((a + 1) + ". " + question.getAnswerList().get(a));
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
        int result = 0;
        float cntCorrect = exam.getCountCorrect();
        float cntAnswered = exam.getCountAnswered();
        if ((cntCorrect / cntAnswered) * 100 > threshold) {
            result = 1;
        }
        printMessage("answer.result", new String[]{String.valueOf(exam.getCountCorrect()), String.valueOf(exam.getCountAnswered())}, result == 1 ? ConsoleLevelEnum.INFO : ConsoleLevelEnum.ERROR);
    }
}
