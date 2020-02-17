package ru.shikhovtsev;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.shikhovtsev.exception.InsufficientFundsException;
import ru.shikhovtsev.exception.UnsupportedNominalException;
import ru.shikhovtsev.model.Atm;
import ru.shikhovtsev.model.AtmCassette;
import ru.shikhovtsev.model.Nominal;
import ru.shikhovtsev.service.AtmService;
import ru.shikhovtsev.service.AtmServiceImpl;
import ru.shikhovtsev.service.CassetteService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.shikhovtsev.model.Nominal.*;

public class AtmTest {

  private Atm atm;
  private static AtmService atmService;

  @BeforeAll
  static void createCassettes() {
    atmService = new AtmServiceImpl(new CassetteService() {});
  }

  @BeforeEach
  void refreshAtm() {
    Set<AtmCassette> cassettes = new HashSet<>();
    cassettes.add(new AtmCassette(TEN));
    cassettes.add(new AtmCassette(FIFTY));
    cassettes.add(new AtmCassette(HUNDRED));
    cassettes.add(new AtmCassette(FIVE_HUNDRED));
    cassettes.add(new AtmCassette(THOUSAND));

    atm = new Atm(cassettes);
  }

  @Test
  void addBanknotes() {
    var inputBanknotes = new HashMap<Nominal, Integer>();
    inputBanknotes.put(TEN, 7);
    inputBanknotes.put(HUNDRED, 3);
    inputBanknotes.put(FIVE_HUNDRED, 8);
    inputBanknotes.put(THOUSAND, 2);

    atmService.addBanknotes(atm, inputBanknotes);

    assertEquals(6370, atmService.getBalance(atm));
  }

  @Test
  void tryToInputUnsupportedNominal() {
    var inputBanknotes = new HashMap<Nominal, Integer>();
    inputBanknotes.put(FIVE_THOUSAND, 2);

    assertThrows(UnsupportedNominalException.class, () -> atmService.addBanknotes(atm, inputBanknotes));
  }

  @Test
  void getBalance() {
    var inputBanknotes = new HashMap<Nominal, Integer>();
    inputBanknotes.put(TEN, 7);
    inputBanknotes.put(HUNDRED, 3);

    atmService.addBanknotes(atm, inputBanknotes);

    assertEquals(370, atmService.getBalance(atm));
  }

  @Test
  void getMoney() {
    var inputBanknotes = new HashMap<Nominal, Integer>();
    inputBanknotes.put(TEN, 14);
    inputBanknotes.put(HUNDRED, 6);
    inputBanknotes.put(FIVE_HUNDRED, 8);
    inputBanknotes.put(THOUSAND, 2);

    atmService.addBanknotes(atm, inputBanknotes);

    assertEquals(5, atmService.getMoney(atm, 1620).values().stream().reduce(0, Integer::sum));
    assertEquals(1, atmService.getMoney(atm, 100).values().stream().reduce(0, Integer::sum));
  }

  @Test
  void getMoneyFromEmptyAtm() {
    assertThrows(InsufficientFundsException.class, () -> atmService.getMoney(atm, 10));
  }
}
