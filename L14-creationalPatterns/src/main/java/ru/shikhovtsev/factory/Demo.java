package ru.shikhovtsev.factory;

public class Demo {
  private static Application configureApp() {
    GUIFactory factory;
    var osName = System.getProperty("os.name").toLowerCase();
    if (osName.contains("mac")) {
      factory = new MacOSFactory();
    } else {
      factory = new WindowsFactory();
    }
    return new Application(factory);
  }

  public static void main(String[] args) {
    var app = configureApp();
    app.paint();
  }
}
