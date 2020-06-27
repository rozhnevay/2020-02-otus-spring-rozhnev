package ru.otus.spring.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Prospect {
  final String name;
  final String phone;
  Customer customer;
}
