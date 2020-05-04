package ru.shikhovtsev.jdbc;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
public class DbExecutor<T> {

  public Optional<T> find(Connection connection, String sql, Long id, Function<ResultSet, T> consumer) throws SQLException {
    try (var ps = connection.prepareStatement(sql)) {
      ps.setLong(1, id);
      try (ResultSet rs = ps.executeQuery()) {
          return Optional.ofNullable(consumer.apply(rs));
      }
    }
  }

  public long insert(Connection connection, String sql, List<Object> params) throws SQLException {
    Savepoint savePoint = connection.setSavepoint("savePoint");
    try (var ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      for (int i = 0; i < params.size(); i++) {
        ps.setObject(i + 1, params.get(i));
      }

      ps.executeUpdate();
      try (var rs = ps.getGeneratedKeys()) {
        rs.next();
        return rs.getLong(1);
      }
    } catch (SQLException ex) {
      connection.rollback(savePoint);
      log.error(ex.getMessage(), ex);
      throw ex;
    }
  }

  public void update(Connection connection, String sql, List<Object> params) throws SQLException {
    Savepoint savePoint = connection.setSavepoint("savePoint");
    try (var ps = connection.prepareStatement(sql)) {
      for (int i = 0; i < params.size(); i++) {
        ps.setObject(i + 1, params.get(i));
      }
      ps.executeUpdate();
    } catch (SQLException ex) {
      connection.rollback(savePoint);
      log.error(ex.getMessage(), ex);
      throw ex;
    }
  }

}
