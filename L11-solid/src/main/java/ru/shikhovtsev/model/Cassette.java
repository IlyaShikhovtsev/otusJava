package ru.shikhovtsev.model;

public interface Cassette {
  void addBanknotes(int banknotesCount);

  void getBanknotes(int banknotesCount);

  int getBanknotesCount();

  Nominal getNominal();
}
