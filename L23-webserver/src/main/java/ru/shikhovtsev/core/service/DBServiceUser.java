package ru.shikhovtsev.core.service;

import ru.shikhovtsev.core.model.User;

import java.util.List;
import java.util.Optional;

public interface DBServiceUser {

  Long saveUser(User user);

  Long updateUser(User user);

  Long createOrUpdate(User user);

  Optional<User> getUser(Long id);

  Optional<User> getUserByLogin(String login);

  List<User> getAll();
}
