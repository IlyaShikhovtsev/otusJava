package ru.shikhovtsev.core.service;

import ru.shikhovtsev.core.model.Account;

import java.util.Optional;

public interface DBServiceAccount {

  Long saveAccount(Account account);

  Long updateAccount(Account petya);

  Long createOrUpdate(Account account);

  Optional<Account> getAccount(long id);
}
