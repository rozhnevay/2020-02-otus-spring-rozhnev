package ru.otus.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.spring.model.Exam;

import java.util.Properties;

import static org.junit.Assert.assertEquals;

@DisplayName("Utils testing")
@SpringBootTest
@ActiveProfiles("test")
public class UtilsTest {
    @Autowired
    private Exam exam;


    @TestConfiguration
    static class TestConfig {
        private final String testFile = "test.csv";

        @Bean
        @Primary
        public MessageSource messageSource() {
            ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
            Properties props = new Properties();
            props.put("file.path", testFile);
            ms.setCommonMessages(props);
            return ms;
        }

    }

    @DisplayName("Utils loadFromCSV method testing")
    @Test
    void testLoading() {
        assertEquals(exam.getQuestionList().size(), 10);
    }
}
