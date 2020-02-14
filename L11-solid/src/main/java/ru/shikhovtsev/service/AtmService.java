package ru.shikhovtsev.service;

import ru.shikhovtsev.model.Atm;
import ru.shikhovtsev.model.Nominal;

import java.util.Map;

public interface AtmService {
  long getBalance(Atm atm);

  void addBanknotes(Atm atm, Map<Nominal, Integer> banknotes);

  Map<Nominal, Integer> getMoney(Atm atm, Integer money);
}
