package ru.shikhovtsev.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import ru.shikhovtsev.core.model.Role;
import ru.shikhovtsev.core.service.DBServiceUser;

@Controller("/login")
@RequiredArgsConstructor
public class LoginController {

  private final DBServiceUser serviceUser;

  @GetMapping
  public String loginView() {
    return "login";
  }

  @PostMapping
  public RedirectView auth(@RequestParam("login") String login, @RequestParam("password") String password) {
    if (authenticate(login, password)) {
      return new RedirectView("/users", true);
    } else {
      return new RedirectView("/", true);
    }
  }

  private boolean authenticate(String login, String password) {
    return serviceUser.getUserByLogin(login)
        .map(u -> u.getPassword().equals(password) && u.getRole().equals(Role.ADMIN))
        .orElse(false);
  }

}
