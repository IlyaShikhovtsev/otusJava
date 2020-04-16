package ru.shikhovtsev.core.service;

import ru.shikhovtsev.core.model.Account;

import java.util.Optional;

public interface DBServiceAccount {

  long saveAccount(Account account);

  Optional<Account> getAccount(long id);

}
