package ru.shikhovtsev.homework.command;

import ru.shikhovtsev.homework.model.Atm;
import ru.shikhovtsev.homework.service.AtmService;

public class BalanceCommand implements Command {

  private AtmService atmService;
  private long balance;

  public BalanceCommand(AtmService atmService) {
    this.atmService = atmService;
  }

  @Override
  public void action(Atm atm) {
    balance = atmService.getBalance(atm);
  }

  public long getBalance() {
    return balance;
  }
}
