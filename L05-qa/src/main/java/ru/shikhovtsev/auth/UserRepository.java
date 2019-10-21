package ru.shikhovtsev.auth;

public interface UserRepository {
  User findByName(String name);
}
