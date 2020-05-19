package ru.shikhovtsev.web;

import java.util.HashMap;
import java.util.Map;

public class Context {

  private Context() {
  }

  private static final Map<Class<?>, Object> services = new HashMap<>();

  public static void put(Class<?> clazz, Object service) {
    services.put(clazz, service);
  }

  @SuppressWarnings("unchecked")
  public static <T> T getService(Class<T> clazz) {
    return (T) services.get(clazz);
  }

}
