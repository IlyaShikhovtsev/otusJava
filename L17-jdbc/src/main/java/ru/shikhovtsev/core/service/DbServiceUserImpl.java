package ru.shikhovtsev.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shikhovtsev.cachehw.HwCache;
import ru.shikhovtsev.core.dao.UserDao;
import ru.shikhovtsev.core.model.User;
import ru.shikhovtsev.core.sessionmanager.SessionManager;

import java.util.Optional;

public class DbServiceUserImpl implements DBServiceUser {
  private static Logger logger = LoggerFactory.getLogger(DbServiceUserImpl.class);

  private final UserDao userDao;
  private final HwCache<Long, User> cache;

  public DbServiceUserImpl(UserDao userDao, HwCache<Long, User> cache) {
    this.userDao = userDao;
    this.cache = cache;
  }

  @Override
  public Long saveUser(User user) {
    try (SessionManager sessionManager = userDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        long userId = userDao.save(user);
        sessionManager.commitSession();

        logger.info("created user: {}", userId);
        return userId;
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
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

        logger.info("updated user: {}", id);

        cache.remove(user.getId());
        return id;
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
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

        cache.remove(id);
        return id;
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        sessionManager.rollbackSession();
        throw new DbServiceException(e);
      }
    }
  }

  @Override
  public Optional<User> getUser(Long id) {
    User cached = cache.get(id);
    if (cached != null) {
      return Optional.of(cached);
    }

    try (SessionManager sessionManager = userDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        Optional<User> userOptional = userDao.findById(id);

        logger.info("user: {}", userOptional.orElse(null));

        userOptional.ifPresent(user -> cache.put(id, user));
        return userOptional;
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        sessionManager.rollbackSession();
      }
      return Optional.empty();
    }
  }

}
