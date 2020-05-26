package ru.shikhovtsev.web;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import ru.shikhovtsev.core.model.Address;
import ru.shikhovtsev.core.model.Phone;
import ru.shikhovtsev.core.model.Role;
import ru.shikhovtsev.core.model.User;
import ru.shikhovtsev.core.service.DBServiceUser;

@Service
@RequiredArgsConstructor
public class DbInitService implements InitializingBean {

  private final DBServiceUser serviceUser;

  @Override
  public void afterPropertiesSet() {
    var address = new Address("Street");
    var phone = new Phone("831483814");
    var admin = new User("admin", "admin", "админ", address, phone, Role.ADMIN);

    serviceUser.createOrUpdate(admin);

    var user = new User("user", "user", "пользователь",
        new Address("Street2"), new Phone("99999999"), Role.USER);
    serviceUser.createOrUpdate(user);
  }
}
