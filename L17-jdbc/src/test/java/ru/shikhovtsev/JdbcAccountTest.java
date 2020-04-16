package ru.shikhovtsev;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.shikhovtsev.core.model.Account;
import ru.shikhovtsev.core.service.DbServiceAccountImpl;
import ru.shikhovtsev.h2.DataSourceH2;
import ru.shikhovtsev.jdbc.DbExecutor;
import ru.shikhovtsev.jdbc.JdbcTemplate;
import ru.shikhovtsev.jdbc.dao.AccountDaoJdbc;
import ru.shikhovtsev.jdbc.sessionmanager.SessionManagerJdbc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JdbcAccountTest {
  private static DbServiceAccountImpl service;

  @BeforeAll
  static void createTable() throws SQLException {
    var dataSource = new DataSourceH2();
    var jdbcTemplate = new JdbcTemplate<>(new DbExecutor<>(), Account.class);
    service = new DbServiceAccountImpl(new AccountDaoJdbc(new SessionManagerJdbc(dataSource), jdbcTemplate));

    try (Connection connection = dataSource.getConnection();
         PreparedStatement pst = connection.prepareStatement("create table account(no long auto_increment, type varchar(255), rest number)")) {
      pst.executeUpdate();
    }
    System.out.println("table created");
  }

  @Test
  void test() {
    String type = "typeKek";
    BigDecimal rest = BigDecimal.valueOf(101);

    long id = service.saveAccount(new Account(type, rest));
    assertTrue(id != 0);

    Optional<Account> account = service.getAccount(id);
    Account acc = account.orElseThrow(() -> new RuntimeException());

    assertEquals(acc.getRest(), rest);
    assertEquals(acc.getType(), type);
  }
}
