package ru.shikhovtsev.jdbc.dao;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.shikhovtsev.core.dao.AccountDao;
import ru.shikhovtsev.core.dao.AccountDaoException;
import ru.shikhovtsev.core.dao.UserDaoException;
import ru.shikhovtsev.core.model.Account;
import ru.shikhovtsev.core.sessionmanager.SessionManager;
import ru.shikhovtsev.jdbc.JdbcTemplate;
import ru.shikhovtsev.jdbc.sessionmanager.SessionManagerJdbc;

import java.sql.Connection;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
public class AccountDaoJdbc implements AccountDao {

  private final SessionManagerJdbc sessionManager;
  private final JdbcTemplate<Account> jdbcTemplate;

  @Override
  public Optional<Account> findById(Long id) {
    try {
      return jdbcTemplate.load(getConnection(), Account.class, id);
    } catch (Exception ex) {
      log.error(ex.getMessage(), ex);
    }
    return Optional.empty();
  }

  @Override
  public Long save(Account account) {
    try {
      return jdbcTemplate.create(getConnection(), account);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new AccountDaoException(e);
    }
  }

  @Override
  public Long update(Account account) {
    try {
      jdbcTemplate.update(getConnection(), account);
      return account.getId();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new UserDaoException(e);
    }
  }

  @Override
  public Long createOrUpdate(Account account) {
    try {
      return jdbcTemplate.createOrUpdate(getConnection(), account);
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
