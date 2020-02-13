package ru.shikhovtsev.service;

import ru.shikhovtsev.exception.BadBanknotesCountException;
import ru.shikhovtsev.model.Cassette;

public class CassetteService {

  public void addBanknotes(Cassette cassette, int banknotesCount) {
    cassette.setBanknotesCount(cassette.getBanknotesCount() + banknotesCount);
  }

  public void getBanknotes(Cassette cassette, int banknotesCount) {
    if (banknotesCount < 0 || banknotesCount > cassette.getBanknotesCount()) {
      throw new BadBanknotesCountException();
    }
    cassette.setBanknotesCount(cassette.getBanknotesCount() - banknotesCount);
  }
}
