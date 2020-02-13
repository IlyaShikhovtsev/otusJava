package ru.shikhovtsev.model;

import lombok.*;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Cassette implements Comparable<Cassette> {

  private final Nominal nominal;
  @Setter
  @EqualsAndHashCode.Exclude
  private int banknotesCount = 0;

  @Override
  public int compareTo(Cassette another) {
    return nominal.compareTo(another.nominal);
  }
}
