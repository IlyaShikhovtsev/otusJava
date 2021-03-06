package ru.shikhovtsev.homework.model;

public interface Cassette {
  void addBanknotes(int banknotesCount);

  void getBanknotes(int banknotesCount);

  int getBanknotesCount();

  Nominal getNominal();

  long getBalance();
}
