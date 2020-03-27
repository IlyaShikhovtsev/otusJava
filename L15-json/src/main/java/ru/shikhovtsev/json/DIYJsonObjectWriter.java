package ru.shikhovtsev.json;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.StringJoiner;

public class DIYJsonObjectWriter {

  public String toJson(Object object) {
    if (object == null) {
      return null;
    }

    JsonComponent jsonComponent = getJsonComponent(object);
    return jsonComponent.toJson(object);
  }

  private JsonComponent getJsonComponent(Object object) {
    JsonComponent result;
    if (object instanceof String) {
      result = new StringJson();
    } else if (object instanceof Number || object instanceof Boolean || object instanceof Character) {
      result = Object::toString;
    } else if (object instanceof Collection<?>) {
      result = new CollectionJson();
    } else if (object.getClass().isArray()) {
      result = new ArrayJson();
    } else {
      result = new ObjectJson();
    }
    return result;
  }

  public interface JsonComponent {
    String toJson(Object object);
  }

  class StringJson implements JsonComponent {
    @Override
    public String toJson(Object string) {
      return "\"" + string + "\"";
    }
  }

  @SuppressWarnings("unchecked")
  class CollectionJson implements JsonComponent {
    @Override
    public String toJson(Object collection) {
      var sj = new StringJoiner(",");
      for (Object o : (Collection<Object>) collection) {
        sj.add(DIYJsonObjectWriter.this.toJson(o));
      }
      return "[" + sj.toString() + "]";
    }
  }

  class ArrayJson implements JsonComponent {
    @Override
    public String toJson(Object object) {
      var sj = new StringJoiner(",");
      for (int i = 0; i < Array.getLength(object); i++) {
        sj.add(DIYJsonObjectWriter.this.toJson(Array.get(object, i)));
      }
      return "[" + sj.toString() + "]";
    }
  }

  class ObjectJson implements JsonComponent {
    @Override
    public String toJson(Object object) {
      var fields = object.getClass().getDeclaredFields();
      var sj = new StringJoiner(",");
      for (Field field : fields) {
        field.setAccessible(true);
        try {
          sj.add("\"" + field.getName() + "\":" + DIYJsonObjectWriter.this.toJson(field.get(object)));
        } catch (IllegalAccessException e) {
          throw new RuntimeException(e);
        }
      }
      return "{" + sj.toString() + "}";
    }
  }
}
