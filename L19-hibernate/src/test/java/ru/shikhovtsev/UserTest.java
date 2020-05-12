package ru.shikhovtsev;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.shikhovtsev.core.model.Address;
import ru.shikhovtsev.core.model.Phone;
import ru.shikhovtsev.core.model.User;
import ru.shikhovtsev.core.service.DbServiceUserImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

  private static final String HIBERNATE_CFG_XML_FILE_RESOURCE = "hibernate-test.cfg.xml";
  private SessionFactory sessionFactory;
  private SessionManagerHibernate sessionManagerHibernate;
  private UserDaoHibernate userDaoHibernate;

  @BeforeEach
  void setUp() {
    sessionFactory = HibernateUtils.buildSessionFactory(HIBERNATE_CFG_XML_FILE_RESOURCE, User.class, Phone.class, Address.class);
    sessionManagerHibernate = new SessionManagerHibernate(sessionFactory);
    userDaoHibernate = new UserDaoHibernate(sessionManagerHibernate);
  }

  @Test
  void save() {
    var userService = new DbServiceUserImpl(userDaoHibernate);

    var address = new Address("Street");
    var phone = new Phone("831483814");
    var user = new User("Ilya", address, phone);

    assertEquals(1L, userService.saveUser(user));
  }

  @Test
  void find() {
    var userService = new DbServiceUserImpl(userDaoHibernate);

    var address = new Address("Street");
    var phone = new Phone("831483814");
    var user = new User("Ilya", address, phone);

    assertEquals(1L, userService.saveUser(user));

    Optional<User> userFromDb = userService.getUser(1L);
    User ilya = userFromDb.orElseThrow(() -> new RuntimeException());

    assertEquals(user, ilya);
    assertEquals(address, ilya.getAddress());
    assertEquals(phone, ilya.getPhone());
  }

}
