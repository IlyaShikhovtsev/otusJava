package ru.shikhovtsev.factorymethod;

import ru.shikhovtsev.factorymethod.button.Button;
import ru.shikhovtsev.factorymethod.button.HtmlButton;

public class HtmlDialog extends Dialog {
  @Override
  public Button createButton() {
    return new HtmlButton();
  }
}
