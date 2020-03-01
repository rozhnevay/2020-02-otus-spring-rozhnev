package ru.otus.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.spring.model.Exam;
import ru.otus.spring.model.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Utils testing")
public class UtilsTest {

    @DisplayName("Utils loadFromCSV method testing")
    @Test
    void testLoading() {
        Exam exam = new Exam("questions_test.csv");
        assertEquals(exam.getQuestionList().size(), 5);
    }
}
