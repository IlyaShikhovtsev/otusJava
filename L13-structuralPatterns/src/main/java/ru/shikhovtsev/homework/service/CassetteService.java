package ru.shikhovtsev.homework.service;


import ru.shikhovtsev.homework.model.Cassette;

public interface CassetteService {
  default void addBanknotes(Cassette cassette, int banknotesCount) {
    cassette.addBanknotes(banknotesCount);
  }

  default void getBanknotes(Cassette cassette, int banknotesCount) {
    cassette.getBanknotes(banknotesCount);
  }
}
