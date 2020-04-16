package ru.shikhovtsev.jdbc.dao;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.shikhovtsev.core.dao.UserDao;
import ru.shikhovtsev.core.dao.UserDaoException;
import ru.shikhovtsev.core.model.User;
import ru.shikhovtsev.core.sessionmanager.SessionManager;
import ru.shikhovtsev.jdbc.JdbcTemplate;
import ru.shikhovtsev.jdbc.sessionmanager.SessionManagerJdbc;

import java.sql.Connection;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
public class UserDaoJdbc implements UserDao {

  private final SessionManagerJdbc sessionManager;
  private final JdbcTemplate<User> jdbcTemplate;

  @Override
  public Optional<User> findById(long id) {
    try {
      return jdbcTemplate.load(getConnection(), User.class, id);
    } catch (Exception ex) {
      log.error(ex.getMessage(), ex);
    }
    return Optional.empty();
  }

  @Override
  public long saveUser(User user) {
    try {
      return jdbcTemplate.create(getConnection(), user);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new UserDaoException(e);
    }
  }

  @Override
  public SessionManager getSessionManager() {
    return sessionManager;
  }

  private Connection getConnection() {
    return sessionManager.getCurrentSession().getConnection();
  }
}
