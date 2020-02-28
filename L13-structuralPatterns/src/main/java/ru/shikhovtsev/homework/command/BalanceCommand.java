package ru.shikhovtsev.homework.command;

import ru.shikhovtsev.homework.model.Atm;
import ru.shikhovtsev.homework.service.AtmService;

public class BalanceCommand implements Command {

  private AtmService atmService;

  public BalanceCommand(AtmService atmService) {
    this.atmService = atmService;
  }

  @Override
  public long action(Atm atm) {
    return atmService.getBalance(atm);
  }
}
