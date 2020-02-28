package ru.shikhovtsev.factorymethod;

import ru.shikhovtsev.factorymethod.button.Button;
import ru.shikhovtsev.factorymethod.button.WindowsButton;

public class WindowsDialog extends Dialog {
  @Override
  public Button createButton() {
    return new WindowsButton();
  }
}
