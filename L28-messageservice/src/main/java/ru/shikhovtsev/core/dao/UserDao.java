package ru.shikhovtsev.core.dao;

import ru.shikhovtsev.core.model.User;
import ru.shikhovtsev.core.sessionmanager.SessionManager;

import java.util.List;
import java.util.Optional;

public interface UserDao {
  Optional<User> findById(Long id);

  List<User> findAll();

  Optional<User> findByLogin(String login);

  Long save(User user);

  Long update(User user);

  Long createOrUpdate(User user);

  SessionManager getSessionManager();
}
