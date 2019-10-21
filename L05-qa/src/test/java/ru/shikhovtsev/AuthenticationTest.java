package ru.shikhovtsev;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.shikhovtsev.auth.AuthenticationService;
import ru.shikhovtsev.auth.User;
import ru.shikhovtsev.auth.UserRepository;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class AuthenticationTest {

  private static AuthenticationService service;

  @BeforeAll
  static void init() {
    UserRepository userRepositoryMock = mock(UserRepository.class);

    String name = "name";
    doReturn(null).when(userRepositoryMock).findByName(anyString());
    doReturn(new User(name, "password")).when(userRepositoryMock).findByName(name);

    service = new AuthenticationService(userRepositoryMock);
  }

  @Test
  void authenticationSuccess() {
    boolean successful = service.authenticate("name", "password");
    assertTrue(successful);
  }

}
