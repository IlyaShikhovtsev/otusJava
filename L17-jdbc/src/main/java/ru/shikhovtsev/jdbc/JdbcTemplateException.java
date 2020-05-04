package ru.shikhovtsev.jdbc;

public class JdbcTemplateException extends RuntimeException {
  public JdbcTemplateException(Exception ex) {
    super(ex);
  }
}
