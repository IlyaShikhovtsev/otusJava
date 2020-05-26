package ru.shikhovtsev;

import ru.shikhovtsev.core.model.Address;
import ru.shikhovtsev.core.model.Phone;
import ru.shikhovtsev.core.model.User;
import ru.shikhovtsev.core.service.DbServiceUserImpl;
import ru.shikhovtsev.hibernate.HibernateUtils;
import ru.shikhovtsev.hibernate.SessionManagerHibernate;
import ru.shikhovtsev.hibernate.UserDaoHibernate;
import ru.shikhovtsev.web.DbInitService;
import ru.shikhovtsev.web.WebServer;
import ru.shikhovtsev.web.template.TemplateProcessorImpl;

public class Demo {

  private static final String HIBERNATE_CFG_XML_FILE_RESOURCE = "hibernate.cfg.xml";

  public static void main(String[] args) throws Exception {


    var sessionFactory = HibernateUtils.buildSessionFactory(HIBERNATE_CFG_XML_FILE_RESOURCE, User.class, Phone.class, Address.class);
    var sessionManagerHibernate = new SessionManagerHibernate(sessionFactory);
    var userDaoHibernate = new UserDaoHibernate(sessionManagerHibernate);
    var userService = new DbServiceUserImpl(userDaoHibernate);

    var templateProcessor = new TemplateProcessorImpl("/templates/");

    new DbInitService(userService).fillDb();
    var server = new WebServer(userService, templateProcessor);

    server.start();
    server.join();
  }
}
