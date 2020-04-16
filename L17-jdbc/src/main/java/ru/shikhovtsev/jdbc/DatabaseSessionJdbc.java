package ru.shikhovtsev.jdbc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.shikhovtsev.core.sessionmanager.DatabaseSession;

import java.sql.Connection;

@AllArgsConstructor
@Getter
public class DatabaseSessionJdbc implements DatabaseSession {
  private final Connection connection;
}
