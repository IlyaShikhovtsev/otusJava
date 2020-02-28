package ru.shikhovtsev.homework.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
@Getter
@ToString
public enum Nominal implements Comparable<Nominal> {

  TEN("Десять", 10),
  FIFTY("Пятьдесят", 50),
  HUNDRED("Сто", 100),
  TWO_HUNDRED("Двести", 200),
  FIVE_HUNDRED("Пятьсот", 500),
  THOUSAND("Тысяча", 1000),
  FIVE_THOUSAND("Пять тысяч", 5000);

  String name;
  int value;
}
