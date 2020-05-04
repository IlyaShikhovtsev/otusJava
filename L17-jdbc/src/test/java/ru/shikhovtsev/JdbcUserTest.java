package ru.shikhovtsev;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.shikhovtsev.core.model.User;
import ru.shikhovtsev.core.service.DbServiceUserImpl;
import ru.shikhovtsev.h2.DataSourceH2;
import ru.shikhovtsev.jdbc.DbExecutor;
import ru.shikhovtsev.jdbc.JdbcTemplate;
import ru.shikhovtsev.jdbc.dao.UserDaoJdbc;
import ru.shikhovtsev.jdbc.sessionmanager.SessionManagerJdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JdbcUserTest {
  private static DbServiceUserImpl service;

  @BeforeAll
  static void createTable() throws SQLException {
    var dataSource = new DataSourceH2();
    var jdbcTemplate = new JdbcTemplate<>(new DbExecutor<>(), User.class);
    service = new DbServiceUserImpl(new UserDaoJdbc(new SessionManagerJdbc(dataSource), jdbcTemplate));

    try (Connection connection = dataSource.getConnection();
         PreparedStatement pst = connection.prepareStatement("create table user(id long auto_increment, name varchar(50))")) {
      pst.executeUpdate();
    }
    System.out.println("table created");
  }

  @Test
  void saveAndGet() {
    String username = "Petya";
    long id = service.saveUser(new User(username));
    assertTrue(id != 0);
    Optional<User> user = service.getUser(id);
    User petya = user.orElseThrow(() -> new RuntimeException());
    assertEquals(petya.getName(), username);
  }

  @Test
  void update() {
    String petyaName = "Petya";
    String vasyaName = "Vasya";

    User petya = new User(petyaName);
    long id = service.saveUser(petya);

    petya = service.getUser(id).orElseThrow(RuntimeException::new);
    petya.setName(vasyaName);
    service.updateUser(petya);

    User petyaFromDb = service.getUser(id).orElseThrow(RuntimeException::new);
    assertEquals(vasyaName, petyaFromDb.getName());
  }

  @Test
  void createOrUpdate() {
    User petya = new User("Petya");
    Long id = service.createOrUpdate(petya);
    petya = service.getUser(id).orElseThrow(RuntimeException::new);

    String vasya = "Vasya";
    petya.setName(vasya);

    assertEquals(id, service.createOrUpdate(petya));

    petya = service.getUser(id).orElseThrow(RuntimeException::new);
    assertEquals(vasya, petya.getName());
  }
}
