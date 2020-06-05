package ru.shikhovtsev.web.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.shikhovtsev.core.model.Role;
import ru.shikhovtsev.core.model.User;
import ru.shikhovtsev.core.service.DBServiceUser;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {
  private final DBServiceUser userService;

  @GetMapping({"/", "/users"})
  public String userList(Model model) {
    model.addAttribute("users", userService.getAll());
    return "users";
  }

  @PostMapping("/users")
  public RedirectView saveUser(@ModelAttribute User user) {
    user.setRole(Role.USER);
    userService.createOrUpdate(user);
    return new RedirectView("/users", true);
  }
}
