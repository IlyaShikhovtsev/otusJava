package ru.shikhovtsev.service;

import lombok.AllArgsConstructor;
import ru.shikhovtsev.exception.InsufficientFundsException;
import ru.shikhovtsev.exception.UnsupportedNominalException;
import ru.shikhovtsev.model.Atm;
import ru.shikhovtsev.model.AtmCassette;
import ru.shikhovtsev.model.Nominal;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class AtmServiceImpl implements AtmService {

  private final CassetteService cassetteService;

  @Override
  public long getBalance(Atm atm) {
    return atm.getCassettes().stream().map(c -> c.getNominal().getValue() * c.getBanknotesCount()).reduce(0, Integer::sum);
  }

  @Override
  public void addBanknotes(Atm atm, Map<Nominal, Integer> banknotes) {
    if (!atm.getNominals().containsAll(banknotes.keySet())) {
      throw new UnsupportedNominalException();
    }

    atm.getCassettes().stream()
        .filter(c -> banknotes.containsKey(c.getNominal()))
        .forEach(c -> cassetteService.addBanknotes(c, banknotes.get(c.getNominal())));
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
}
