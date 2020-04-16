package ru.shikhovtsev.jdbc.sessionmanager;

import ru.shikhovtsev.core.sessionmanager.SessionManager;
import ru.shikhovtsev.core.sessionmanager.SessionManagerException;
import ru.shikhovtsev.jdbc.DatabaseSessionJdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class SessionManagerJdbc implements SessionManager {
  private static final int TIMEOUT_IN_SECONDS = 5;
  private final DataSource dataSource;

  private Connection connection;
  private DatabaseSessionJdbc databaseSession;

  public SessionManagerJdbc(DataSource dataSource) {
    if (dataSource == null) {
      throw new SessionManagerException("Datasource is null");
    }
    this.dataSource = dataSource;
  }

  @Override
  public void beginSession() {
    try {
      connection = dataSource.getConnection();
      databaseSession = new DatabaseSessionJdbc(connection);
    } catch (SQLException ex) {
      throw new SessionManagerException(ex);
    }
  }

  @Override
  public void commitSession() {
    checkConnection();
    try {
      connection.commit();
    } catch (SQLException ex) {
      throw new SessionManagerException(ex);
    }
  }

  private void checkConnection() {
    try {
      if (connection == null || !connection.isValid(TIMEOUT_IN_SECONDS)) {
        throw new SessionManagerException("Connection is invalid");
      }
    } catch (SQLException ex) {
      throw new SessionManagerException(ex);
    }
  }

  @Override
  public void rollbackSession() {
    checkConnection();
    try {
      connection.rollback();
    } catch (SQLException ex) {
      throw new SessionManagerException(ex);
    }
  }

  @Override
  public void close() {
    try {
      connection.close();
    } catch (SQLException ex) {
      throw new SessionManagerException(ex);
    }
  }

  @Override
  public DatabaseSessionJdbc getCurrentSession() {
    checkConnection();
    return databaseSession;
  }
}
