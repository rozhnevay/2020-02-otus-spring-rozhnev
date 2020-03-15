package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
public class ShellService {

    private final ExamService examService;

    @ShellMethod(key = "login", value = "Введите ФИО")
    public String login(@ShellOption({"username", "u"}) String username) {
        return username;
    }
}
