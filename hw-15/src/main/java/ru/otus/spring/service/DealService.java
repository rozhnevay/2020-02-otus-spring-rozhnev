package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Customer;
import ru.otus.spring.domain.Deal;
import ru.otus.spring.domain.Lead;
import ru.otus.spring.domain.Prospect;

@Service
public class DealService {
  private static final String LEAD_SUCCESS_STATUS = "DEAL_OPENED";

  public static Deal promoteLeadToDeal(Lead lead) {


    Prospect prospect = lead.getProspect();
    Customer customer = new Customer();
    customer.setName(prospect.getName());
    customer.setPhone(prospect.getPhone());
    prospect.setCustomer(customer);

    Deal deal = new Deal();
    deal.setAmount(lead.getAmount());
    deal.setCustomer(customer);
    deal.setProduct(lead.getProduct());

    lead.setStatus(LEAD_SUCCESS_STATUS);
    lead.setDeal(deal);

    return deal;
  }
}
