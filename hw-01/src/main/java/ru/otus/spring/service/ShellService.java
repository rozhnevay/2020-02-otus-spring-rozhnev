package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
@Slf4j
public class ShellService {

    private final ExamService examService;
    private String name;

    @ShellMethod(key = "login", value = "Введите ФИО")
    public String login(@ShellOption({"username", "u"}) String username) {
        name = username;
        return "Добро пожаловать. Введите команду start для начала экзамена";
    }

    @ShellMethod(value = "Start", key = {"s", "start"})
    @ShellMethodAvailability(value = "isStartCommandAvailable")
    public String start() {
        examService.runTest(name);
        return "Экзамен начат";
    }

    private Availability isStartCommandAvailable() {
        return name == null ? Availability.unavailable("Сначала login") : Availability.available();
    }
}
