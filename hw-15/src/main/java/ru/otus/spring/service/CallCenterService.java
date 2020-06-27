package ru.otus.spring.service;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Lead;

@Service
public class CallCenterService {
  private static final String[] CALL_STATUSES = {"MEETING_SCHEDULED", "CLIENT_REFUSED"};

  public static Lead call(Lead lead) {
    lead.setStatus(CALL_STATUSES[RandomUtils.nextInt(0, CALL_STATUSES.length)]);
    return lead;
  }
}
