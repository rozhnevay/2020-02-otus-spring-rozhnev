package ru.otus.spring;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.messaging.PollableChannel;
import ru.otus.spring.domain.Deal;
import ru.otus.spring.domain.Lead;
import ru.otus.spring.domain.Prospect;

@IntegrationComponentScan
@ComponentScan
@EnableIntegration
@RequiredArgsConstructor
public class LeadProcessingApp {
  private static final String[] PRODUCTS = {"deposit", "credit", "insurance", "investment"};
  private static final String[] PROSPECT_NAMES = {"Ivan", "Vasiliy", "Nikolay"};
  private static final String[] PROSPECT_PHONES = {"+7911111111", "+79000000000", "+791165431111"};
  private static final String INITIAL_STATUS = "CALL_SCHEDULED";


  public static void main(String[] args) throws Exception {
    AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(LeadProcessingApp.class);
    LeadProcessor leadProcessor = ctx.getBean(LeadProcessor.class);
    while (true) {
      Thread.sleep(1000);

      Lead lead = generateLead();
      System.out.println("New lead: " + lead);
      Deal deal = leadProcessor.process(lead);
      if (deal != null) {
        System.out.println("Ready deal: " + deal);
      }
    }
  }

  private static Lead generateLead() {
    Lead lead = new Lead();
    lead.setProduct(PRODUCTS[RandomUtils.nextInt(0, PRODUCTS.length)]);

    Prospect prospect = new Prospect(
        PROSPECT_NAMES[RandomUtils.nextInt(0, PROSPECT_NAMES.length)],
        PROSPECT_PHONES[RandomUtils.nextInt(0, PROSPECT_PHONES.length)]
    );

    lead.setProspect(prospect);
    lead.setStatus(INITIAL_STATUS);
    lead.setAmount(RandomUtils.nextInt(0, 100000));
    return lead;
  }

}