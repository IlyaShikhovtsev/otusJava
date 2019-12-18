package ru.shikhovtsev.proxy;

public class Main {
  public static void main(String[] args) {
    var container = new IoCContainer();
    MyService service = getService(container);
    service.computeLastDateTime();
    service.setCount(123);
    service.setParams(1, 5, "simple string");
    System.out.println(service.getClass());
  }

  public static MyService getService(IoCContainer container) {
    return container.getProxiedObject(MyService.class, new MyServiceImpl());
  }
}
