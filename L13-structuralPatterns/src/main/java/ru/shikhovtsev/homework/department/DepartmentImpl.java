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

  public DepartmentImpl(AtmService atmService, List<EventListener> atms, Originator originator) {
    this.atmService = atmService;
    this.listeners = atms;
    this.originator = originator;
  }

  @Override
  public long getBalance() {
    return listeners.stream()
        .map(l -> {
          var command = new BalanceCommand(atmService);
          l.execute(command);
          return command.getBalance();
        }).reduce(0L, Long::sum);
  }

  @Override
  public void resetAtms() {
    listeners.forEach(l -> l.execute(new ResetCommand(atmService, originator)));
  }
}
