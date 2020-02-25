package ru.shikhovtsev.homework.service;


import ru.shikhovtsev.homework.model.Atm;
import ru.shikhovtsev.homework.model.AtmCassette;
import ru.shikhovtsev.homework.model.Nominal;

import java.util.Map;
import java.util.Set;

public interface AtmService {
  long getBalance(Atm atm);

  void addBanknotes(Atm atm, Map<Nominal, Integer> banknotes);

  Map<Nominal, Integer> getMoney(Atm atm, Integer money);

  void reset(Atm atm, Set<AtmCassette> startState);
}
