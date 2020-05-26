package ru.shikhovtsev.web;

import org.eclipse.jetty.security.AbstractLoginService;
import org.eclipse.jetty.util.security.Password;
import ru.shikhovtsev.core.service.DBServiceUser;

public class LoginService extends AbstractLoginService {

  private final DBServiceUser serviceUser;

  public LoginService(DBServiceUser serviceUser) {
    this.serviceUser = serviceUser;
  }

  @Override
  protected String[] loadRoleInfo(UserPrincipal user) {
    return new String[]{"admin"};
  }

  @Override
  protected UserPrincipal loadUserInfo(String login) {
    return serviceUser.getUserByLogin(login)
        .map(u -> new UserPrincipal(u.getLogin(), new Password(u.getPassword()))).orElse(null);
  }
}
