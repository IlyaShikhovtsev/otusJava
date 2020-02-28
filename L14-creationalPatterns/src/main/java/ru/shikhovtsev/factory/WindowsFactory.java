package ru.shikhovtsev.factory;

import ru.shikhovtsev.factory.buttons.Button;
import ru.shikhovtsev.factory.buttons.WindowsButton;
import ru.shikhovtsev.factory.checkbox.Checkbox;
import ru.shikhovtsev.factory.checkbox.WindowsCheckbox;

public class WindowsFactory implements GUIFactory {
  @Override
  public Button createButton() {
    return new WindowsButton();
  }

  @Override
  public Checkbox createCheckbox() {
    return new WindowsCheckbox();
  }
}
