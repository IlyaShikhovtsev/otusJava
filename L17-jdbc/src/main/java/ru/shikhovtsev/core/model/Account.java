package ru.shikhovtsev.core.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.shikhovtsev.core.Id;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@ToString
public class Account {
  @Id
  private long no;
  private String type;
  private BigDecimal rest;

  public Account(String type, BigDecimal rest) {
    this.type = type;
    this.rest = rest;
  }
}
