package ru.shikhovtsev;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.shikhovtsev.core.sessionmanager.DatabaseSession;

@AllArgsConstructor
@Getter
public class DatabaseSessionHibernate implements DatabaseSession {
  private Session session;
  private Transaction transaction;

  public DatabaseSessionHibernate(Session session) {
    this.session = session;
    this.transaction = session.beginTransaction();
  }

  public void close() {
    if (transaction.isActive()) {
      transaction.commit();
    }
    session.close();
  }
}
