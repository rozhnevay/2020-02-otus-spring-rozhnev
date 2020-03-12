package ru.otus.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class MessageSourceConfig {
    @Bean
    public Locale currentLocale(@Value("${locale}") String localeProp) {
        return new Locale(localeProp);
    }
}
