package ru.shikhovtsev.proxy;

import java.time.LocalDateTime;

public interface MyService {

  LocalDateTime computeLastDateTime();

  int getCount();

  void setCount(int count);

  void setParams(int a, int b, String str);
}
