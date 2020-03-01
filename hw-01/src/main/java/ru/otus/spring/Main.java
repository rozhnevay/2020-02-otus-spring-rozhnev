package ru.otus.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.service.ExamService;

public class Main {
    private static final String CONTEXT_FILE = "/spring-context.xml";

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(CONTEXT_FILE);
        ExamService examService = context.getBean(ExamService.class);
        examService.runTest();
    }
}
