package ru.shikhovtsev.homework;

import ru.shikhovtsev.homework.model.Atm;
import ru.shikhovtsev.homework.model.AtmCassette;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Originator {
  private final Map<Integer, Set<AtmCassette>> originalStates;

  public Originator(List<Atm> atms) {
    originalStates = atms.stream().collect(
        Collectors.toMap(Atm::getId,
            atm -> atm.getCassettes().stream().map(AtmCassette::new).collect(Collectors.toSet()))
    );
  }

  public Set<AtmCassette> getOriginalCassettes(Atm atm) {
    return originalStates.get(atm.getId()).stream().map(AtmCassette::new).collect(Collectors.toSet());
  }
}
