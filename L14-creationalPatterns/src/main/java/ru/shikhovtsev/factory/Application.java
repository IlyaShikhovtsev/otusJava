package ru.shikhovtsev.factory;

import ru.shikhovtsev.factory.buttons.Button;
import ru.shikhovtsev.factory.checkbox.Checkbox;

public class Application {
  private Button button;
  private Checkbox checkbox;

  public Application(GUIFactory factory) {
    button = factory.createButton();
    checkbox = factory.createCheckbox();
  }

  public void paint() {
    button.paint();
    checkbox.paint();
  }
}
