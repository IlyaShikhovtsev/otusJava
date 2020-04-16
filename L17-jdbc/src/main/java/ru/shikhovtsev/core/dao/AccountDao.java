package ru.shikhovtsev.core.dao;

import ru.shikhovtsev.core.model.Account;
import ru.shikhovtsev.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface AccountDao {
  Optional<Account> findById(long id);

  long save(Account account);

  SessionManager getSessionManager();
}
