package ru.shikhovtsev.core.dao;

import ru.shikhovtsev.core.model.Account;
import ru.shikhovtsev.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface AccountDao {
  Optional<Account> findById(Long id);

  Long save(Account account);

  Long update(Account account);

  Long createOrUpdate(Account account);

  SessionManager getSessionManager();
}
