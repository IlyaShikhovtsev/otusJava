package ru.shikhovtsev;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.shikhovtsev.exception.InsufficientFundsException;
import ru.shikhovtsev.exception.UnsupportedNominalException;
import ru.shikhovtsev.model.Atm;
import ru.shikhovtsev.model.Cassette;
import ru.shikhovtsev.model.Nominal;
import ru.shikhovtsev.service.AtmService;
import ru.shikhovtsev.service.CassetteService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AtmTest {

  private static final Nominal NOMINAL_10 = new Nominal("Десять", 10);
  private static final Nominal NOMINAL_50 = new Nominal("Пятьдесят", 50);
  private static final Nominal NOMINAL_100 = new Nominal("Сто", 100);
  private static final Nominal NOMINAL_500 = new Nominal("Пятьсот", 500);
  private static final Nominal NOMINAL_1000 = new Nominal("Тысяча", 1000);
  private static final Nominal NOMINAL_5000 = new Nominal("Пять тысяч", 5000);

  private static Set<Cassette> cassettes;

  private Atm atm;
  private static AtmService atmService;

  @BeforeAll
  static void createCassettes() {
    atmService = new AtmService(new CassetteService());

    cassettes = new HashSet<>();
    cassettes.add(new Cassette(NOMINAL_10));
    cassettes.add(new Cassette(NOMINAL_50));
    cassettes.add(new Cassette(NOMINAL_100));
    cassettes.add(new Cassette(NOMINAL_500));
    cassettes.add(new Cassette(NOMINAL_1000));
  }

  @BeforeEach
  void refreshAtm() {
    atm = new Atm(cassettes);
  }

  @Test
  void addBanknotes() {
    var inputBanknotes = new HashMap<Nominal, Integer>();
    inputBanknotes.put(NOMINAL_10, 7);
    inputBanknotes.put(NOMINAL_100, 3);
    inputBanknotes.put(NOMINAL_500, 8);
    inputBanknotes.put(NOMINAL_1000, 2);

    atmService.addBanknotes(atm, inputBanknotes);

    assertEquals(6370, atmService.getBalance(atm));
  }

  @Test
  void tryToInputUnsupportedNominal() {
    var inputBanknotes = new HashMap<Nominal, Integer>();
    inputBanknotes.put(NOMINAL_5000, 2);

    assertThrows(UnsupportedNominalException.class, () -> atmService.addBanknotes(atm, inputBanknotes));
  }

  @Test
  void getBalance() {
    var inputBanknotes = new HashMap<Nominal, Integer>();
    inputBanknotes.put(NOMINAL_10, 7);
    inputBanknotes.put(NOMINAL_100, 3);

    atmService.addBanknotes(atm, inputBanknotes);

    assertEquals(370, atmService.getBalance(atm));
  }

  @Test
  void getMoney() {
    var inputBanknotes = new HashMap<Nominal, Integer>();
    inputBanknotes.put(NOMINAL_10, 14);
    inputBanknotes.put(NOMINAL_100, 6);
    inputBanknotes.put(NOMINAL_500, 8);
    inputBanknotes.put(NOMINAL_1000, 2);

    atmService.addBanknotes(atm, inputBanknotes);

    assertEquals(5, atmService.getMoney(atm, 1620).values().stream().reduce(0, Integer::sum));
    assertEquals(1, atmService.getMoney(atm, 100).values().stream().reduce(0, Integer::sum));
  }

  @Test
  void getMoneyFromEmptyAtm() {
    assertThrows(InsufficientFundsException.class, () -> atmService.getMoney(atm, 10));
  }
}
