package ru.shikhovtsev.proxy;

public class Main {
  public static void main(String[] args) {
    var container = new IoCContainer();
    MyService service = container.getService();
    service.computeLastDateTime();
    service.setCount(123);
    service.setParams(1, 5, "simple string");
    System.out.println(service.getClass());

    System.out.println(container.getService().getClass());
  }
}
