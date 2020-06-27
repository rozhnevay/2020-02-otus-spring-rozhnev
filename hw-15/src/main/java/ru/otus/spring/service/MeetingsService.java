package ru.otus.spring.service;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Lead;

@Service
public class MeetingsService {
  private static final String[] MEETING_STATUSES = {"DOCUMENTS_SIGNED", "CLIENT_REFUSED"};

  public static Lead meeting(Lead lead) {
    lead.setStatus(MEETING_STATUSES[RandomUtils.nextInt(0, MEETING_STATUSES.length)]);
    return lead;
  }
}
