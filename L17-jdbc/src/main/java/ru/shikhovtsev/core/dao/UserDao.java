package ru.shikhovtsev.core.dao;

import ru.shikhovtsev.core.model.User;
import ru.shikhovtsev.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface UserDao {
  Optional<User> findById(long id);

  long saveUser(User user);

  SessionManager getSessionManager();
}
