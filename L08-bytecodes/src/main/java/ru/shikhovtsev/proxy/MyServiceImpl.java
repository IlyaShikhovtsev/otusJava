package ru.shikhovtsev.proxy;

import ru.shikhovtsev.Log;

import java.time.LocalDateTime;

public class MyServiceImpl implements MyService {

  private int count = 0;

  @Log
  @Override
  public LocalDateTime computeLastDateTime() {
    return LocalDateTime.now();
  }

  @Log
  @Override
  public int getCount() {
    return count;
  }

  @Log
  @Override
  public void setCount(int count) {
    this.count = count;
  }

  @Log
  @Override
  public void setParams(int a, int b, String str) {
  }
}
