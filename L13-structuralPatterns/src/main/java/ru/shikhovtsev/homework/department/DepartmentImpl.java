package ru.shikhovtsev.homework.department;

import ru.shikhovtsev.homework.EventListener;
import ru.shikhovtsev.homework.Originator;
import ru.shikhovtsev.homework.command.BalanceCommand;
import ru.shikhovtsev.homework.command.ResetCommand;
import ru.shikhovtsev.homework.service.AtmService;

import java.util.List;

public class DepartmentImpl implements Department {

  private final AtmService atmService;
  private final List<EventListener> listeners;
  private final Originator originator;

  private BalanceCommand balanceCommand;
  private ResetCommand resetCommand;

  public DepartmentImpl(AtmService atmService, List<EventListener> atms, Originator originator) {
    this.atmService = atmService;
    this.listeners = atms;
    this.originator = originator;
  }

  @Override
  public long getBalance() {
    if (balanceCommand == null) {
      balanceCommand = new BalanceCommand(atmService);
    }
    long result = 0;
    for (EventListener l : listeners) {
      result += l.execute(balanceCommand);
    }
    return result;
  }

  @Override
  public void resetAtms() {
    if (resetCommand == null) {
      resetCommand = new ResetCommand(atmService, originator);
    }
    listeners.forEach(l -> l.execute(resetCommand));
  }
}
