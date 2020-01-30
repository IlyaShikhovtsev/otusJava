package ru.shikhovtsev.framework.tests;

import ru.shikhovtsev.framework.annotations.AfterEach;
import ru.shikhovtsev.framework.annotations.Test;

public class AfterFailedTest {

  @Test
  public void test() {
    System.out.println("Hello");
  }

  @AfterEach
  public void afterWithException() {
    System.out.println("exception");
    throw new RuntimeException();
  }

  @AfterEach
  public void afterWithoutException() {
    System.out.println("Hi");
  }

}
