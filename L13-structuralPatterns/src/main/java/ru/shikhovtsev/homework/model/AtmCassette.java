package ru.shikhovtsev.homework.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ru.shikhovtsev.homework.exception.BadBanknotesCountException;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class AtmCassette implements Cassette, Comparable<AtmCassette> {

  private final Nominal nominal;
  @EqualsAndHashCode.Exclude
  private int banknotesCount = 0;

  public AtmCassette(AtmCassette atmCassette) {
    nominal = atmCassette.getNominal();
    banknotesCount = atmCassette.getBanknotesCount();
  }

  @Override
  public void addBanknotes(int banknotesCount) {
    this.banknotesCount += banknotesCount;
  }

  @Override
  public void getBanknotes(int banknotesCount) {
    if (banknotesCount < 0 || banknotesCount > this.banknotesCount) {
      throw new BadBanknotesCountException();
    }
    this.banknotesCount -= banknotesCount;
  }

  @Override
  public long getBalance() {
    return nominal.getValue() * banknotesCount;
  }

  @Override
  public int compareTo(AtmCassette another) {
    return nominal.compareTo(another.nominal);
  }
}
