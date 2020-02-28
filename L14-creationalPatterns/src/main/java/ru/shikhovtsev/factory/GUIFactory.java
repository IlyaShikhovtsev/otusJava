package ru.shikhovtsev.factory;

import ru.shikhovtsev.factory.buttons.Button;
import ru.shikhovtsev.factory.checkbox.Checkbox;

public interface GUIFactory {
  Button createButton();
  Checkbox createCheckbox();
}
