package ru.shikhovtsev.core.service;

import lombok.extern.slf4j.Slf4j;
import ru.shikhovtsev.core.dao.UserDao;
import ru.shikhovtsev.core.model.User;
import ru.shikhovtsev.core.sessionmanager.SessionManager;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
public class DbServiceUserImpl implements DBServiceUser {

  private final UserDao userDao;

  public DbServiceUserImpl(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public Long saveUser(User user) {
    try (SessionManager sessionManager = userDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        long userId = userDao.save(user);
        sessionManager.commitSession();

        log.info("created user: {}", userId);
        return userId;
      } catch (Exception e) {
        log.error(e.getMessage(), e);
        sessionManager.rollbackSession();
        throw new DbServiceException(e);
      }
    }
  }

  @Override
  public Long updateUser(User user) {
    try (SessionManager sessionManager = userDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        Long id = userDao.update(user);
        sessionManager.commitSession();

        log.info("updated user: {}", id);
        return id;
      } catch (Exception e) {
        log.error(e.getMessage(), e);
        sessionManager.rollbackSession();
        throw new DbServiceException(e);
      }
    }
  }

  @Override
  public Long createOrUpdate(User user) {
    try (SessionManager sessionManager = userDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        Long id = userDao.createOrUpdate(user);
        sessionManager.commitSession();
        return id;
      } catch (Exception e) {
        log.error(e.getMessage(), e);
        sessionManager.rollbackSession();
        throw new DbServiceException(e);
      }
    }
  }

  @Override
  public Optional<User> getUser(Long id) {
    try (SessionManager sessionManager = userDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        Optional<User> userOptional = userDao.findById(id);

        log.info("user: {}", userOptional.orElse(null));
        return userOptional;
      } catch (Exception e) {
        log.error(e.getMessage(), e);
        sessionManager.rollbackSession();
      }
      return Optional.empty();
    }
  }

  @Override
  public Optional<User> getUserByLogin(String login) {
    try (SessionManager sessionManager = userDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        Optional<User> userOptional = userDao.findByLogin(login);

        log.info("user: {}", userOptional.orElse(null));
        return userOptional;
      } catch (Exception e) {
        log.error(e.getMessage(), e);
        sessionManager.rollbackSession();
      }
      return Optional.empty();
    }
  }

  public List<User> getAll() {
    try (SessionManager sessionManager = userDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        List<User> users = userDao.findAll();

        log.info("users: {}", users);
        return users;
      } catch (Exception e) {
        log.error(e.getMessage(), e);
        sessionManager.rollbackSession();
      }
      return Collections.emptyList();
    }
  }

}
