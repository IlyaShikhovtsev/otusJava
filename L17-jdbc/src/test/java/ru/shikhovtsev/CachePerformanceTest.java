package ru.shikhovtsev;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.shikhovtsev.cachehw.HwCache;
import ru.shikhovtsev.cachehw.HwListener;
import ru.shikhovtsev.cachehw.MyCache;
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

import static org.mockito.Mockito.mock;

public class CachePerformanceTest {

  private static DataSourceH2 dataSource;
  private static JdbcTemplate<User> jdbcTemplate;

  //При установке значения в 500, можно проследить как очищается кеш из-за нехватки памяти
  private static int USER_COUNT = 100;
  private static DbServiceUserImpl service;

  @BeforeAll
  static void createTable() throws SQLException {
    dataSource = new DataSourceH2();
    jdbcTemplate = new JdbcTemplate<>(new DbExecutor<>(), User.class);
    var cache = new MyCache<Long, User>();
    cache.addListener(new HwListener<Long, User>() {
      @Override
      public void notify(Long key, User value, String action) {
        System.out.println(String.format("Cache: (%s) key: %d; value: %s", action.toUpperCase(), key, value));
      }
    });
    service = new DbServiceUserImpl(new UserDaoJdbc(new SessionManagerJdbc(dataSource), jdbcTemplate), cache);

    try (Connection connection = dataSource.getConnection();
         PreparedStatement pst = connection.prepareStatement("create table user(id long auto_increment, name varchar(50))")) {
      pst.executeUpdate();
    }
    System.out.println("table created");

    for (int i = 0; i < USER_COUNT; i++) {
      service.saveUser(new User(String.valueOf(i)));
    }
  }

  @Test
  void performance() {
    perf(service);
    //время на выполнение 78 107 85
  }

  @Test
  void withoutCache() {
    var cache = mock(HwCache.class);
    var service = new DbServiceUserImpl(new UserDaoJdbc(new SessionManagerJdbc(dataSource), jdbcTemplate), cache);

    perf(service);
    //время на выполнение 818 899 833
  }

  /**
   * 10 раз получает всех пользователей
   */
  private void perf(DbServiceUserImpl service) {
    long start = System.currentTimeMillis();
    for (int j = 0; j < 10; j++) {
      for (int i = 1; i < USER_COUNT; i++) {
        var user = service.getUser((long) i).get();
      }
    }
    System.out.println((System.currentTimeMillis() - start));
  }

}
