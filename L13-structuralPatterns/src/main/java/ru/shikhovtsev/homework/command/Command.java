package ru.shikhovtsev.homework.command;

import ru.shikhovtsev.homework.model.Atm;

public interface Command {
  long action(Atm atm);
}
