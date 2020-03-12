package ru.otus.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.spring.config.MessageSourceConfig;
import ru.otus.spring.model.Exam;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Utils testing")
public class UtilsTest {

    @DisplayName("Utils loadFromCSV method testing")
    @Test
    void testLoading() {
        MessageSourceConfig mc = new MessageSourceConfig();
        Exam exam = new Exam(mc.currentLocale("ru_RU"), mc.messageSource());
        assertEquals(exam.getQuestionList().size(), 5);
    }
}
