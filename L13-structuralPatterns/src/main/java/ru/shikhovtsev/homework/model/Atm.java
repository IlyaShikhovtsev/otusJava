package ru.shikhovtsev.homework.model;

import lombok.*;
import ru.shikhovtsev.homework.EventListener;
import ru.shikhovtsev.homework.command.Command;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Atm implements EventListener {
  private Integer id;
  private Set<AtmCassette> cassettes;

  public List<Nominal> getNominals() {
    return cassettes.stream().map(Cassette::getNominal).collect(Collectors.toList());
  }

  @Override
  public long execute(Command command) {
    return command.action(this);
  }


}
