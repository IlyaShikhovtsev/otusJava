package ru.shikhovtsev.core.model;

import lombok.*;
import ru.shikhovtsev.core.Id;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Account {
  @Id
  @Setter(AccessLevel.NONE)
  @Getter(AccessLevel.NONE)
  private Long no;
  private String type;
  private BigDecimal rest;

  public Account(String type, BigDecimal rest) {
    this.type = type;
    this.rest = rest;
  }

  public Long getId() {
    return no;
  }
}
