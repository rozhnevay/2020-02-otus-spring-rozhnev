package ru.otus.spring;


import java.util.Collection;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.spring.domain.Deal;
import ru.otus.spring.domain.Lead;

@MessagingGateway
public interface LeadProcessor {

    @Gateway(requestChannel = "leadChannel", replyChannel = "dealChannel", replyTimeout = 1000)
    Deal process(Lead lead);
}
