package ru.shikhovtsev.homework.service;

import lombok.AllArgsConstructor;
import ru.shikhovtsev.homework.exception.InsufficientFundsException;
import ru.shikhovtsev.homework.exception.UnsupportedNominalException;
import ru.shikhovtsev.homework.model.Atm;
import ru.shikhovtsev.homework.model.AtmCassette;
import ru.shikhovtsev.homework.model.Nominal;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class AtmServiceImpl implements AtmService {

  private final CassetteService cassetteService;

  @Override
  public long getBalance(Atm atm) {
    long result = 0L;
    for (AtmCassette cassette : atm.getCassettes()) {
      result += cassette.getBalance();
    }
    return result;
  }

  @Override
  public void addBanknotes(Atm atm, Map<Nominal, Integer> banknotes) {
    if (!atm.getNominals().containsAll(banknotes.keySet())) {
      throw new UnsupportedNominalException();
    }

    for (AtmCassette cassette : atm.getCassettes()) {
      if (banknotes.containsKey(cassette.getNominal())) {
        cassetteService.addBanknotes(cassette, banknotes.get(cassette.getNominal()));
      }
    }
  }

  @Override
  public Map<Nominal, Integer> getMoney(Atm atm, Integer money) {
    // Используется жадный алгоритм
    var banknotes = new EnumMap<Nominal, Integer>(Nominal.class);
    for (AtmCassette cassette : atm.getCassettes().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList())) {
      int banknotesCount = Math.min(money / cassette.getNominal().getValue(), cassette.getBanknotesCount());
      cassetteService.getBanknotes(cassette, banknotesCount);
      banknotes.put(cassette.getNominal(), banknotesCount);

      money -= banknotesCount * cassette.getNominal().getValue();
    }

    if (money != 0) {
      throw new InsufficientFundsException();
    }

    return banknotes;
  }

  @Override
  public void reset(Atm atm, Set<AtmCassette> startState) {
    atm.setCassettes(startState);
  }
}
