package ru.shikhovtsev.homework.command;

import ru.shikhovtsev.homework.Originator;
import ru.shikhovtsev.homework.model.Atm;
import ru.shikhovtsev.homework.service.AtmService;

public class ResetCommand implements Command {
  private AtmService atmService;
  private Originator originator;

  public ResetCommand(AtmService atmService, Originator originator) {
    this.atmService = atmService;
    this.originator = originator;
  }

  @Override
  public long action(Atm atm) {
    atmService.reset(atm, originator.getOriginalCassettes(atm));
    return 0;
  }
}
