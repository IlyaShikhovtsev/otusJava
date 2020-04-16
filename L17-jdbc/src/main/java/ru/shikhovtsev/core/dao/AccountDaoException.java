package ru.shikhovtsev.core.dao;

public class AccountDaoException extends RuntimeException {
  public AccountDaoException(Exception e) {
    super(e);
  }
}
