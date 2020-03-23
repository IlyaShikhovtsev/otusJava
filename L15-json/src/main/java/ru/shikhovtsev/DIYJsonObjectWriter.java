package ru.shikhovtsev;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.StringJoiner;

public class DIYJsonObjectWriter {

  public String toJson(Object object) {
    if (object instanceof String) {
      return "\"" + object + "\"";
    }
    if (object instanceof Number) {
      return object.toString();
    }

    if (object instanceof Collection<?>) {
      var collection = (Collection<?>) object;
      var sj = new StringJoiner(",");
      for (Object o : collection) {
        sj.add(toJson(o));
      }
      return "[" + sj.toString() + "]";
    }

    if (object instanceof boolean[]) {
      var array = (boolean[]) object;
      var sj = new StringJoiner(",");
      for (Object o : array) {
        sj.add(toJson(o));
      }
      return "[" + sj.toString() + "]";
    }

    if (object instanceof int[]) {
      var array = (int[]) object;
      var sj = new StringJoiner(",");
      for (Object o : array) {
        sj.add(toJson(o));
      }
      return "[" + sj.toString() + "]";
    }

    if (object instanceof long[]) {
      var array = (long[]) object;
      var sj = new StringJoiner(",");
      for (Object o : array) {
        sj.add(toJson(o));
      }
      return "[" + sj.toString() + "]";
    }

    if (object instanceof byte[]) {
      var array = (byte[]) object;
      var sj = new StringJoiner(",");
      for (Object o : array) {
        sj.add(toJson(o));
      }
      return "[" + sj.toString() + "]";
    }

    if (object instanceof short[]) {
      var array = (short[]) object;
      var sj = new StringJoiner(",");
      for (Object o : array) {
        sj.add(toJson(o));
      }
      return "[" + sj.toString() + "]";
    }

    if (object instanceof double[]) {
      var array = (double[]) object;
      var sj = new StringJoiner(",");
      for (Object o : array) {
        sj.add(toJson(o));
      }
      return "[" + sj.toString() + "]";
    }

    if (object instanceof float[]) {
      var array = (float[]) object;
      var sj = new StringJoiner(",");
      for (Object o : array) {
        sj.add(toJson(o));
      }
      return "[" + sj.toString() + "]";
    }

    if (object.getClass().isArray()) {
      Object[] array = (Object[]) object;
      var sj = new StringJoiner(",");
      for (Object o : array) {
        sj.add(toJson(o));
      }
      return "[" + sj.toString() + "]";
    }

    return getResult(object);
  }

  private String getResult(Object object) {
    var fields = object.getClass().getDeclaredFields();
    var sj = new StringJoiner(",");
    for (Field field : fields) {
      field.setAccessible(true);
      try {
        sj.add("\"" + field.getName() + "\":" + toJson(field.get(object)));
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }
    return "{" + sj.toString() + "}";
  }
}
