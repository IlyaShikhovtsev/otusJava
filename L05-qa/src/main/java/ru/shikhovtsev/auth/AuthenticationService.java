package ru.shikhovtsev.auth;

import java.util.Optional;

public class AuthenticationService {

  private UserRepository userRepository;

  public AuthenticationService(UserRepository provider) {
    this.userRepository = provider;
  }

  public boolean authenticate(String name, String password) {
    return Optional.of(userRepository.findByName(name))
        .map(user -> user.getPassword().equals(password))
        .orElse(false);
  }
}
