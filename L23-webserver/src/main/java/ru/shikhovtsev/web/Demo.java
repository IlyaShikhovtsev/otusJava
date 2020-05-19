package ru.shikhovtsev.web;

import ru.shikhovtsev.HibernateUtils;
import ru.shikhovtsev.SessionManagerHibernate;
import ru.shikhovtsev.UserDaoHibernate;
import ru.shikhovtsev.core.dao.UserDao;
import ru.shikhovtsev.core.model.Address;
import ru.shikhovtsev.core.model.Phone;
import ru.shikhovtsev.core.model.Role;
import ru.shikhovtsev.core.model.User;
import ru.shikhovtsev.core.service.DBServiceUser;
import ru.shikhovtsev.core.service.DbServiceUserImpl;
import ru.shikhovtsev.web.template.TemplateProcessor;
import ru.shikhovtsev.web.template.TemplateProcessorImpl;

import java.io.IOException;

public class Demo {

  private static final String HIBERNATE_CFG_XML_FILE_RESOURCE = "hibernate.cfg.xml";

  public static void main(String[] args) throws Exception {
    initServices();

    fillDb();

    var server = new WebServer();
    server.start();
    server.join();
  }

  private static void fillDb() {
    var service = Context.getService(DBServiceUser.class);

    var address = new Address("Street");
    var phone = new Phone("831483814");
    var admin = new User("admin", "admin", "админ", address, phone, Role.ADMIN);

    service.createOrUpdate(admin);

    var user = new User("user", "user", "пользователь",
        new Address("Street2"), new Phone("99999999"), Role.USER);
    service.createOrUpdate(user);
  }

  private static void initServices() {
    var sessionFactory = HibernateUtils.buildSessionFactory(HIBERNATE_CFG_XML_FILE_RESOURCE, User.class, Phone.class, Address.class);
    var sessionManagerHibernate = new SessionManagerHibernate(sessionFactory);
    var userDaoHibernate = new UserDaoHibernate(sessionManagerHibernate);
    var userService = new DbServiceUserImpl(userDaoHibernate);

    Context.put(DBServiceUser.class, userService);
    Context.put(UserDao.class, userDaoHibernate);

    try {
      var templateProcessor = new TemplateProcessorImpl("/templates/");
      Context.put(TemplateProcessor.class, templateProcessor);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
