package ru.shikhovtsev.core.model;

import lombok.*;
import ru.shikhovtsev.core.Id;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {
  @Id
  @Setter(AccessLevel.NONE)
  private Long id;
  private String name;

  public User(String name) {
    this.name = name;
  }
}
