package ru.shikhovtsev.core.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.shikhovtsev.core.Id;

@Getter
@NoArgsConstructor
@ToString
public class User {
  @Id
  private long id;
  private String name;

  public User(String name) {
    this.name = name;
  }
}
