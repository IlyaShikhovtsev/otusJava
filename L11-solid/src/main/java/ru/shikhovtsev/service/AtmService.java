package ru.shikhovtsev.service;

import lombok.AllArgsConstructor;
import ru.shikhovtsev.exception.InsufficientFundsException;
import ru.shikhovtsev.exception.UnsupportedNominalException;
import ru.shikhovtsev.model.Atm;
import ru.shikhovtsev.model.Cassette;
import ru.shikhovtsev.model.Nominal;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class AtmService {

  private CassetteService cassetteService;

  public long getBalance(Atm atm) {
    return atm.getCassettes().stream().map(c -> c.getNominal().getValue() * c.getBanknotesCount()).reduce(0, Integer::sum);
  }

  public void addBanknotes(Atm atm, Map<Nominal, Integer> banknotes) {
    if (!atm.getNominals().containsAll(banknotes.keySet())) {
      throw new UnsupportedNominalException();
    }

    atm.getCassettes().stream()
        .filter(c -> banknotes.containsKey(c.getNominal()))
        .forEach(c -> cassetteService.addBanknotes(c, banknotes.get(c.getNominal())));
  }

  public Map<Nominal, Integer> getMoney(Atm atm, Integer money) {
    // Используется жадный алгоритм
    var banknotes = new HashMap<Nominal, Integer>();
    for (Cassette cassette : atm.getCassettes().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList())) {
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
