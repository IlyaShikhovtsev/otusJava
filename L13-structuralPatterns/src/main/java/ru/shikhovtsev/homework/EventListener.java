package ru.shikhovtsev.homework;

import ru.shikhovtsev.homework.command.Command;

public interface EventListener {
  void execute(Command command);
}
