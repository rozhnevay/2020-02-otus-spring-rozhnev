package ru.otus.spring.filter;

import org.springframework.integration.annotation.Filter;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Lead;

@Component
public class LeadFilter {
  private static final String CLIENT_REFUSED = "CLIENT_REFUSED";

  @Filter
  public boolean isLeadRefused(
      Message<?> message
  ){
    Lead lead = (Lead) message.getPayload();
    if (lead.getStatus().equalsIgnoreCase(CLIENT_REFUSED)) {
      return false;
    }
    return true;
  }
}
