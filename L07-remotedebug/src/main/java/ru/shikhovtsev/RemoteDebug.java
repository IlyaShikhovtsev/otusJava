package ru.shikhovtsev;//java -Xdebug -Xrunjdwp:transport=dt_socket,address=5005,server=y,suspend=n ru.otus.ru.shikhovtsev.RemoteDebug

public class RemoteDebug {

  public static void main(String[] args) {
    new RemoteDebug().loop();
  }

  private int counter = 0;

  private void loop() {
    while (true) {
      counter += 1;
      incInt();

      System.out.println(counter);
    }
  }

  private void incInt() {
    counter += 1000;
  }

  public class TestByte {
    byte valByte;
  }

}
