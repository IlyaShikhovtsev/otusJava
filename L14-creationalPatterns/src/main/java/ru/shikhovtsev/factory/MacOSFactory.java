package ru.shikhovtsev.factory;

import ru.shikhovtsev.factory.buttons.Button;
import ru.shikhovtsev.factory.buttons.MacOSButton;
import ru.shikhovtsev.factory.checkbox.Checkbox;
import ru.shikhovtsev.factory.checkbox.MacOSCheckbox;

public class MacOSFactory implements GUIFactory {
  @Override
  public Button createButton() {
    return new MacOSButton();
  }

  @Override
  public Checkbox createCheckbox() {
    return new MacOSCheckbox();
  }
}
