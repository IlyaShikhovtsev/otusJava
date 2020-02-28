package ru.shikhovtsev;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.shikhovtsev.homework.department.DepartmentImpl;
import ru.shikhovtsev.homework.EventListener;
import ru.shikhovtsev.homework.Originator;
import ru.shikhovtsev.homework.exception.InsufficientFundsException;
import ru.shikhovtsev.homework.exception.UnsupportedNominalException;
import ru.shikhovtsev.homework.model.Atm;
import ru.shikhovtsev.homework.model.AtmCassette;
import ru.shikhovtsev.homework.model.Nominal;
import ru.shikhovtsev.homework.service.AtmService;
import ru.shikhovtsev.homework.service.AtmServiceImpl;
import ru.shikhovtsev.homework.service.CassetteService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static ru.shikhovtsev.homework.model.Nominal.*;

public class AtmTest {

  private Atm atm1;
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

    atm1 = new Atm(1, cassettes);
  }

  @Test
  void addBanknotes() {
    var inputBanknotes = new HashMap<Nominal, Integer>();
    inputBanknotes.put(TEN, 7);
    inputBanknotes.put(HUNDRED, 3);
    inputBanknotes.put(FIVE_HUNDRED, 8);
    inputBanknotes.put(THOUSAND, 2);

    atmService.addBanknotes(atm1, inputBanknotes);

    assertEquals(6370, atmService.getBalance(atm1));
  }

  @Test
  void tryToInputUnsupportedNominal() {
    var inputBanknotes = new HashMap<Nominal, Integer>();
    inputBanknotes.put(FIVE_THOUSAND, 2);

    assertThrows(UnsupportedNominalException.class, () -> atmService.addBanknotes(atm1, inputBanknotes));
  }

  @Test
  void getBalance() {
    var inputBanknotes = new HashMap<Nominal, Integer>();
    inputBanknotes.put(TEN, 7);
    inputBanknotes.put(HUNDRED, 3);

    atmService.addBanknotes(atm1, inputBanknotes);

    assertEquals(370, atmService.getBalance(atm1));
  }

  @Test
  void getAllAtmsBalance() {
    List<Atm> atms = getAtms();
    var originator = new Originator(atms);
    var department = new DepartmentImpl(atmService, atms.stream().map(atm -> (EventListener) atm).collect(Collectors.toList()), originator);

    assertEquals(11080, department.getBalance());
  }

  @Test
  void resetAllAtms() {
    List<Atm> atms = getAtms();
    var originator = new Originator(atms);
    var department = new DepartmentImpl(atmService, atms.stream().map(atm -> (EventListener) atm).collect(Collectors.toList()), originator);

    atms.forEach(atm -> atm.getCassettes().forEach(c -> c.addBanknotes(10)));

    assertNotEquals(11080, department.getBalance());
    department.resetAtms();
    assertEquals(11080, department.getBalance());

    atms.forEach(atm -> atm.getCassettes().forEach(c -> c.addBanknotes(10)));
    department.resetAtms();
    assertEquals(11080, department.getBalance());
  }

  private List<Atm> getAtms() {
    int id = 1;

    Set<AtmCassette> cassettes1 = new HashSet<>();
    cassettes1.add(new AtmCassette(TEN));
    cassettes1.add(new AtmCassette(FIFTY));
    cassettes1.add(new AtmCassette(HUNDRED));
    cassettes1.add(new AtmCassette(FIVE_HUNDRED));
    cassettes1.add(new AtmCassette(THOUSAND));
    var atm1 = new Atm(id++, cassettes1);

    var inputBanknotes = new HashMap<Nominal, Integer>();
    inputBanknotes.put(TEN, 7);
    inputBanknotes.put(HUNDRED, 3);
    inputBanknotes.put(FIVE_HUNDRED, 8);
    inputBanknotes.put(THOUSAND, 2);
    atmService.addBanknotes(atm1, inputBanknotes);
    //6370

    Set<AtmCassette> cassettes2 = new HashSet<>();
    cassettes2.add(new AtmCassette(TEN));
    cassettes2.add(new AtmCassette(FIVE_HUNDRED));
    cassettes2.add(new AtmCassette(THOUSAND));
    var atm2 = new Atm(id++, cassettes2);

    var inputBanknotes2 = new HashMap<Nominal, Integer>();
    inputBanknotes2.put(TEN, 1);
    inputBanknotes2.put(FIVE_HUNDRED, 2);
    inputBanknotes2.put(THOUSAND, 3);
    atmService.addBanknotes(atm2, inputBanknotes2);
    //4010

    Set<AtmCassette> cassettes3 = new HashSet<>();
    cassettes3.add(new AtmCassette(FIFTY));
    cassettes3.add(new AtmCassette(HUNDRED));
    var atm3 = new Atm(id++, cassettes3);

    var inputBanknotes3 = new HashMap<Nominal, Integer>();
    inputBanknotes3.put(FIFTY, 4);
    inputBanknotes3.put(HUNDRED, 5);
    atmService.addBanknotes(atm3, inputBanknotes3);
    // 700

    return List.of(atm1, atm2, atm3);
  }

  @Test
  void getMoney() {
    var inputBanknotes = new HashMap<Nominal, Integer>();
    inputBanknotes.put(TEN, 14);
    inputBanknotes.put(HUNDRED, 6);
    inputBanknotes.put(FIVE_HUNDRED, 8);
    inputBanknotes.put(THOUSAND, 2);

    atmService.addBanknotes(atm1, inputBanknotes);

    assertEquals(5, atmService.getMoney(atm1, 1620).values().stream().reduce(0, Integer::sum));
    assertEquals(1, atmService.getMoney(atm1, 100).values().stream().reduce(0, Integer::sum));
  }

  @Test
  void getMoneyFromEmptyAtm() {
    assertThrows(InsufficientFundsException.class, () -> atmService.getMoney(atm1, 10));
  }
}
