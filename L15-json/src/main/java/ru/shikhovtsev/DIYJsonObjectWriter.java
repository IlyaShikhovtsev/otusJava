package ru.shikhovtsev;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.StringJoiner;

public class DIYJsonObjectWriter {

  public String toJson(Object object) {
    if (object == null) {
      return null;
    }

    if (object instanceof String) {
      return "\"" + object.toString() + "\"";
    }

    if (object instanceof Number || object instanceof Boolean || object instanceof Character) {
      return object.toString();
    }

    if (object instanceof Collection<?>) {
      return toJson((Collection<?>) object);
    }

    if (object.getClass().isArray()) {
      return arrayToJson(object);
    }

    return objectToJson(object);
  }

  public String toJson(Collection<?> collection) {
    var sj = new StringJoiner(",");
    for (Object o : collection) {
      sj.add(toJson(o));
    }
    return "[" + sj.toString() + "]";
  }

  private String arrayToJson(Object object) {
    var sj = new StringJoiner(",");
    for (int i = 0; i < Array.getLength(object); i++) {
      sj.add(toJson(Array.get(object, i)));
    }
    return "[" + sj.toString() + "]";
  }

  private String objectToJson(Object object) {
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
