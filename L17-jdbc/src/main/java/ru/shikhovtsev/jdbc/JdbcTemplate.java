package ru.shikhovtsev.jdbc;

import lombok.extern.slf4j.Slf4j;
import ru.shikhovtsev.core.UsingMultipleIdException;
import ru.shikhovtsev.core.Id;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

@Slf4j
public class JdbcTemplate<T> {

  private final DbExecutor<T> dbExecutor;
  private final Field[] fields;
  private final Field idField;

  public JdbcTemplate(DbExecutor<T> dbExecutor, Class<T> clazz) {
    fields = clazz.getDeclaredFields();
    for (Field field : fields) {
      field.setAccessible(true);
    }
    idField = getIdField();
    this.dbExecutor = dbExecutor;
  }

  private Field getIdField() {
    List<Field> idFields = new ArrayList<>();
    for (Field field : fields) {
      if (Arrays.stream(field.getDeclaredAnnotations()).anyMatch(an -> an.annotationType().equals(Id.class))) {
        idFields.add(field);
      }
    }

    if (idFields.size() != 1) {
      throw new UsingMultipleIdException("Fields: " + idFields.toString());
    }
    return idFields.get(0);
  }

  public long create(Connection connection, T object) throws SQLException {
    var params = new ArrayList<>();
    return dbExecutor.insertRecord(connection, createSqlInsert(object, params), params);
  }


  public void update(T object) {

  }

  public void createOrUpdate(T object) {

  }

  public Optional<T> load(Connection connection, Class<T> clazz, long id) throws SQLException {
    return dbExecutor.find(connection, createSqlFind(clazz), id, rs -> {
      try {
        if (rs.next()) {
          T t = clazz.getConstructor().newInstance();
          for (Field field : fields) {
            field.set(t, rs.getObject(field.getName()));
          }
          return t;
        }
      } catch (NoSuchMethodException e) {
        log.error("Need default public constructor, class {}", clazz.getName(), e);
      } catch (SQLException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
        log.error(e.getMessage(), e);
      }
      return null;
    });
  }

  private String createSqlInsert(T object, ArrayList<Object> params) {
    String queryPattern = "INSERT INTO %s (%s) values (%s)";

    var sjFields = new StringJoiner(",");
    var sjValues = new StringJoiner(",");
    for (Field field : fields) {
      if (field.equals(idField)) {
        continue;
      }

      sjFields.add(field.getName());
      try {
        params.add(field.get(object));
        sjValues.add("?");
      } catch (IllegalAccessException ex) {
        log.error(ex.getMessage(), ex);
        throw new RuntimeException(ex);
      }
    }

    return String.format(queryPattern, object.getClass().getSimpleName(), sjFields, sjValues);
  }

  private String createSqlFind(Class<T> object) {
    String queryPattern = "SELECT %s FROM %s WHERE %s = ?";

    var sjFields = new StringJoiner(",");
    for (Field field : fields) {
      sjFields.add(field.getName());
    }

    return String.format(queryPattern, sjFields, object.getSimpleName(), idField.getName());
  }
}
