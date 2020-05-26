package ru.shikhovtsev.hibernate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.shikhovtsev.core.dao.UserDao;
import ru.shikhovtsev.core.dao.UserDaoException;
import ru.shikhovtsev.core.model.User;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserDaoHibernate implements UserDao {

  private final SessionManagerHibernate sessionManager;

  @Override
  public Optional<User> findById(Long id) {
    DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
    try {
      return Optional.ofNullable(currentSession.getSession().find(User.class, id));
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    return Optional.empty();
  }

  @Override
  public Optional<User> findByLogin(String login) {
    DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
    try {
      return Optional.ofNullable(
          currentSession.getSession().createQuery("SELECT u From User u WHERE u.login = :name", User.class)
              .setParameter("name", login)
              .getSingleResult());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    return Optional.empty();
  }

  @Override
  public List<User> findAll() {
    DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
    return currentSession.getSession().createQuery("SELECT u From User u", User.class).getResultList();
  }

  @Override
  public Long save(User user) {
    DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
    try {
      Session hibernateSession = currentSession.getSession();
      if (user.getId() != null) {
        hibernateSession.merge(user);
      } else {
        hibernateSession.persist(user);
      }
      return user.getId();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new UserDaoException(e);
    }
  }

  @Override
  public Long update(User user) {
    return save(user);
  }

  @Override
  public Long createOrUpdate(User user) {
    return save(user);
  }

  @Override
  public SessionManagerHibernate getSessionManager() {
    return sessionManager;
  }
}
