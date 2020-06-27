package ru.otus.spring.domain;

import lombok.Data;

@Data
public class Deal {
  String product;
  Number amount;
  Customer customer;
}
