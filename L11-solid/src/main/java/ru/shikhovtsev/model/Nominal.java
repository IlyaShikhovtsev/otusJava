package ru.shikhovtsev.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
@Getter
@EqualsAndHashCode
@ToString
public class Nominal implements Comparable<Nominal> {

  String name;
  int value;

  @Override
  public int compareTo(Nominal o) {
    return Integer.compare(value, o.value);
  }
}
