package ru.shikhovtsev.bridge;

import ru.shikhovtsev.bridge.device.Device;
import ru.shikhovtsev.bridge.device.Radio;
import ru.shikhovtsev.bridge.device.Tv;
import ru.shikhovtsev.bridge.remote.AdvancedRemote;
import ru.shikhovtsev.bridge.remote.BasicRemote;

public class Demo {
  public static void main(String[] args) {
    testDevice(new Tv());
    testDevice(new Radio());
  }

  public static void testDevice(Device device) {
    System.out.println("Tests with basic remote.");
    BasicRemote basicRemote = new BasicRemote(device);
    basicRemote.power();
    device.printStatus();

    System.out.println("Tests with advanced remote.");
    AdvancedRemote advancedRemote = new AdvancedRemote(device);
    advancedRemote.power();
    advancedRemote.mute();
    device.printStatus();
  }
}
