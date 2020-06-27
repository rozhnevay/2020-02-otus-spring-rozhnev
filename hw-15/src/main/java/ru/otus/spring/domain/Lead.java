package ru.otus.spring.domain;


import lombok.Data;

@Data
public class Lead {
  String product;
  String status;
  Number amount;
  Prospect prospect;
  Deal deal;
}
