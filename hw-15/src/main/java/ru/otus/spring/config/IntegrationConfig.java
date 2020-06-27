package ru.otus.spring.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.Message;
import org.springframework.messaging.PollableChannel;
import ru.otus.spring.filter.LeadFilter;

@Configuration
@RequiredArgsConstructor
public class IntegrationConfig {
  private final LeadFilter leadFilter;

  @Bean
  public QueueChannel leadChannel() {
    return MessageChannels.queue(10).get();
  }

  @Bean
  public PublishSubscribeChannel dealChannel() {
    return MessageChannels.publishSubscribe().get();
  }

  @Bean(name = PollerMetadata.DEFAULT_POLLER)
  public PollerMetadata poller() {
    return Pollers.fixedRate(100).maxMessagesPerPoll(2).get();
  }

  @Bean
  public IntegrationFlow leadFlow() {
    return IntegrationFlows.from("leadChannel")
        .handle("callCenterService", "call")
        .filter(Message.class, leadFilter::isLeadRefused,
            e -> e.discardFlow(
                m -> m.handle(
                    message -> System.out.println("Discarded lead: " + message.getPayload())
                )
            )
        )
        .handle("meetingsService", "meeting")
        .filter(Message.class, leadFilter::isLeadRefused,
            e -> e.discardFlow(
                m -> m.handle(
                    message -> System.out.println("Discarded lead: " + message.getPayload())
                )
            )
        )
        .handle("dealService", "promoteLeadToDeal")
        .channel("dealChannel")
        .get();
  }
}
