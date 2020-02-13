package ru.shikhovtsev.model;

import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Atm {
  private Set<Cassette> cassettes;

  public List<Nominal> getNominals() {
    return cassettes.stream().map(Cassette::getNominal).collect(Collectors.toList());
  }
}
